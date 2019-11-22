package com.pojdd.hellotrangle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private static final String TAG = "opengl-demos";
    private static final int GL_VERSION = 3;

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 GLSurfaceView
        glSurfaceView = new GLSurfaceView(this);
        //检验是否支持 opengles3.0
        if (!checkGLVersion()){
            Log.e(TAG, "not supported opengl es 3.0+");
            finish();
        }
        Program.context=this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //使用 opengles 3.0
        glSurfaceView.setEGLContextClientVersion(GL_VERSION);
        //设置渲染器
        glSurfaceView.setRenderer(new DemoRender());
        //将 GLSurfaceView 显示到 Activity
        setContentView(glSurfaceView);
    }

    private boolean checkGLVersion(){
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        return ci.reqGlEsVersion >= 0x30000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //执行渲染
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停渲染
        glSurfaceView.onPause();
    }
}
