package com.marginleft.android.richtextview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Max on 2017/1/4.
 */

public class UnitBean implements Parcelable {
    public String describe;
    public String path;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.describe);
        dest.writeString(this.path);
    }

    public UnitBean() {
    }

    protected UnitBean(Parcel in) {
        this.describe = in.readString();
        this.path = in.readString();
    }

    public static final Creator<UnitBean> CREATOR = new Creator<UnitBean>() {
        @Override
        public UnitBean createFromParcel(Parcel source) {
            return new UnitBean(source);
        }

        @Override
        public UnitBean[] newArray(int size) {
            return new UnitBean[size];
        }
    };
}
