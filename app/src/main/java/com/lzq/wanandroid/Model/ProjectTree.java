package com.lzq.wanandroid.model;

import java.util.List;

public class ProjectTree {
    private List<ProjectTree.DataBean> data;
    private int errorCode;
    private String errorMsg;

    public ProjectTree() {
    }

    public ProjectTree(List<ProjectTree.DataBean> data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public List<ProjectTree.DataBean> getData() {
        return data;
    }

    public void setData(List<ProjectTree.DataBean> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        private List<Children> children;
        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private boolean userControlSetTop;
        private int visible;

        public DataBean() {
        }

        public DataBean(List<Children> children, int courseId, int id, String name, int order, int parentChapterId, boolean userControlSetTop, int visible) {
            this.children = children;
            this.courseId = courseId;
            this.id = id;
            this.name = name;
            this.order = order;
            this.parentChapterId = parentChapterId;
            this.userControlSetTop = userControlSetTop;
            this.visible = visible;
        }

        public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public boolean isUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }
    }

}
