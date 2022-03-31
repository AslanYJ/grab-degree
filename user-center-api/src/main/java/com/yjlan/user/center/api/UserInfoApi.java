package com.yjlan.user.center.api;

import com.grab.degree.common.resp.ResponseResult;
import com.yjlan.user.center.domain.UserInfoVO;

/**
 * userInfoApi
 * @author yjlan
 */
public interface UserInfoApi {

    /**
     * 获取用户的基本信息
     * @param userId 用户id
     * @return 用户基本信息
     */
    ResponseResult<UserInfoVO> getUserInfoByUserId(Long userId);
}
