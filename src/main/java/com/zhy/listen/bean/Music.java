package com.zhy.listen.bean;

public class Music extends Paging implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2909860277973637781L;

    private Long id;
    
    private String title;
    
    private String author;
    
    private String url;
    
    private String lrc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }
}
