package com.lzq.wanandroid.model;

import java.util.List;

public class SearchResult {
    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public SearchResult() {
    }

    public SearchResult(DataBean data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class  DataBean {
        private int curPage;
        private List<Datas> datas;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;

        public DataBean() {
        }

        public DataBean(int curPage, List<Datas> datas, int offset, boolean over, int pageCount, int size, int total) {
            this.curPage = curPage;
            this.datas = datas;
            this.offset = offset;
            this.over = over;
            this.pageCount = pageCount;
            this.size = size;
            this.total = total;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public List<Datas> getDatas() {
            return datas;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class Datas{
            private String apkLink;
            private String author;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String envelopePic;
            private boolean fresh;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private String prefix;
            private String projectLink;
            private long publishTime;
            private int superChapterId;
            private String superChapterName;
            private List<Tags> tags;
            private String title;
            private int type;
            private int userId;
            private int visible;
            private int zan;

            public Datas() {
            }

            public Datas(String apkLink, String author, int chapterId, String chapterName, boolean collect, int courseId, String desc, String envelopePic, boolean fresh, int id, String link, String niceDate, String origin, String prefix, String projectLink, long publishTime, int superChapterId, String superChapterName, List<Tags> tags, String title, int type, int userId, int visible, int zan) {
                this.apkLink = apkLink;
                this.author = author;
                this.chapterId = chapterId;
                this.chapterName = chapterName;
                this.collect = collect;
                this.courseId = courseId;
                this.desc = desc;
                this.envelopePic = envelopePic;
                this.fresh = fresh;
                this.id = id;
                this.link = link;
                this.niceDate = niceDate;
                this.origin = origin;
                this.prefix = prefix;
                this.projectLink = projectLink;
                this.publishTime = publishTime;
                this.superChapterId = superChapterId;
                this.superChapterName = superChapterName;
                this.tags = tags;
                this.title = title;
                this.type = type;
                this.userId = userId;
                this.visible = visible;
                this.zan = zan;
            }

            public String getApkLink() {
                return apkLink;
            }

            public void setApkLink(String apkLink) {
                this.apkLink = apkLink;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public boolean isFresh() {
                return fresh;
            }

            public void setFresh(boolean fresh) {
                this.fresh = fresh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getSuperChapterId() {
                return superChapterId;
            }

            public void setSuperChapterId(int superChapterId) {
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName() {
                return superChapterName;
            }

            public void setSuperChapterName(String superChapterName) {
                this.superChapterName = superChapterName;
            }

            public List<Tags> getTags() {
                return tags;
            }

            public void setTags(List<Tags> tags) {
                this.tags = tags;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public class Tags {
                private String name;
                private String url;


                public void setName(String name) {
                    this.name = name;
                }
                public String getName() {
                    return name;
                }


                public void setUrl(String url) {
                    this.url = url;
                }
                public String getUrl() {
                    return url;
                }
            }

        }
    }
}
