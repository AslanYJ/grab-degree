package com.grab.degree.common.resp;

import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * 分页
 *
 * @author yjlan
 */
@Data
public class PageResult<T> {
    
    private long total = 0L;
    
    private long pageSize = 10L;
    
    private long pageNo = 1L;
    
    private List<T> records = Collections.emptyList();
    
    public PageResult() {
    }
    
    public PageResult(long pageNo, long pageSize, long total) {
        this.total = total;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }
}
