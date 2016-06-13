package com.zzptc.LiuXiaolong.baidu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zzptc.LiuXiaolong.baidu.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_general__setting)
public class General_Setting extends AppCompatActivity {
    @ViewInject(R.id.btn_1)
    Button btn_1;
    @ViewInject(R.id.btn_2)
    Button btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn_1,R.id.btn_2})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.btn_1:
                Toast.makeText(General_Setting.this, btn_1.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_2:
                Toast.makeText(General_Setting.this, btn_2.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
