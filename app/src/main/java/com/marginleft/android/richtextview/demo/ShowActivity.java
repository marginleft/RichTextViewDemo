package com.marginleft.android.richtextview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.marginleft.android.richtextview.model.UnitBean;
import com.marginleft.android.richtextview.view.TextImageView;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity implements TextImageView.OnCompletedListener {

    public static final int RESULT_SAVE = 1001;
    private TextImageView mTiv_whole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String behavior = intent.getStringExtra(ConstanceUtil.INTENT_BEHAVIOR);
        ArrayList<UnitBean> unitBeanList = intent.getParcelableArrayListExtra(ConstanceUtil.INTENT_DATA);
        initView(behavior,unitBeanList);
    }

    private void initView(String behavior, ArrayList<UnitBean> unitBeanList) {
        mTiv_whole = (TextImageView) findViewById(R.id.tiv_whole);

        mTiv_whole.setOnCompletedListener(this);

        if (TextUtils.equals(behavior,ConstanceUtil.INTENT_EDIT)) {
            editView(unitBeanList);
        } else if (TextUtils.equals(behavior,ConstanceUtil.INTENT_PREVIEW)) {
            previewView(unitBeanList);
        }
    }

    private void editView(ArrayList<UnitBean> unitBeanList) {
        if (unitBeanList != null && !unitBeanList.isEmpty()) {
            mTiv_whole.showView(unitBeanList);
        }
    }

    private void previewView(ArrayList<UnitBean> unitBeanList) {
        if (unitBeanList != null && !unitBeanList.isEmpty()) {
            mTiv_whole.previewView(unitBeanList);
        }
    }

    @Override
    public void onCompleted(List<UnitBean> unitBeanList) {
        setResultAndFinished((ArrayList<? extends Parcelable>) unitBeanList);
    }

    @Override
    public void onCanceled(List<UnitBean> unitBeanList) {
        setResultAndFinished((ArrayList<? extends Parcelable>) unitBeanList);
    }

    private void setResultAndFinished(ArrayList<? extends Parcelable> unitBeanList) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ConstanceUtil.INTENT_DATA, unitBeanList);
        setResult(RESULT_SAVE,intent);
        finish();
    }

}
