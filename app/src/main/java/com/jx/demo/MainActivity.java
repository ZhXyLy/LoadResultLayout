package com.jx.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jx.loadresultlayout.widget.LoadResultLayout;

/**
 * @author zhaoxl
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoadResultLayout loadResultLayout = findViewById(R.id.loadResultLayout);
        loadResultLayout.setReloadListener(new LoadResultLayout.OnReloadListener() {
            @Override
            public void onReload() {
                Toast.makeText(MainActivity.this, "重新加载", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResultLayout.loading();
            }
        });

        findViewById(R.id.btn_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResultLayout.success();
            }
        });

        findViewById(R.id.btn_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResultLayout.empty();
            }
        });

        findViewById(R.id.btn_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResultLayout.error();
            }
        });

        findViewById(R.id.btn_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResultLayout.reload();
            }
        });
    }
}
