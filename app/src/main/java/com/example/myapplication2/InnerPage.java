package com.example.myapplication2;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class InnerPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_page);
        final Intent intent = getIntent();
        WebView webView;
        Button button1, button2;
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        final TextView textView1;
        textView1=findViewById(R.id.comments);
        webView = findViewById(R.id.webview);
        final TextView textView22;
        textView22=findViewById(R.id.likess);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(intent.getStringExtra("url"));
        final String id = intent.getStringExtra("id");
        int number=Integer.valueOf(id);
        final int number2=number%100;
        final String extra = " https://news-at.zhihu.com/api/3/story-extra/" + id;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(extra);
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
                    final String comments = jsonObject.getString("comments");
                    final String popularity = jsonObject.getString("popularity");
                    //回到主线程
                    runOnUiThread(new Runnable() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void run() {
                            textView1.setText(comments);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if(Data.getBJ(number2)==0)
        {
            Data.setBj(1,number2);
            Data.setI(0,number2);
        }
        textView22.setSelected(Data.getI(number2)==1);

        textView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Data.getI(number2)==0)
                {
                    Data.setI(1,number2);
                }
                else Data.setI(0,number2);
                textView22.setSelected(Data.getI(number2)==1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(InnerPage.this, CommentPage.class);
                intent2.putExtra("id",id);
                startActivity(intent2);
            }
        });
    }
}
