package com.marginleft.android.richtextview.utils;

import android.content.Context;
import android.util.SparseArray;

import com.marginleft.android.richtextview.view.UnitView;

/**
 * Created by Max on 2017/1/4.
 * UnitView的工厂类。
 */

public class UnitViewFactory {

    private static SparseArray<UnitView> sUnitViewSparseArray = new SparseArray<>();

    public static UnitView getUnitViewById(Context context, int id) {
        UnitView unitView = sUnitViewSparseArray.get(id);
        if (unitView == null) {
            unitView = new UnitView(context);
            sUnitViewSparseArray.put(id,unitView);
        }
        return unitView;
    }

    public static int getId(UnitView unitView) {
        return sUnitViewSparseArray.indexOfValue(unitView);
    }
}
