package com.sangjun.mhp.peace;

import android.app.Application;

import com.kakao.auth.KakaoSDK;

/**
 * Created by Sangjun on 2017-10-15.
 */

public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;

    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
