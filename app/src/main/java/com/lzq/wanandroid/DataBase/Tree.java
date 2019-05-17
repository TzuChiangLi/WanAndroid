package com.lzq.wanandroid.DataBase;

public class Tree {
    //只保存一级标题
    private int parentID;
    private String name;

    public Tree() {
    }

    public Tree(int parentID, String name) {
        this.parentID = parentID;
        this.name = name;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
