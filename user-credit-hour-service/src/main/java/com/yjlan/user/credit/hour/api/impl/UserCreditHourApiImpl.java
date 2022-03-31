package com.yjlan.user.credit.hour.api.impl;

import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.credit.api.UserCreditHourApi;
import com.yjlan.user.credit.domain.UserCreditHourInfoVO;
import com.yjlan.user.credit.hour.dao.UserCreditHourDAO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService(interfaceClass = UserCreditHourApi.class,retries = 0)
public class UserCreditHourApiImpl implements UserCreditHourApi {

    @Resource
    private UserCreditHourDAO userCreditHourDAO;

    @Override
    public ResponseResult<UserCreditHourInfoVO> getUserCreditHour(Long userId) {
        UserCreditHourInfoVO userCreditHourInfoVO = new UserCreditHourInfoVO();
        userCreditHourInfoVO.setUserId(userId);
        userCreditHourInfoVO.setAvailableCreditHour(100);
        userCreditHourInfoVO.setTotalCreditHour(100);
        userCreditHourInfoVO.setUsedCreditHour(0);
        userCreditHourInfoVO.setFreezeCreditHour(0);
        return ResponseResult.success(userCreditHourInfoVO);
    }
}
