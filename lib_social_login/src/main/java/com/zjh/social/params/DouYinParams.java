package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class DouYinParams {

    private String clientKey;
    private String scope;
    private String state;
    private String callerLocalEntry;

    public DouYinParams() {
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCallerLocalEntry() {
        return callerLocalEntry;
    }

    public void setCallerLocalEntry(String callerLocalEntry) {
        this.callerLocalEntry = callerLocalEntry;
    }
}
