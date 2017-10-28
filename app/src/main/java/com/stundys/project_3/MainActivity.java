package com.stundys.project_3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    WebView viewer;
    SharedPreferences sharedPref;
    HashMap<String, String> links = new HashMap<>();

    MainActivity() {
        links.put("youtube", "https://www.youtube.com/");
        links.put("15min",   "https://www.15min.lt/");
        links.put("delfi",   "https://www.delfi.lt/");
        links.put("empty",   "file:///android_asset/empty.html");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String current_url = sharedPref.getString(getString(R.string.current_url), "");

        viewer = findViewById(R.id.webView);
        viewer.setWebViewClient(new WebViewClient());
        WebSettings webSettings = viewer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(current_url.isEmpty()){
            resetViewer();
        } else {
            openLink(current_url);
        }
    }

    public void resetViewer(){
        openLink(links.get("empty"));
    }
    public void openDelfi(View view){
        openLink(links.get("delfi"));
    }
    public void open15Min(View view){
        openLink(links.get("15min"));
    }
    public void openYouTube(View view){
        openLink(links.get("youtube"));
    }
    public void clearCache(View view){
        resetViewer();
    }
    private void openLink(String link) {
        viewer.loadUrl(link);
        cacheLink(link);
    }
    private void cacheLink(String link){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.current_url), link);
        editor.apply();
    }
}
