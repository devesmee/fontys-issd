package com.example.issd_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {

    private static boolean authComplete = false;
    private static String authCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);

        WebView web = (WebView) findViewById(R.id.webv_fr);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                if (authComplete){
                    startActivity(intentMainActivity);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("access_token=") && !authComplete) {
                    authCode = url.split("#access_token=", 2)[1].split("&", 2)[0];
                    authComplete = true;
                    startActivity(intentMainActivity);
                } else if(url.contains("error=access_denied")){
                    Log.i("", "ACCESS_DENIED_HERE");
                    authComplete = true;
                }
            }
        });

        String CLIENT_ID = "i874073-no-production";
        String REDIRECT_URI="https://tas.fhict.nl/oob.html";
        String OAUTH_URL ="https://identity.fhict.nl/connect/authorize";
        String OAUTH_SCOPE="fhict fhict_personal";
        web.loadUrl(OAUTH_URL+"?redirect_uri="+REDIRECT_URI+"&response_type=token&client_id="+CLIENT_ID+"&scope="+OAUTH_SCOPE);
    }
}