package com.lzq.wanandroid.DataBase;

import org.litepal.crud.LitePalSupport;

public class Tree extends LitePalSupport {
    //只保存一级标题
    private int parentID;
    private String name;
    private int type;//体系还是导航


    public Tree() {
    }

    public Tree(int parentID, String name, int type) {
        this.parentID = parentID;
        this.name = name;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
