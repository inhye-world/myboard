package edu.example.myboard.dto;

public class CommentPage {
    private final static int perBlock = 10;
    private int bnum;
    private int limit;
    private int curPage;
    private int startPage;
    private int start;
    private int endPage;
    private int maxPage;

    public void setStart(int start) {
        this.start = start;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setStartPage(int startPage) { this.startPage = startPage; }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
