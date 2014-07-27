package com.zhy.listen.bean;

/**
 * 分页 先放入pageSize,然后放入最大记录数,自动算出最大页,在放入当前页
 * 
 * @author zhy
 * 
 */
public class Page implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6383539006738508408L;

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

    public Page() {

    }

    public Page(int page, int pageSize) {
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
        if (page >= 1) {
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

    public void getSinceCountByPage() {
        if (page > 1) {
            sinceCount = (page - 1) * pageSize;
        }
    }

}
