package com.example.onlinestore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class VisitUs extends AppCompatActivity {
    private VisitUsViewModel galleryViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitus);


        galleryViewModel = new ViewModelProvider(this).get(VisitUsViewModel.class);
        WebView webView = (WebView) findViewById(R.id.web_view_practice);
        webView.loadUrl("http://mohamad6719-001-site1.ctempurl.com/");
        webView.setWebViewClient(new WebViewController());

    }
}