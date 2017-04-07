package com.aotomdemo.lostjason.aotome206demo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.aotomdemo.lostjason.aotome206demo.etc.ETCparse;
import com.aotomdemo.lostjason.aotome206demo.log.Logshow;
import com.aotomdemo.lostjason.aotome206demo.video.VideoConstans;

import com.libs.ffmpeg.FFmpegPlayer;

public class MainActivity extends Activity implements FFmpegPlayer.onVideoLostLinkListner {
private  boolean isRun= true;
private TextView  accout;
    private FFmpegPlayer fFmpegPlayer;
    private SurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        readCard();
    }
    private void initUI() {
        accout= (TextView) findViewById(R.id.accout);
        surfaceView= (SurfaceView) findViewById(R.id.videoplay);
        surfaceView.setZOrderOnTop(false);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        fFmpegPlayer =new FFmpegPlayer(this);
        surfaceView.getHolder().addCallback(callback);
    }
    private SurfaceHolder surfaceHolder;
    public boolean isSurfaceCreated =false;
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Logshow.logshow("surfaceCreated");
            surfaceHolder = holder;
          isSurfaceCreated = true;
            if (isSurfaceCreated){
                Logshow.logshow("isSurfaceCreated");
                setMedia();
            }

        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            surfaceHolder = holder;
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };
   /*读取ETC卡余额*/
    public void readCard(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               while (isRun){
                      ETCparse.getETCCardValue(MainActivity.this,accout);

               }
           }
       }).start();
     }


    @Override
    protected void onResume() {
        super.onResume();
    }
    private String currentUri = VideoConstans.RTSP_URL1;
    @Override
    protected void onDestroy() {

        fFmpegPlayer = null;
        Logshow.logshow("onDestroy");
         super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logshow.logshow("onPause");
        fFmpegPlayer.stop();
    }

    private  void setMedia() {
        if (fFmpegPlayer==null) return;
       int s= fFmpegPlayer.setMediaUri(currentUri);
        if (surfaceHolder == null) return;
        fFmpegPlayer.setSurface(surfaceHolder.getSurface(),0,0);

    }




    @Override
    public void videoLostLink() {
        Logshow.logshow("videoLostLink");
        return;
    }
}
