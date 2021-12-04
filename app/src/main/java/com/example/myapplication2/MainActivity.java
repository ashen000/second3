package com.example.myapplication2;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    My adaputer;

    private Calendar c = Calendar.getInstance();
    private TextView cttime1;
    private TextView zhihu;
    private TextView cttime2;
    final List<Map<String, Object>> list = new ArrayList<>();
    int ddaa,ddaaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//默认线程，主线程
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        final RefreshLayout mRefreshLayout=findViewById(R.id.refreshLayout);

        for(int i=0;i<100;i++){
            Data.setI(0,i);
            Data.setBj(0,i);
        }

        zhihu = findViewById(R.id.textView3);
        cttime1 = findViewById(R.id.cttime2);
        Log.i("123","1");
        int month = c.get(Calendar.MONTH) + 1;
        cttime1.setText(Integer.toString(month) + "月");
        TextPaint tp1 = cttime1.getPaint();
        tp1.setFakeBoldText(true);
        cttime2 = findViewById(R.id.cttime1);
        int day = c.get(Calendar.DAY_OF_MONTH);//获取日期显示在标题栏
        ddaa=day+month*100+2021*10000;
        ddaaa=ddaa;
        cttime2.setText(Integer.toString(day));
        TextPaint tp2 = cttime2.getPaint();
        tp2.setFakeBoldText(true);
        int i = c.get(Calendar.HOUR_OF_DAY);
        if (i < 7) {
            zhihu.setText("早上好！");
        }
        if (i >= 7 && i <= 18) {
            zhihu.setText("知乎日报");
        }
        if (i > 18&&i<22) {
            zhihu.setText("晚上好！");
        }
        if(i>=22)
        {
            zhihu.setText("早点休息！");
        }
        TextPaint tp3 = zhihu.getPaint();
        tp3.setFakeBoldText(true);
        //创造子线程
        new Thread(new Runnable() {
                @Override
                public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://news-at.zhihu.com/api/3/stories/latest");
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
                    String date = jsonObject.getString("date");
                    JSONArray jsonArray = jsonObject.getJSONArray("stories");
                    for (int i =0; i <jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String title = jsonObject1.getString("title");
                        String urll = jsonObject1.getString("url");
                        JSONArray jsonArray2=jsonObject1.getJSONArray("images");
                        String images = jsonArray2.getString(0);
                        String id=jsonObject1.getString("id");
                        String hint = jsonObject1.getString("hint");
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("title", title);
                        map2.put("bj","0");
                        map2.put("id",id);
                        map2.put("hint", hint);
                        map2.put("url", urll);
                        map2.put("images", images);
                        list.add(map2);
                    }
                    //回到主线程
                    runOnUiThread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            adaputer = new My(MainActivity.this, list);
                            mRecyclerView.setAdapter(adaputer);
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
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                ddaaa=ddaa;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            URL url = new URL("https://news-at.zhihu.com/api/3/stories/latest");
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
                            String date = jsonObject.getString("date");
                            JSONArray jsonArray = jsonObject.getJSONArray("stories");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String title = jsonObject1.getString("title");
                                String urll = jsonObject1.getString("url");
                                JSONArray jsonArray2=jsonObject1.getJSONArray("images");
                                String images = jsonArray2.getString(0);
                                String id=jsonObject1.getString("id");
                                String hint = jsonObject1.getString("hint");
                                Map<String, Object> map = new HashMap<>();
                                map.put("title", title);
                                map.put("bj","0");

                                map.put("id",id);
                                map.put("hint", hint);
                                map.put("url", urll);
                                map.put("images", images);
                                list.add(map);
                            }
                            //回到主线程
                            runOnUiThread(new Runnable() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void run() {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                                    mRecyclerView.setLayoutManager(layoutManager);
                                    adaputer = new My(MainActivity.this, list);
                                    mRecyclerView.setAdapter(adaputer);
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
                adaputer.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            String uu="https://news-at.zhihu.com/api/3/news/before/"+Integer.toString(ddaaa);
                            Log.i("121",String.valueOf(ddaaa));
                            URL url = new URL(uu);
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
                            String date = jsonObject.getString("date");
                            JSONArray jsonArray = jsonObject.getJSONArray("stories");
                            Map<String, Object> map0= new HashMap<>();
                            map0.put("title"," ");
                            map0.put("id","1");
                            date=date.substring(4);
                            String date1=date.substring(0,2);
                            String date2=date.substring(2);

                            map0.put("hint",date1+"月"+date2+"日");
                            map0.put("url", "1");
                            map0.put("images", "1");
                            map0.put("bj","1");

                            list.add(map0);
                            for (int i =0; i <jsonArray.length();i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String title = jsonObject1.getString("title");
                                String urll = jsonObject1.getString("url");
                                JSONArray jsonArray2=jsonObject1.getJSONArray("images");
                                String images = jsonArray2.getString(0);
                                String id=jsonObject1.getString("id");
                                String hint = jsonObject1.getString("hint");
                                Map<String, Object> map2 = new HashMap<>();
                                map2.put("page","1");
                                map2.put("title", title);
                                Log.i("11",title);
                                map2.put("id",id);
                                map2.put("bj","0");
                                map2.put("hint", hint);
                                map2.put("url", urll);
                                map2.put("images", images);
                                list.add(map2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            ddaaa=ddaaa-1;
                            if(ddaaa%100==0)
                            {
                                int tmp=ddaaa%1000/100;
                                if(tmp==2||tmp==4||tmp==6||tmp==8||tmp==9||tmp==11)
                                {
                                    ddaaa=ddaaa-100+31;
                                }
                                else if(tmp==1){
                                    ddaaa=ddaaa+1131;
                                }
                                else if(tmp==2){
                                    ddaaa=ddaaa-100+29;
                                }
                                else if(tmp==5||tmp==7||tmp==10||tmp==12)
                                {
                                    ddaaa=ddaaa-100+30;
                                }
                            }
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
                adaputer.notifyDataSetChanged();
                mRefreshLayout.finishLoadMore(true);

}
        });

    }
}
