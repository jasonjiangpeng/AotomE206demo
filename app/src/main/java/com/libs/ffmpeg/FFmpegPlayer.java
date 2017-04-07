package com.libs.ffmpeg;




/**
 * Created by xiaoyunfei on 16/5/12.
 */
public class FFmpegPlayer {   //JNI函数
    private onVideoLostLinkListner listner;
    private static LibUtil libUtil = LibUtil.getLibUtil();
    static {
        libUtil.loadLibs();
    }
    public FFmpegPlayer(onVideoLostLinkListner listner) {
        this.listner = listner;
    }


    public native int setMediaUri(String uri);

    public native void start();

    public native void stop();

    public native void setSurface(Object surface, int weight, int height);

    public native int getVideoWight();

    public native int getVideoheight();

    public native void takeSpanShot(Object bitMap);
    public void videoLostLink() {
        listner.videoLostLink();
    }

    public interface onVideoLostLinkListner {
        void videoLostLink();
    }

}