package com.jerry.fanju_manager.main;

import android.app.Application;

import cn.leancloud.LeanCloud;

public class Apl extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 提供 this、App ID、绑定的自定义 API 域名作为参数
        LeanCloud.initialize(this, "HHuCWxcQFxe5dk75hlusKIN1-MdYXbMMI",
                "rsuQlDR8fcVxpRNDBQCvfNtD",
                "https://hhucwxcq.api.lncldglobal.com");
    }
}
