package com.grab.degree.common.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author yjlan
 */
public class BaseDAO<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public List<T> queryByConditions(Map<String, Object> conditions) {
        List<T> list = new ArrayList<>();
        if (null == conditions) {
            return list;
        }

        Map<String, Object> where = new HashMap<>();

        Set<String> keys = conditions.keySet();
        for (String key : keys) {
            if (null == conditions.get(key)) {
                continue;
            }
            where.put(key, conditions.get(key));
        }

        return listByMap(where);
    }
}
