package com.zjh.social.web.helpers;

public interface OnBasicProfileListener {
    void onDataRetrievalStart();

    void onDataSuccess(WebAuthUser linkedInUser);

    void onDataFailed(int errCode, String errMessage);

}
