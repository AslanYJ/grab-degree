package com.grab.degree.cms.dao;

import org.springframework.stereotype.Repository;

import com.grab.degree.cms.domain.entity.CourseInfo;
import com.grab.degree.cms.mapper.CourseInfoMapper;
import com.grab.degree.common.dao.BaseDAO;

/**
 * 课程信息DAO
 * @author yjlan
 */
@Repository
public class CourseInfoDAO extends BaseDAO<CourseInfoMapper, CourseInfo> {

}
