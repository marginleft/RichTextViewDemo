package com.marginleft.android.richtextview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.marginleft.android.richtextview.view.OnImageSelectCallback;

/**
 * Created by Max on 2017/1/4.
 * 用来接收用户选择的图片路径的Activity。
 */

public class ResultActivity extends AppCompatActivity {

    private static final int IMAGE = 1001;
    private static OnImageSelectCallback mOnImageSelectCallback;

    public static void startResultAcitivity(Context context, OnImageSelectCallback onImageSelectCallback) {
        Intent intent = new Intent(context, ResultActivity.class);
        context.startActivity(intent);
        mOnImageSelectCallback = onImageSelectCallback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAlbum();
    }

    // 打开系统相册。
    private void startAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent2, IMAGE);
    }

    // 把选择的图片路径传递给UnitView。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
            if (mOnImageSelectCallback != null) {
                mOnImageSelectCallback.onSelected(imagePath);
            }
        }
        finish();
    }
}
