package com.yjlan.user.credit.api;

import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.credit.domain.UserCreditHourInfoVO;

/**
 * 用户课时服务
 * @author yjlan
 */
public interface UserCreditHourApi {

    /**
     * 获取用户的学时
     * @param userId 用户id
     * @return 用户的学时
     */
    ResponseResult<UserCreditHourInfoVO> getUserCreditHour(Long userId);
}
