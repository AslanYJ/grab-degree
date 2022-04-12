package com.yjlan.user.credit.api.impl;

import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.credit.api.UserCreditHourApi;
import com.yjlan.user.credit.domain.UserCreditHourInfoVO;
import com.yjlan.user.credit.dao.UserCreditHourDAO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 实现类
 * @author yjlan
 */
@DubboService(version = "1.0.0",interfaceClass = UserCreditHourApi.class,retries = 0)
public class UserCreditHourApiImpl implements UserCreditHourApi {

    @Resource
    private UserCreditHourDAO userCreditHourDAO;

    @Override
    public ResponseResult<UserCreditHourInfoVO> getUserCreditHour(Long userId) {
        UserCreditHourInfoVO userCreditHourInfoVO = new UserCreditHourInfoVO();
        userCreditHourInfoVO.setUserId(userId);
        userCreditHourInfoVO.setAvailableCreditHour(100.0);
        userCreditHourInfoVO.setTotalCreditHour(100.0);
        userCreditHourInfoVO.setUsedCreditHour(0.0);
        userCreditHourInfoVO.setFreezeCreditHour(0.0);
        return ResponseResult.success(userCreditHourInfoVO);
    }
}
