package com.marginleft.android.richtextview.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marginleft.android.richtextview.R;
import com.marginleft.android.richtextview.activity.ResultActivity;
import com.marginleft.android.richtextview.model.UnitBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2017/1/4.
 * 富文本组件
 */

public class TextImageView extends FrameLayout implements View.OnClickListener, UnitView.OnImageClickListener {

    private LinearLayout mLl_container;
    private Button       mBtn_add;
    private Button       mBtn_complete;
    private List<UnitBean> mUnitBeanList = new ArrayList<>(); // 记录所有文本和图片数据。
    private OnCompletedListener mOnCompletedListener; // 用户完成添加数据监听。
    private static final int ERROR_TEXT  = 1001;
    private static final int ERROR_IMAGE = 1002;
    private static final int READY       = 1003;
    private Button mBtn_cancel;

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        addUnitView();
    }

    // 初始化View，并设置监听。
    private void initView() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.view_text_image, this, false));
        mLl_container = (LinearLayout) findViewById(R.id.ll_container);
        mBtn_add = (Button) findViewById(R.id.btn_add);
        mBtn_complete = (Button) findViewById(R.id.btn_complete);
        mBtn_cancel = (Button) findViewById(R.id.btn_cancel);

        mBtn_add.setOnClickListener(this);
        mBtn_complete.setOnClickListener(this);
        mBtn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int state = judgeUitView();
        if (id == R.id.btn_add) {
            toDoByState(state, true);
        } else if (id == R.id.btn_complete) {
            toDoByState(state, false);
        } else if (id == R.id.btn_cancel) {
            saveToList();
            if (mOnCompletedListener != null) {
                mOnCompletedListener.onCanceled(mUnitBeanList);
            }
        }
    }

    // 判断输入数据是否为空，并添加或者上传数据。
    private int judgeUitView() {
        int childCount = mLl_container.getChildCount();
        UnitBean unitBean = ((UnitView) mLl_container.getChildAt(childCount - 2)).getUnitBean();
        if (unitBean.describe == null || TextUtils.isEmpty(unitBean.describe)) {
            return ERROR_TEXT;
        } else if (unitBean.path == null || TextUtils.isEmpty(unitBean.path)) {
            return ERROR_IMAGE;
        } else {
            return READY;
        }
    }

    // 根据状态判断是否添加编辑或者完成编辑。
    private void toDoByState(int state, boolean isAdd) {
        if (state == ERROR_TEXT) {
            Toast.makeText(getContext(), "还未填写文字", Toast.LENGTH_SHORT).show();
        } else if (state == ERROR_IMAGE) {
            Toast.makeText(getContext(), "还未选择图片", Toast.LENGTH_SHORT).show();
        } else if (state == READY) {
            if (isAdd) {
                addUnitView();
            } else {
                saveToList();
                if (mOnCompletedListener != null) {
                    mOnCompletedListener.onCompleted(mUnitBeanList);
                }
            }
        }
    }

    // 把所有编辑的数据保存在集合中。
    private void saveToList() {
        mUnitBeanList.clear();
        for (int i = 0; i < (mLl_container.getChildCount() - 1); i++) {
            UnitView unitView = (UnitView) mLl_container.getChildAt(i);
            mUnitBeanList.add(unitView.getUnitBean());
        }
    }

    // 添加一个UnitView。
    private UnitView addUnitView() {
        int childCount = mLl_container.getChildCount();
        UnitView unitView = new UnitView(getContext());
        unitView.setOnImageClickListener(this);
        unitView.requestFocusToText(0);
        mLl_container.addView(unitView, childCount - 1);
        return unitView;
    }

    // 显示数据。
    public void showView(List<UnitBean> unitBeanList) {
        if (unitBeanList != null && !unitBeanList.isEmpty()) {
            mLl_container.removeViews(0, mLl_container.getChildCount() - 1);
            for (UnitBean unitBean : unitBeanList) {
                addUnitView().showView(unitBean,true);
            }
        }
    }

    // 预览数据。
    public void previewView(List<UnitBean> unitBeanList) {
        if (unitBeanList != null && !unitBeanList.isEmpty()) {
            mLl_container.removeAllViews();
            for (UnitBean unitBean : unitBeanList) {
                addUnitView().showView(unitBean,false);
            }
        }
    }

    @Override
    public void onImageClick(final UnitView unitView) {
        ResultActivity.startResultAcitivity(getContext(), new OnImageSelectCallback() {
            @Override
            public void onSelected(String imagePath) {
                unitView.setImageBitmap(imagePath);
            }
        });
    }

    public interface OnCompletedListener {
        void onCompleted(List<UnitBean> unitBeanList);

        void onCanceled(List<UnitBean> unitBeanList);
    }

    public void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        mOnCompletedListener = onCompletedListener;
    }
}
