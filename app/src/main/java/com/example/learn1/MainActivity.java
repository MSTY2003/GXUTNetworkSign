package com.example.learn1;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    private String[] stArray = {"免费校园网","中国电信","中国移动","中国联通"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = findViewById(R.id.stuPSWEditText);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        initspinner();

        FileHelper fileHelper = new FileHelper();


        findViewById(R.id.button2).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText accEditText = findViewById(R.id.stuNumEditText);
                EditText pswEditText = findViewById(R.id.stuPSWEditText);
                Spinner spinner = findViewById(R.id.sp);
                String yys = "",temp = spinner.getSelectedItem().toString();
                switch (temp)
                {
                    case "免费校园网":
                        yys = "";
                        break;
                    case "中国电信":
                        yys = "@telecom";
                        break;
                    case "中国移动":
                        yys = "@cmcc";
                        break;
                    case "中国联通":
                        yys = "@unicom";
                        break;
                }
                Toast.makeText(MainActivity.this,accEditText.getText().toString()+" "+pswEditText.getText().toString(),Toast.LENGTH_SHORT).show();

                final EditText et1 = accEditText;
                final EditText et2 = pswEditText;
                final String yysTemp = yys;

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try
                        {
                            sendPost(et1.getText().toString(),et2.getText().toString(),yysTemp);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                }).start();
            }
        });
    }

    private void initspinner()
    {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.select_item,stArray);
        stringArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Spinner sp = findViewById(R.id.sp);
        sp.setPrompt("选择运营商");
        sp.setAdapter(stringArrayAdapter);
        //sp.setSelection(0);
        sp.setOnItemSelectedListener(new SpinnerSelectedListenner());
    }

    class SpinnerSelectedListenner implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this,"您选择的是："+stArray[i],Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void sendPost(String acc,String psw,String netWay) throws Exception {

        String url = "http://172.16.1.11";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //添加请求头
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        con.setRequestProperty("Cache-Control", "max-age=0");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Host", "172.16.1.11");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1 Edg/99.0.4844.74");



        //请求内容
        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        String urlParameters = "DDDDD="+acc+netWay+"&"+"upass="+psw+"&"+"R1=0&R3=0&R6=0&pare=00&0MKKey=123456";

        //发送Post请求
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //打印结果
        System.out.println(response.toString());

    }

}
