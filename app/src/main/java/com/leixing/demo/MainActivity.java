package com.leixing.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author leixing
 */
public class MainActivity extends AppCompatActivity {

    private Button btRecyclerAdapter;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btRecyclerAdapter = findViewById(R.id.bt_recycler_adapter);
        btRecyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerAdapterActivity.start(mActivity);
            }
        });
    }
}
