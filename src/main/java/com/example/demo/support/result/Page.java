package com.example.demo.support.result;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */
public class Page implements Serializable {

    private int pages;//页数

    private long total;//总记录数

    private int currentPage;//当前页

    public Page(){

    }

    public Page(long total,int pages,int currentPage){
        this.total = total;
        this.pages = pages;
        this.currentPage = currentPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
