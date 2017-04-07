package com.libs.ffmpeg;

/**
 * Created by xiaoyunfei on 16/5/12.
 */
public class LibUtil {   //加载JNI库
    private static LibUtil libUtil = null;
    public static LibUtil getLibUtil() {
        if (libUtil == null) {
            libUtil = new LibUtil();
        }
        return libUtil;
    }
    public void loadLibs() {
        System.loadLibrary("avutil-55");
        System.loadLibrary("swscale-4");
        System.loadLibrary("swresample-2");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avformat-57");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("ffmpeg");
    }
}
