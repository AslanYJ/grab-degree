package com.grab.degree.cms.dao;

import org.springframework.stereotype.Repository;

import com.grab.degree.cms.domain.entity.Test;
import com.grab.degree.cms.mapper.TestMapper;
import com.grab.degree.common.dao.BaseDAO;

/**
 * 测试Dao
 * @author yjlan
 */
@Repository
public class TestDAO extends BaseDAO<TestMapper, Test> {

}
