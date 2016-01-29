package com.deng.duobaodaodemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.SocketHandler;


public class MainActivity extends Activity {

    private EditText goodsId;
    private EditText aimPrice;
    private Button submit;
    private double price;
    private double mPrice = 0;
    private double inputP;
    private String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goodsId = (EditText) findViewById(R.id.goods_id);
        aimPrice = (EditText) findViewById(R.id.aim_price);
        submit = (Button) findViewById(R.id.submit);
        mPrice = 0;
        initView();
    }

    private void initView() {

        if (goodsId.getText() != null && aimPrice.getText() != null) {

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = goodsId.getText().toString();
                    inputP = Double.parseDouble(aimPrice.getText().toString());
                    System.out.println(id);

                    sendRequestWithHttpClient();
//                    waiting();
                }
            });
        } else {
            Toast.makeText(this, "请输入正确的商品号和价格", Toast.LENGTH_SHORT).show();
        }
    }

//    方法：发送网络请求，获取百度首页的数据。在里面开启线程

    private void sendRequestWithHttpClient() {
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                HttpClient httpCientp = new DefaultHttpClient();

                HttpGet httpGetp = new HttpGet("http://paimai.jd.com/json/current/englishquery?paimaiId=" + id + "&skuId=0&t=310933&start=0&end=9");

                try {
                    HttpResponse httpResponse = httpCientp.execute(httpGetp);
                    String p = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
//            System.out.println("pricepricepricepricepriceprice" + p);

                    int i = p.indexOf("\"price\":");
                    int j = p.indexOf(",\"userIpAddress");
                    i += 8;
//
//                    System.out.println(p.substring(i, j).toString() + "price price price price price");
                    price = Double.parseDouble(p.substring(i, j).toString());
                    System.out.println(price + "price price price price price");


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //目前价格小于预期并且当前价格是大于曾经出过的价格
                if (price < inputP && mPrice < price) {
                    price++;
                    mPrice = price;
                    sendRequestWithHttpClient1();
//                    HttpClient httpCient = new DefaultHttpClient();
//                    HttpGet httpGet = new HttpGet("http://paimai.jd.com/services/bid.action?t=906176&paimaiId=" + goodsId.getText().toString() + "&price=" + (int)price + "&proxyFlag=0&bidSource=0");
//
//                    httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//                    httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
//                    httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
//                    httpGet.addHeader("Connection", "keep-alive");
//                    httpGet.addHeader("Cookie", "user-key=099c7b09-498d-4262-aa63-6a054788b633; _tp=DvDRYyF8UKrfN97t3LYodQ%3D%3D; unick=%E7%96%AF%E7%8B%97%E6%9D%A5%E4%BA%86; _pst=1548821018_m; TrackID=1M2Qb8ieNi_zLtGs6zOu8nCkuzFSiUmoiDisF9XTqz_e3ZHF1e0M3HnmONO56izxkuhf50sxdKLlxrX22B0Cfp9kpENS9dZ7YjVIZIHKgjPG57GQog1qaogV1ZLxISxUl; pinId=_tEe7H8U9CP04HaG57Pu6w; pin=1548821018_m; __jdv=122270672|baidu|-|organic|not set; cn=0; thor=F05B93B61B1C97A2A88BE4692E99333289DDF58039BCEEA1C1A09EC41B2E6BBA9F1E5DF069A8A42C1D8DA5C951CE71B38CBB884B37B86C0FB76AE1CA4E335827FE128AEA0E26B9E7DD18AB5953732D5FBEF09CAF630E2046B6856CEDD2C02D82A44ACBDCC09BD579444C8C428A6E3B7AA592021D0646966E9ACB787934BB8D049723EFF76C8573DAD70A7529B6550871; __jda=122270672.858362558.1454041009.1454041009.1454045652.2; __jdb=122270672.244.858362558|2.1454045652; __jdc=122270672; __jdu=858362558");
//                    httpGet.addHeader("Host", "paimai.jd.com");
//                    httpGet.addHeader("Referer", "http://paimai.jd.com/" + id);
//                    httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");
//                    httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
//                    //获取请求头，并用Header数组接收
//                    Header[] reqHeaders = httpGet.getAllHeaders();
//                    //遍历Header数组，并打印出来
//                    for (int i = 0; i < reqHeaders.length; i++) {
//                        String name = reqHeaders[i].getName();
//                        String value = reqHeaders[i].getValue();
//                        Log.d("http01", "Http request: Name--->" + name + ",Value--->" + value);
//                    }
//
//                    try {
//                        HttpResponse httpResponse = httpCient.execute(httpGet);
//
////                    //获取响应头，并用Header数组接收
////                    Header [] responseHeaders = httpResponse.getAllHeaders();
////                    //遍历Header数组，并打印出来
////                    for (int i = 0; i < responseHeaders.length; i++) {
////                        String name = responseHeaders[i].getName();
////                        String value = responseHeaders[i].getValue();
////                        Log.d("http01", "Http response: Name--->" + name + ",Value--->" + value);
////                    }
////
////                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
////                        HttpEntity entity = httpResponse.getEntity();
////                        String response = EntityUtils.toString(entity,"utf-8");//将entity当中的数据转换为字符串
////
////
////                    }
//
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                }
            }
        }, 10, 200
        );



    }

