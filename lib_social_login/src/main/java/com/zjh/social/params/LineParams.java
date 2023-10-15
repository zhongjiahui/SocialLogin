package com.zjh.social.params;

import com.linecorp.linesdk.Scope;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LineParams {

    private String channelID;
    private ArrayList<Scope> scopes;

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public ArrayList<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(ArrayList<Scope> scopes) {
        this.scopes = scopes;
    }
}
