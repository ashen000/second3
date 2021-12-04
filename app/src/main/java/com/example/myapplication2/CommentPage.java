package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentPage extends AppCompatActivity {
    private TextView textView1;
    private Button button1;
    private RecyclerView nrecyclerView;
    MySecond mySecond;
    final List<Map<String,Object>> list11=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_page);
        nrecyclerView=findViewById(R.id.recyclerview2);

        button1=findViewById(R.id.button);
        textView1=findViewById(R.id.textView);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        final String id=intent.getStringExtra("id");
        final String extra= " https://news-at.zhihu.com/api/3/story-extra/" +id;
        final String seeklongcomment="https://news-at.zhihu.com/api/4/story/"+id+"/long-comments";
        final String seekshortcomment="https://news-at.zhihu.com/api/4/story/"+id+"/short-comments";

        LinearLayoutManager layoutManager = new LinearLayoutManager(CommentPage.this);
        nrecyclerView.setLayoutManager(layoutManager);
        mySecond= new MySecond(CommentPage.this,list11);
        nrecyclerView.setAdapter(mySecond);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {

                    URL url1=new URL(extra);
                    HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
                    urlConnection.setRequestMethod("GET");
                    InputStream in = urlConnection.getInputStream();//获取输入流
                    //读取刚刚获取的输入流
                    reader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    final String comments=jsonObject.getString("comments");
                    final String long_commentsnumber=jsonObject.getString("long_comments");
                    final String short_commentsnumber=jsonObject.getString("short_comments");
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            textView1.setText(comments+"条评论");
                            if(Integer.parseInt(long_commentsnumber)>0)
                            {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpURLConnection connection = null;
                                        BufferedReader reader = null;
                                        try {
                                            URL url = new URL(seeklongcomment);
                                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                            urlConnection.setRequestMethod("GET");
                                            InputStream in = urlConnection.getInputStream();
                                            reader = new BufferedReader(new InputStreamReader(in));
                                            StringBuilder response = new StringBuilder();
                                            String line;
                                            while ((line = reader.readLine()) != null) {
                                                response.append(line);
                                            }
                                            JSONObject jsonObject = new JSONObject(response.toString());
                                            JSONArray jsonArray = jsonObject.getJSONArray("comments");
                                            Map<String,Object> map11= new HashMap<>();
                                            map11.put("author",long_commentsnumber+"条长评");
                                            map11.put("content"," ");
                                            map11.put("avatar"," ");
                                            list11.add(map11);
                                            for (int i =0; i <jsonArray.length(); i++) {
                                                Log.i("1211",String.valueOf(jsonArray.length()));
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                String author = jsonObject1.getString("author");
                                                String content = jsonObject1.getString("content");
                                                String avatar=jsonObject1.getString("avatar");
                                                Map<String,Object> map12= new HashMap<>();

                                                map12.put("author", author);
                                                map12.put("content",content);
                                                map12.put("avatar",avatar);
                                                list11.add(map12);
                                            }
                                            //回到主线程
                                            runOnUiThread(new Runnable() {
                                                @SuppressLint("WrongConstant")
                                                @Override
                                                public void run() {
                                                    mySecond.notifyDataSetChanged();
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (reader != null) {
                                                try {
                                                    reader.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();


                            //短

                            }if(Integer.parseInt(short_commentsnumber)>0)
                            {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpURLConnection connection = null;
                                        BufferedReader reader = null;
                                        try {
                                            URL url = new URL(seekshortcomment);
                                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                            urlConnection.setRequestMethod("GET");
                                            InputStream in = urlConnection.getInputStream();
                                            reader = new BufferedReader(new InputStreamReader(in));
                                            StringBuilder response = new StringBuilder();
                                            String line;
                                            while ((line = reader.readLine()) != null) {
                                                response.append(line);
                                            }
                                            JSONObject jsonObject = new JSONObject(response.toString());
                                            JSONArray jsonArray = jsonObject.getJSONArray("comments");
                                            Map<String,Object> map23= new HashMap<>();
                                            map23.put("author",short_commentsnumber+"条短评");
                                            map23.put("content"," ");
                                            map23.put("avatar","1");
                                            list11.add(map23);
                                            for (int i =0; i <jsonArray.length(); i++) {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                String author = jsonObject1.getString("author");
                                                String content = jsonObject1.getString("content");
                                                String avatar=jsonObject1.getString("avatar");
                                                Map<String,Object> map22= new HashMap<>();
                                                map22.put("author", author);
                                                map22.put("content",content);
                                                map22.put("avatar",avatar);
                                                list11.add(map22);

                                            }
                                            //回到主线程
                                            runOnUiThread(new Runnable() {
                                                @SuppressLint("WrongConstant")
                                                @Override
                                                public void run() {
                                                    mySecond.notifyDataSetChanged();
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (reader != null) {
                                                try {
                                                    reader.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();


                                //短

                            }

                        }
                    });


                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
