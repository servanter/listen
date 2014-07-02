package com.zhy.listen.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页 先放入pageSize,然后放入最大记录数,自动算出最大页,在放入当前页
 * 
 * @author zhy
 * 
 */
public class Paging<T> {

    /**
     * 默认页大小
     */
    private int pageSize = 10;

    /**
     * 当前页
     */
    private int page = 1;

    /**
     * 总页数
     */
    private int totalPage = 0;

    /**
     * 总记录数
     */
    private long totalRecord = 0;

    /**
     * 从id
     */
    private int sinceCount = 0;

    private List<T> result;

    public Paging() {

    }

    public Paging(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        getSinceCountByPage();
    }

    public Paging(long totalRecord, int page, int pageSize, List<T> result) {
        this.page = page;
        this.pageSize = pageSize;
        setTotalRecord(totalRecord);
        this.setResult(result);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        if (page != 1) {
            sinceCount = (page - 1) * pageSize;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
        totalPage = (totalRecord.intValue() - 1) / pageSize + 1;
    }

    public int getSinceCount() {
        return sinceCount;
    }

    public void setSinceCount(int sinceCount) {
        this.sinceCount = sinceCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    /**
     * 全部加装时，分页
     * 
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getResult(List list) {
        List result = new ArrayList();
        int previousRecord = (getPage() - 1) * getPageSize();
        int currRecord = getPage() * getPageSize();
        if (list.size() < getPageSize()) {
            return list;
        }
        if (list.size() < currRecord) {
            currRecord = list.size();
        }
        for (int i = 0; i < currRecord; i++) {
            if (i >= previousRecord) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * 分页10页内下标
     * 
     * @return
     */
    public int[] getStartAndEndRange() {
        int startPage = 0;
        int endPage = 0;
        if (getPage() < 4) {
            startPage = 1;
        } else {
            startPage = getPage() - 3;
        }

        if (getTotalPage() >= 8) {
            if (getPage() + 3 >= getTotalPage()) {
                endPage = getTotalPage();
            } else {
                endPage = getPage() + 3;
            }

        } else if (getTotalPage() < 8) {
            endPage = getTotalPage();
        } else {
            endPage = getPage() + getTotalPage() - 1;
        }
        int[] result = { startPage, endPage };
        return result;
    }
    
    public static void main(String[] args) {
        Paging p = new Paging();
        p.setPage(7);
        p.setTotalPage(100);
        int i[] = p.getStartAndEndRange();
        System.out.println(i[0] + " " + i[1]);
    }

    /**
     * to map
     * 
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> pagMap = new HashMap<String, Object>();
        int sinceCount = 0;
        if (page != 1) {
            sinceCount = (page - 1) * pageSize;
        }
        pagMap.put("sinceCount", String.valueOf(sinceCount));
        pagMap.put("pageSize", String.valueOf(pageSize));
        return pagMap;
    }

    public void getSinceCountByPage() {
        if (page > 1) {
            sinceCount = (page - 1) * pageSize;
        }
    }

    public Paging<T> setTotalRecordAndResult(long totalRecord, List<T> result) {
        setTotalRecord(totalRecord);
        this.result = result;
        return this;
    }

    public Paging generateCommonAttributePaging() {
        Paging p = new Paging();
        p.setPage(this.page);
        p.setPageSize(this.pageSize);
        p.setTotalPage(this.totalPage);
        p.setTotalRecord(this.totalRecord);
        p.setSinceCount(this.sinceCount);
        return p;
    }


}
