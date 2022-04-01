package com.grab.degree.activity.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.grab.degree.activity.config.ShardJedisClient;
import com.grab.degree.activity.constants.CacheKeyConstants;
import com.grab.degree.activity.domain.dto.GrabDegreeDTO;
import com.grab.degree.common.exception.BaseBizException;
import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.center.api.UserInfoApi;
import com.yjlan.user.center.domain.UserInfoVO;
import com.yjlan.user.credit.api.UserCreditHourApi;
import com.yjlan.user.credit.domain.UserCreditHourInfoVO;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import com.grab.degree.activity.service.GrabDegreeService;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 抢购服务
 *
 * @author yjlan
 */
@Slf4j
@Service
public class GrabDegreeServiceImpl implements GrabDegreeService {

    /**
     * 顺序，用来决定在哪台机器上进行扣减
     */
    private static final AtomicLong SEQUENCER = new AtomicLong();

    @Resource
    private ShardJedisClient shardJedisClient;


    @DubboReference(version = "1.0.0")
    private UserCreditHourApi userCreditHourApi;

    @DubboReference(version = "1.0.0")
    private UserInfoApi userInfoApi;


    @Override
    public boolean garbDegree(GrabDegreeDTO grabDegreeDTO) {
        // 校验是否符合抢学位的条件
        checkIsSuitable(grabDegreeDTO);
        // 校验通过后开始抢学位的逻辑，主要是通过redis的lua脚本来进行抢，如果失败的话直接就抛出异常
        // 告诉用户学位已经没有了学位了,可以返回对应的code，然后全局异常件进行捕捉，统一返回
        // 每一个线程过来都会先拿到对应的sequence，然后拿到这个sequence去redis中进行学位扣减
        long sequence = SEQUENCER.incrementAndGet();
        int redisCount = shardJedisClient.getRedisCount();
        // 计算拿到最大值 从sequence 到 maxSequence就是redisCount的数量
        long maxSequence = sequence + redisCount - 1;
        String key = CacheKeyConstants.buildGrabDegreeKey(grabDegreeDTO.getActivityId());
        String script = String.format(CacheKeyConstants.SCRIPT, key);
        String result;
        // 这里会轮询所有的redis实例
        for (long i = sequence; i <= maxSequence; i++) {
            result = (String) shardJedisClient.eval(i, script);
            if (result.equals("success")) {
                // 抢购成功的信息保存到Redis中
                String checkKey = CacheKeyConstants.buildCheckExistKey(grabDegreeDTO.getUserId(), grabDegreeDTO.getActivityId());
                shardJedisClient.set(checkKey,"1",7200);
                // 发送到MQ中,生成学位信息，扣除学时
                log.info("扣除学位成功,sequence:{}",sequence);
                return true;
            }
        }
        return false;
    }

    private void checkIsSuitable(GrabDegreeDTO grabDegreeDTO) {
        Long userId = grabDegreeDTO.getUserId();
        ResponseResult<UserInfoVO> userInfoVOResponseResult = userInfoApi.getUserInfoByUserId(userId);
        if (!userInfoVOResponseResult.isSuccess()) {
            log.info("userId:{},没有找到对应的学员信息", userId);
            throw new BaseBizException("宝宝不符合该课程的报名要求");
        }
        // 判断年龄是否合适
        UserInfoVO userInfoVO = userInfoVOResponseResult.getData();
        if (userInfoVO.getAge() > grabDegreeDTO.getMaxAge() || userInfoVO.getAge() < grabDegreeDTO.getMaxAge()) {
            throw new BaseBizException("宝宝的年龄不符合该课程的报名要求");
        }
        // 判断是否有足够的课时
        ResponseResult<UserCreditHourInfoVO> userCreditHourResult = userCreditHourApi.getUserCreditHour(userId);
        if (!userCreditHourResult.isSuccess()) {
            log.info("userId:{},没有找到对应的学时数据", userId);
            throw new BaseBizException("宝宝不符合该课程的报名要求");
        }
        UserCreditHourInfoVO userCreditHourInfoVO = userCreditHourResult.getData();
        if (userCreditHourInfoVO.getAvailableCreditHour() < grabDegreeDTO.getCourseHour()) {
            throw new BaseBizException("宝宝不符合该课程的报名要求");
        }
        // 判断是否已经有学位了
        String key = CacheKeyConstants.buildCheckExistKey(userId, grabDegreeDTO.getActivityId());
        String value = shardJedisClient.get(key);
        if (StringUtils.isNotBlank(value)) {
            throw new BaseBizException("宝宝已经报名该课程啦，请勿重复报名~");
        }
    }
}
