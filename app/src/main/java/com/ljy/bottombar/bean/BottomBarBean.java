package com.ljy.bottombar.bean;

public class BottomBarBean {

    private boolean isCheck;
    private int index;
    private int image;
    private int imageHover;
    private int msgCount;
    private String title;

    public BottomBarBean(int image, int imageHover, int msgCount, String title, boolean isCheck) {
        this.image = image;
        this.imageHover = imageHover;
        this.msgCount = msgCount;
        this.title = title;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageHover() {
        return imageHover;
    }

    public void setImageHover(int imageHover) {
        this.imageHover = imageHover;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BottomBarBean{" +
                "isCheck=" + isCheck +
                ", index=" + index +
                ", image=" + image +
                ", imageHover=" + imageHover +
                ", msgCount=" + msgCount +
                ", title='" + title + '\'' +
                '}';
    }
}
