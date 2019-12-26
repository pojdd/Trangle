package com.pojdd.hellotrangle;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.Matrix.orthoM;
import static java.lang.Math.sin;

public class Trangle {
    float sx=0.1f,sy=0.1f;
    float tx=0.1f,ty=0.1f;
    float roate=.0f;
    public static Program TrangleProgram=new Program(R.raw.tranglevs,R.raw.tranglefs);//加载shader;
    private FloatBuffer verticesBuffer;

    //顶点，按逆时针顺序排列
    private static final float[] vertices = {
            0f, 1f, 0.0f,
            -1f, -1f, 0.0f,
            1f, -1f, 0.0f
    };
    public void init(){
        //将顶点数据拷贝映射到 native 内存中，以便opengl能够访问
        verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * 4)//直接分配 native 内存，不会被gc
                .order(ByteOrder.nativeOrder())//和本地平台保持一致的字节序（大/小头）
                .asFloatBuffer();//将底层字节映射到FloatBuffer实例，方便使用
        verticesBuffer
                .put(vertices)//将顶点拷贝到 native 内存中
                .position(0);//每次 put position 都会 + 1，需要在绘制前重置为0
    }
    public void Rander(){
        //使用程序
        TrangleProgram.useProgram();

        //获取 vPosition 属性的位置
        int vposition = GLES30.glGetAttribLocation(TrangleProgram.getProgram(), "vPosition");
        //加载顶点数据到 vPosition 属性位置
        GLES30.glVertexAttribPointer(vposition,3,GLES30.GL_FLOAT,false,0,verticesBuffer);
        GLES30.glEnableVertexAttribArray(vposition);

        TrangleProgram.setUniformMatrix4fv("matrix",1,false,Camera.projectionMatrix,0);

        long t1 = System.currentTimeMillis();
        t1%=10000000;
        double t=t1/1000.0f;
        t=sin(t)/2+0.5f;
        //Camera.Addroate((float) t);
        TrangleProgram.setUniform1f("time",(float)t);//设置时间
        TrangleProgram.setUniform4f("st",sx-Camera.sx,sy-Camera.sy,tx-Camera.tx,ty-Camera.ty);//设置缩放和位移
        TrangleProgram.setUniform1f("angle", roate-Camera.roate);//设置旋转角
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,3);//绘制三角形
    }
    public static void comple(){
        TrangleProgram.complie();//编译shader
    }

    public void moveto(float x,float y){
        sx=x;
        sy=y;
    }
    public void rmove(float rx,float ry){
        sx+=rx;
        sy+=ry;
    }
    public void scale(float x,float y){
        tx=x;
        ty=y;
    }
    public void Setroate(float ro){
        roate=ro;
    }
    public void Addroate(float ro){
        roate+=ro;
    }
}
