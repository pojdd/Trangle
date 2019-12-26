package com.pojdd.hellotrangle;

import static android.opengl.Matrix.orthoM;

public class Camera {
    public static float roate;
    public static float sx=0,sy=0;//缩放
    public static float tx=0,ty=0;//位置
    public static final float[] projectionMatrix = new float[16];
    public static int w=0,h=0;
    public static void moveto(float x,float y){
        tx=x;
        ty=y;
    }
    public static void rmove(float rx,float ry){
        tx+=rx;
        ty+=ry;
    }
    public static void scale(float x,float y){
        sx=x;
        sy=y;
    }
    public static void Setroate(float ro){
        roate=ro;
    }
    public static void Addroate(float ro){
        roate+=ro;
    }
    public static void reset(int width,int height){
        w=width;h=height;
        float aspectRatio = width > height ?
                (float)width / (float)height : (float)height / (float)width;
        if (width > height){
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }else {
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }
}
