package com.marginleft.android.richtextview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.marginleft.android.richtextview.model.UnitBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_EDIT = 1001;
    private Button              mBtn_edit;
    private Button              mBtn_preview;
    private ArrayList<UnitBean> mUnitBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn_edit = (Button) findViewById(R.id.btn_edit);
        mBtn_preview = (Button) findViewById(R.id.btn_preview);

        mBtn_edit.setOnClickListener(this);
        mBtn_preview.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ShowActivity.class);
        switch (v.getId()) {
            case R.id.btn_edit:
                intent.putExtra(ConstanceUtil.INTENT_BEHAVIOR, ConstanceUtil.INTENT_EDIT);
                intent.putParcelableArrayListExtra(ConstanceUtil.INTENT_DATA, mUnitBeanList);
                startActivityForResult(intent, REQUEST_EDIT);
                break;
            case R.id.btn_preview:
                if (isCompleted()) {
                    intent.putExtra(ConstanceUtil.INTENT_BEHAVIOR, ConstanceUtil.INTENT_PREVIEW);
                    intent.putParcelableArrayListExtra(ConstanceUtil.INTENT_DATA, mUnitBeanList);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请先完成对富文本的编辑", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isCompleted() {
        if ((mUnitBeanList != null && !mUnitBeanList.isEmpty())) {
            int i = 0;
            for (UnitBean unitBean : mUnitBeanList) {
                if (unitBean.describe == null || unitBean.path == null || TextUtils.isEmpty(unitBean.describe) || TextUtils.isEmpty(unitBean.path)) {
                    i++;
                }
            }
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == ShowActivity.RESULT_SAVE) {
            mUnitBeanList = data.getParcelableArrayListExtra(ConstanceUtil.INTENT_DATA);
        }
    }
}
