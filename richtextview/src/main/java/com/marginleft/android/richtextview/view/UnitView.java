package com.marginleft.android.richtextview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.marginleft.android.richtextview.R;
import com.marginleft.android.richtextview.model.UnitBean;

/**
 * Created by Max on 2017/1/4.
 * 一个TextView和一个ImageView组成一个UnitView。
 */

public class UnitView extends FrameLayout implements View.OnClickListener {

    private EditText             mEt_describe;
    private ImageView            mIv_content;
    private UnitBean             mUnitBean;
    private OnImageClickListener mOnImageClickListener;

    public UnitView(Context context) {
        this(context, null);
    }

    public UnitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mUnitBean = new UnitBean();
        initView();
    }

    // 初始化View,并设置监听。
    private void initView() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.view_unit, this, false));

        mEt_describe = (EditText) findViewById(R.id.et_describe);
        mIv_content = (ImageView) findViewById(R.id.iv_content);

        mIv_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_content) {
            if (mOnImageClickListener != null) {
                mOnImageClickListener.onImageClick(this);
            }
        }
    }

    public void setImageBitmap(String imagePath) {
        mUnitBean.path = imagePath;
        Bitmap bitmap = getNewBitmap(BitmapFactory.decodeFile(imagePath));
        mIv_content.setImageBitmap(bitmap);
    }

    private Bitmap getNewBitmap(Bitmap bitmap) {
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        int height = bitmap.getHeight() * width / bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    // 编辑框获取焦点
    public void requestFocusToText(int index) {
        mEt_describe.setSelection(index);
    }

    // 获取用户输入数据。
    public UnitBean getUnitBean() {
        mUnitBean.describe = mEt_describe.getText().toString().trim();
        return mUnitBean;
    }

    // 显示数据。
    public void showView(UnitBean unitBean, boolean enable) {
        if (unitBean != null) {
            if (!TextUtils.isEmpty(unitBean.describe)) {
                mEt_describe.setText(unitBean.describe);
                requestFocusToText(mEt_describe.getText().toString().length());
            }
            if (!TextUtils.isEmpty(unitBean.path)) {
                setImageBitmap(unitBean.path);
            }
            if (!enable) {
                mEt_describe.setEnabled(false);
                mIv_content.setEnabled(false);
            }
        }
    }

    public interface OnImageClickListener {
        void onImageClick(UnitView unitView);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

}
