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
public class Paging {

    /**
     * 页大小,当pageSize为0时则取所有
     */
    private int pageSize = 10;

    /**
     * 当前页
     */
    private int page = 1;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private int totalRecord;

    /**
     * 起始数(limit 第一位)
     */
    private int sinceCount = 0;

    public Paging() {

    }

    public Paging(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        getSinceCountByPage();
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

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        totalPage = (totalRecord - 1) / pageSize + 1;
    }

    /**
     * 根据pageSize分页List集合
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
     * 获取分页下标范围
     * 
     * @return
     */
    public int[] getStartAndEndRange() {
        int startPage = 0;
        int endPage = 0;
        if (getPage() < 6) {
            startPage = 1;
        } else {
            startPage = getPage() - 5;
        }

        if (getTotalPage() >= 10) {
            if (getPage() + 5 >= getTotalPage()) {
                endPage = getTotalPage();
            } else {
                endPage = getPage() + 5;
            }

        } else if (getTotalPage() < 10) {
            endPage = getTotalPage();
        } else {
            endPage = getPage() + getTotalPage() - 1;
        }
        int[] result = { startPage, endPage };
        return result;
    }

    public int getSinceCount() {
        return sinceCount;
    }

    public void setSinceCount(int sinceCount) {
        this.sinceCount = sinceCount;
    }

    /**
     * 封装成Map(翻页)
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

    public static Paging generatePaging(String page, String pageSize) {
        int p = 0;
        int pSize = 10;
        try {
            if (page != null && page.length() > 0) {
                p = Integer.parseInt(page);
            }
            if (pageSize != null && pageSize.length() > 0) {
                pSize = Integer.parseInt(pageSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Paging(p, pSize);
    }

}
