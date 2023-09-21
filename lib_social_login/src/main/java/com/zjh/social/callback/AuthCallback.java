package com.zjh.social.callback;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2023/9/19
 * @description
 **/
public interface AuthCallback<T> extends Serializable {

    void call(int code, String message, T data);

}
