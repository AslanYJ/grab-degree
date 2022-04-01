package com.yjlan.user.center.api.impl;

import com.grab.degree.common.resp.ResponseCode;
import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.center.api.UserInfoApi;
import com.yjlan.user.center.dao.UserInfoDAO;
import com.yjlan.user.center.domain.UserInfoVO;
import com.yjlan.user.center.domain.entity.UserInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

@DubboService(version = "1.0.0",interfaceClass = UserInfoApi.class,retries = 0)
public class UserInfoApiImpl implements UserInfoApi {

    @Resource
    private UserInfoDAO userInfoDAO;

    @Override
    public ResponseResult<UserInfoVO> getUserInfoByUserId(Long userId) {
        UserInfo userInfo = userInfoDAO.getById(userId);
        if (userInfo == null) {
            return ResponseResult.fail(ResponseCode.CHECK_FAIL.getCode(),"该用户数据不存在");
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(userId);
        BeanUtils.copyProperties(userInfo,userInfoVO);
        return ResponseResult.success(userInfoVO);
    }
}
