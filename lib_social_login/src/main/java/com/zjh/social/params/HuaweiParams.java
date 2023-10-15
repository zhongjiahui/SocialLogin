package com.zjh.social.params;

import com.huawei.hms.support.api.entity.auth.Scope;

import java.util.List;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class HuaweiParams {

    private List<Scope> scopes;

    public List<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }
}
