package com.zjh.social.util;

/**
 * @author Administrator
 * @date 2023/9/20
 * @description
 **/
public class ResultCode {

    public final static int AUTH_SUCCESS = 200; // 认证成功

    public final static int ERROR_PARAMS = 10000; // 参数错误
    public final static int ERROR_ALIPAY = 10001; // Alipay auth failed
    public final static int ERROR_QQ = 10002; // QQ auth failed
    public final static int ERROR_FACEBOOK = 10003; // FaceBook auth failed
    public final static int ERROR_WEIBO = 10004; // Weibo auth failed
    public final static int ERROR_BAIDU = 10005; // 百度 auth failed
    public final static int ERROR_LINKEDIN = 10006; // Linkedin auth failed
    public final static int ERROR_GITHUB = 10007; // Github auth failed
    public final static int ERROR_GITEE = 10008; // Gitee auth failed
    public final static int ERROR_GITLAB = 10009; // GitLab auth failed
    public final static int ERROR_DOU_YIN = 10010; // 抖音 auth failed
}
