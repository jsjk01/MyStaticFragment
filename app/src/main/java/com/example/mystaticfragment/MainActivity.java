package com.example.mystaticfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.media.Image;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String[] top_app = new String[]{"抖音短视频", "QQ浏览器", "百度网盘", "酷狗音乐", "今日头条", "哔哩哔哩", "喜马拉雅", "百度", "全民K歌"};
    int[] top_app_icon = new int[]{R.drawable.douyin, R.drawable.qqbrowser, R.drawable.baiduwangpan, R.drawable.kugou,
            R.drawable.toptitle, R.drawable.bilibili, R.drawable.ting, R.drawable.baidu, R.drawable.ktv};
    String[] urls = new String[]{"https://appstore.huawei.com/app/C10652857","https://appstore.huawei.com/app/C20679","https://appstore.huawei.com/app/C174391","https://appstore.huawei.com/app/C3466",
            "https://appstore.huawei.com/app/C57236","https://appstore.huawei.com/app/C10125085","https://appstore.huawei.com/app/C10010188",
            "https://appstore.huawei.com/app/C31346","https://appstore.huawei.com/app/C10183952"};

    private ListView app_list;
    private ArrayList<Map<String, Object>> listview;
    private TextView app_name;
    private ImageView app_icon;
    private SimpleAdapter adapter;
    private MyFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        app_icon = findViewById(R.id.app_icon);
        app_list = findViewById(R.id.app_list);
        listview = new ArrayList<>();
        for(int i = 0; i < top_app.length; i ++){
            Map<String, Object> map = new HashMap();
            map.put("appicon",top_app_icon[i]);
            listview.add(map);
        }
        adapter = new SimpleAdapter(getApplicationContext(), listview, R.layout.app_list_style, new String[]{"appicon"}, new int[]{R.id.app_icon});
        app_list.setAdapter(adapter);
        app_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment = (MyFragment) fragmentManager.findFragmentById(R.id.ly_fragment);
                WebView web = fragment.getView().findViewById(R.id.webview);
                WebSettings webSettings = web.getSettings();
                webSettings.setDomStorageEnabled(true);
                webSettings.setLoadsImagesAutomatically(true);
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAppCacheEnabled(true);
                web.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed();
                    }
                });
                web.loadUrl(urls[position]);
            }
        });
    }
}
