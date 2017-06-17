package com.mdakram28.smarthome.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.util.Preferences;

import static com.mdakram28.smarthome.util.Preferences.httpServer;

public class AddDeviceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webview);
        String token = Preferences.getAccessToken(this);
        String deviceUrl = String.format("%s/manage/addDevice?token=%s", httpServer, token);
        webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(deviceUrl);
    }
}
