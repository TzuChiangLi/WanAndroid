package com.lzq.wanandroid.model;

/**
 * @author TzuchiangLi
 * @time 2019年7月5日10:55:25
 * @description Event
 */
public class Event {

    /**
     * 登录界面监听
     */
    public static final int TYPE_USER_LOGIN = 1;
    /**
     * 登录成功
     */
    public static final int TYPE_LOGIN = 2;

    /**
     * 退出登录
     */
    public static final int TYPE_LOGOUT = 3;

    /**
     * 收藏
     */
    public static final int TYPE_COLLECT = 4;

    /**
     * 取消收藏
     */
    public static final int TYPE_UNCOLLECT = 5;

    /**
     * 切换夜间模式
     */
    public static final int TYPE_CHANGE_DAY_NIGHT_MODE = 6;

    /**
     * 开始动画
     */
    public static final int TYPE_START_ANIMATION = 7;

    /**
     * 停止动画
     */
    public static final int TYPE_STOP_ANIMATION = 8;

    /**
     * 登录界面动画
     */
    public static final int TYPE_LOGIN_ANIMATION=9;
    /**
     * 登录界面渐隐顶部标题
     */
    public static final int TYPE_LOGIN_SUCCESS=10;
    /**
     * 退出当前用户刷新UserFragment界面
     */
    public static final int TYPE_LOGOUT_SUCCESS=11;
    /**
     * 需要登录
     */
    public static final int TYPE_NEED_LOGIN=12;
    /**
     * 收藏刷新
     */
    public static final int TYPE_COLLECT_REFRESH = 13;
    /**
     * 收藏注销
     */
    public static final int TYPE_COLLECT_LOGOUT = 14;
    /**
     * 收藏注销
     */
    public static final int TYPE_CHANGE_MAIN_TITLE = 14;
    /**
     * 跳转到体系界面
     */
    public static final int TYPE_CHANGE_SYS = 15;
    /**
     * 跳转到导航界面
     */
    public static final int TYPE_CHANGE_NAVI = 16;
    /**
     * 跳转到项目界面
     */
    public static final int TYPE_CHANGE_PROJECT = 17;
    /**
     * 跳转到项目界面
     */
    public static final int TYPE_HOME_BACKTOTOP = 18;
    /**
     * 跳转到项目界面
     */
    public static final int TYPE_PROJECT_REFRESH = 19;
    /**
     * 刷新时是否已登录
     */
    public static final int TYPE_REFRESH_ISLOGIN = 20;
    /**
     * 刷新时是否已登录
     */
    public static final int TYPE_REFRESH_NOTLOGIN = 21;
    /**
     * 全局加载
     */
    public static final int TYPE_SYS_LOAD = 22;




    /**
     * 目标界面-MainActivity
     */
    public static final int TARGET_MAIN = 1;

    /**
     * 目标界面-目录
     */
    public static final int TARGET_MENU = 2;

    /**
     * 目标界面-首页
     */
    public static final int TARGET_HOME = 3;

    /**
     * 目标界面-体系
     */
    public static final int TARGET_TREE = 4;

    /**
     * 目标界面-项目
     */
    public static final int TARGET_PROJECT = 5;

    /**
     * 目标界面-公众号
     */
    public static final int TARGET_WX = 6;

    /**
     * 目标界面-收藏列表
     */
    public static final int TARGET_COLLECT = 7;

    /**
     * 目标界面-搜索结果
     */
    public static final int TARGET_SEARCH_RESULT = 8;
    /**
     * 目标界面-我的信息
     */
    public static final int TARGET_USER = 9;
    /**
     * 目标界面-导航体系
     */
    public static final int TARGET_SYSTEM = 10;
    /**
     * 目标界面-全局刷新
     */
    public static final int TARGET_RESFRESH = 11;



    public int target;

    public int type;

    public String data;

    public int position;

}
