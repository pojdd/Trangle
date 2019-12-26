package com.pojdd.hellotrangle;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DemoRender implements GLSurfaceView.Renderer {
    private static final String TAG = "rander";
    private Trangle trangle;
    public DemoRender() {
        trangle=new Trangle();
        trangle.init();
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Trangle.comple();//编译shader
//        GLES30.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
//        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置适口尺寸
        GLES30.glViewport(0,0,width,height);
        Log.d(TAG, "onSurfaceChanged: "+width+" "+height);
        Camera.reset(width,height);
    }
    FPS fps=new FPS();
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        trangle.Rander();
        fps.test();
    }
}