package com.aotomdemo.lostjason.aotome206demo.etc;

import android.app.Activity;
import android.util.Xml;
import android.widget.TextView;

import com.aotomdemo.lostjason.aotome206demo.log.Logshow;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by LostJason on 2017/4/5.
 */

public class ETCparse {
    public static String parseAccounts(String xml) {
        ByteArrayInputStream tInputStringStream = null;
        try {
            if (xml != null && !xml.trim().equals("")) {
                tInputStringStream = new ByteArrayInputStream(xml.getBytes());
            }
        } catch (Exception e) {
            return null;
        }
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(tInputStringStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("CardAccount")) {
                            return parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        break;
                }
                eventType = parser.next();
            }
            tInputStringStream.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (tInputStringStream!=null){
                    tInputStringStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static  String getETCResponeData() throws IOException {  //获取解析余额
       String  result ="余额:0.00";
        String url = ETCConstants.normalCard;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String str =response.body().string();
            result  =ETCparse.parseAccounts(str);
            Logshow.logshow(str);
        }else return result;
        if (response!=null){
            response.close();
        }
        return "余额:"+result;
    }
    public  static void getETCCardValue(Activity activity,final TextView textView) {

        try {
            final  String result =  ETCparse.getETCResponeData();

          activity.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  textView.setText(result);
              }
          });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
