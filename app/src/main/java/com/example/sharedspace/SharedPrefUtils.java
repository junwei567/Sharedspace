package com.example.sharedspace;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefUtils {
    public static boolean saveArray(Context mContext, List<String> l)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
        mEdit1.putInt("Status_size", l.size());

        for(int i=0; i<l.size(); i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, l.get(i));
        }

        return mEdit1.commit();
    }

    public static List<String> loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        List<String> output = new ArrayList<>();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0; i<size; i++)
        {
            output.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return output;
    }
}