//    private void getPriceList() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }).start();//这个start()方法不要忘记了
//    }

    /**
     * 定时器
     */
    //计时器
    private void waiting() {
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                final Timer timer = new Timer(true);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
//                        getPriceList();
                        Object[] objects = new Object[2];
//                        objects[0] = i[0];
//                        objects[1] = timer;
//                        publishProgress(price);
                        ///////////
                        HttpClient httpCient = new DefaultHttpClient();

                        HttpGet httpGet1 = new HttpGet("http://paimai.jd.com/json/current/englishquery?paimaiId=" + id + "&skuId=0&t=310933&start=0&end=9");

                        try {
                            HttpResponse httpResponse = httpCient.execute(httpGet1);
                            String p = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                            System.out.println("aaaaaaaaaaaaaaaaaaaaaa+--------" + p);

                            int i = p.indexOf("\"price\":");
                            int j = p.indexOf(",\"userIpAddress");
                            i += 8;
//
                            System.out.println(p.substring(i, j).toString() + "[][][[]]][][][][][][][][][]");
                            price = Double.parseDouble(p.substring(i, j).toString());
                            System.out.println(price + ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
                            publishProgress(price);


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //////////
                    }
                }, 0, 1000);
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] p) {

                Toast.makeText(MainActivity.this, "加价成功！当前价格为:" + price, Toast.LENGTH_SHORT).show();

            }
        };

        asyncTask.execute();

    }

    //方法：发送网络请求，获取百度首页的数据。在里面开启线程
    private void sendRequestWithHttpClient1() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //用HttpClient发送请求，分为五步
                HttpClient httpCient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://paimai.jd.com/services/bid.action?t=906176&paimaiId=" + goodsId.getText().toString() + "&price=" + (int)price + "&proxyFlag=0&bidSource=0");

                httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
                httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
                httpGet.addHeader("Connection", "keep-alive");
                httpGet.addHeader("Cookie", "user-key=099c7b09-498d-4262-aa63-6a054788b633; _tp=DvDRYyF8UKrfN97t3LYodQ%3D%3D; unick=%E7%96%AF%E7%8B%97%E6%9D%A5%E4%BA%86; _pst=1548821018_m; TrackID=1M2Qb8ieNi_zLtGs6zOu8nCkuzFSiUmoiDisF9XTqz_e3ZHF1e0M3HnmONO56izxkuhf50sxdKLlxrX22B0Cfp9kpENS9dZ7YjVIZIHKgjPG57GQog1qaogV1ZLxISxUl; pinId=_tEe7H8U9CP04HaG57Pu6w; pin=1548821018_m; __jdv=122270672|baidu|-|organic|not set; cn=0; thor=F05B93B61B1C97A2A88BE4692E99333289DDF58039BCEEA1C1A09EC41B2E6BBA9F1E5DF069A8A42C1D8DA5C951CE71B38CBB884B37B86C0FB76AE1CA4E335827FE128AEA0E26B9E7DD18AB5953732D5FBEF09CAF630E2046B6856CEDD2C02D82A44ACBDCC09BD579444C8C428A6E3B7AA592021D0646966E9ACB787934BB8D049723EFF76C8573DAD70A7529B6550871; __jda=122270672.858362558.1454041009.1454041009.1454045652.2; __jdb=122270672.244.858362558|2.1454045652; __jdc=122270672; __jdu=858362558");
                httpGet.addHeader("Host", "paimai.jd.com");
                httpGet.addHeader("Referer", "http://paimai.jd.com/" + goodsId.getText().toString());
                httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36");
                httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
                //获取请求头，并用Header数组接收
                Header[] reqHeaders = httpGet.getAllHeaders();
                //遍历Header数组，并打印出来
                for (int i = 0; i < reqHeaders.length; i++) {
                    String name = reqHeaders[i].getName();
                    String value = reqHeaders[i].getValue();
                    Log.d("http01", "Http request: Name--->" + name + ",Value--->" + value);
                }

                try {
                    HttpResponse httpResponse = httpCient.execute(httpGet);

//                    //获取响应头，并用Header数组接收
//                    Header [] responseHeaders = httpResponse.getAllHeaders();
//                    //遍历Header数组，并打印出来
//                    for (int i = 0; i < responseHeaders.length; i++) {
//                        String name = responseHeaders[i].getName();
//                        String value = responseHeaders[i].getValue();
//                        Log.d("http01", "Http response: Name--->" + name + ",Value--->" + value);
//                    }
//
//                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                        HttpEntity entity = httpResponse.getEntity();
//                        String response = EntityUtils.toString(entity,"utf-8");//将entity当中的数据转换为字符串
//
//
//                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();//这个start()方法不要忘记了

    }

    private void getPrice() {
        HttpClient httpCient = new DefaultHttpClient();

        HttpGet httpGet1 = new HttpGet("http://paimai.jd.com/json/current/englishquery?paimaiId=" + id + "&skuId=0&t=310933&start=0&end=9");

        try {
            HttpResponse httpResponse = httpCient.execute(httpGet1);
            String p = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            System.out.println("aaaaaaaaaaaaaaaaaaaaaa+--------" + p);

            int i = p.indexOf("\"price\":");
            int j = p.indexOf(",\"userIpAddress");
            i += 8;
//
            System.out.println(p.substring(i, j).toString() + "[][][[]]][][][][][][][][][]");
            price = Double.parseDouble(p.substring(i, j).toString());
            System.out.println(price + ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;");


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
