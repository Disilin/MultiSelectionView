package com.example.disilin.selection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.disilin.selection.info.SubSelectionInfo;
import com.example.disilin.selection.view.SelectionDialog;
import com.example.disilin.selection.view.SelectionView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Button mBtn;
    private List<SubSelectionInfo> mSubs = new ArrayList<SubSelectionInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化布局
        initView();
    }

    private void initView() {
        setData();
        mBtn = (Button) findViewById(R.id.bt_click);

        //初始化监听事件
        initListener();
    }

    private void setData() {
        for (int i = 0; i < 3; i ++) {
            SubSelectionInfo sub = new SubSelectionInfo();
            sub.setIconName("菜单" + i);
            sub.setIconId(R.drawable.zhuanfa_weibo);
            mSubs.add(sub);
        }
        SubSelectionInfo sub3 = new SubSelectionInfo();
        sub3.setIconName("菜单" + 3);
        sub3.setIconId(R.drawable.zhuanfa_tudou);
        mSubs.add(sub3);
        SubSelectionInfo sub4 = new SubSelectionInfo();
        sub4.setIconName("菜单" + 4);
        sub4.setIconId(R.drawable.zhuanfa_weixin);
        mSubs.add(sub4);

    }

    private void initListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectionDialog.setDialog(MainActivity.this, mSubs, new SelectionView.OnMenuItemClickListener() {
                    @Override
                    public void clickPosition(int position) {
                        Toast.makeText(MainActivity.this, "点击菜单" + position, Toast.LENGTH_SHORT).show();
                        SelectionDialog.cancel();
                    }
                });
            }
        });
    }
}
