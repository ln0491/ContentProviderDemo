package com.liu.contentproviderdemo.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class L {

    public static boolean isDebug =true;

    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }
}
