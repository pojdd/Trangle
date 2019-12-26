package com.pojdd.hellotrangle;

import android.opengl.GLES30;
import android.util.Log;

public class FPS {
    private static final String TAG = "FPS";
    long st=System.currentTimeMillis();
    long ed=System.currentTimeMillis();
    long fps=0;
    long fpsl=0;
    FPS(){

    }
    void test(){
        ed=System.currentTimeMillis();
        fps++;
        if(ed-st>5000){
            st=ed;
            fpsl=fps/5;
            Log.d(TAG, "帧率:"+fpsl);
            fps=0;
        }
    }
}
