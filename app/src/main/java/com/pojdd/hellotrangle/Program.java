package com.pojdd.hellotrangle;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Program {
    private static final String TAG = "Shader";
    String VS;//顶点sharder
    String FS;//片段着色器
    int program;
    public static Context context;
    Program(int vertextShader, int fargmentShader) {
        InputStream in = context.getResources().openRawResource(vertextShader);;
        int lenght = 0;
        try {
            lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            VS =new String(buffer);

            in = context.getResources().openRawResource(fargmentShader);
            lenght = in.available();
            //创建byte数组
            buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            FS =new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.print(VS);
//        System.out.print("FS:\n"+FS);
//        complie();
    }
    private int loadShader(int type, String shaderSource) {
        //创建着色器对象
        int shader = GLES30.glCreateShader(type);
        if (shader == 0) return 0;//创建失败
        //加载着色器源
        GLES30.glShaderSource(shader, shaderSource);
        //编译着色器
        GLES30.glCompileShader(shader);
        //检查编译状态
        int[] compiled = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            return 0;//编译失败
        }
        return shader;
    }

    public void complie(){
        //获取顶点着色器
        int vertextShader = loadShader(GLES30.GL_VERTEX_SHADER, VS);
        //获取片段着色器
        int fragmentShader =loadShader(GLES30.GL_FRAGMENT_SHADER, FS);
        //创建程序
        int tmpProgram = GLES30.glCreateProgram();
        if (tmpProgram == 0) return;//创建失败
        //绑定着色器到程序
        GLES30.glAttachShader(tmpProgram, vertextShader);
        GLES30.glAttachShader(tmpProgram, fragmentShader);

        //绑定属性位置 vPosition ：0 着色器中没有设定属性位置时使用
//        GLES30.glBindAttribLocation(tmpProgram, 0, "vPosition");

        //连接程序
        GLES30.glLinkProgram(tmpProgram);
        //检查连接状态
        int[] linked = new int[1];
        GLES30.glGetProgramiv(tmpProgram,GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0){
            Log.e(TAG, "tmpProgram linked error");
            Log.e(TAG, GLES30.glGetProgramInfoLog(tmpProgram));
            GLES30.glDeleteProgram(tmpProgram);
            return;//连接失败
        }
        //保存程序，后面使用
        program = tmpProgram;
    }
    public void useProgram(){
        GLES30.glUseProgram(program);
    }
    public int getProgram(){
        return program;
    }
}
