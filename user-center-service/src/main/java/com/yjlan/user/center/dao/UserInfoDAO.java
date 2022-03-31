package com.yjlan.user.center.dao;

import com.grab.degree.common.dao.BaseDAO;
import com.yjlan.user.center.domain.entity.UserInfo;
import com.yjlan.user.center.mapper.UserInfoMapper;
import org.springframework.stereotype.Repository;

/**
 * DAO
 * @author yjlan
 */
@Repository
public class UserInfoDAO extends BaseDAO<UserInfoMapper, UserInfo> {
}
