package com.pojdd.hellotrangle;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.translateM;

public class DemoRender implements GLSurfaceView.Renderer {
    private static final String TAG = "rander";
    private final FloatBuffer verticesBuffer;
    private Program TrangleProgram;
    //顶点，按逆时针顺序排列
    private static final float[] vertices = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f};
    //顶点颜色
    private static final float[] verticeColors = {
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };
    private final FloatBuffer verticeColorsBuffer;

    public DemoRender() {
        //将顶点数据拷贝映射到 native 内存中，以便opengl能够访问
        verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * 4)//直接分配 native 内存，不会被gc
                .order(ByteOrder.nativeOrder())//和本地平台保持一致的字节序（大/小头）
                .asFloatBuffer();//将底层字节映射到FloatBuffer实例，方便使用
        verticesBuffer
                .put(vertices)//将顶点拷贝到 native 内存中
                .position(0);//每次 put position 都会 + 1，需要在绘制前重置为0

        //将顶点颜色数据拷贝映射到 native 内存中，以便opengl能够访问

        verticeColorsBuffer = ByteBuffer
                .allocateDirect(verticeColors.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        verticeColorsBuffer
                .put(verticeColors)
                .position(0);
        TrangleProgram=new Program(R.raw.tranglevs,R.raw.tranglefs);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        TrangleProgram=new Program(R.raw.tranglevs,R.raw.tranglefs);
        TrangleProgram.complie();
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    public final float[] projectionMatrix = new float[16];

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置适口尺寸
        GLES30.glViewport(0,0,width,height);
        float aspectRatio = width > height ?
                (float)width / (float)height : (float)height / (float)width;
        if (width > height){
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }else {
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        //使用程序
        TrangleProgram.useProgram();

        //获取 vPosition 属性的位置
        int vposition = GLES30.glGetAttribLocation(TrangleProgram.getProgram(), "vPosition");
        //加载顶点数据到 vPosition 属性位置
        GLES30.glVertexAttribPointer(vposition,3,GLES30.GL_FLOAT,false,0,verticesBuffer);
        GLES30.glEnableVertexAttribArray(vposition);

        //获取 vColor 属性位置
        int aColor = GLES30.glGetAttribLocation(TrangleProgram.getProgram(), "aColor");
        //加载顶点颜色数据到 vColor 属性位置
        GLES30.glEnableVertexAttribArray(aColor);
        GLES30.glVertexAttribPointer(aColor, 4, GLES30.GL_FLOAT, false, 0, verticeColorsBuffer);
        int matrixLoc = glGetUniformLocation(TrangleProgram.getProgram(), "matrix");
        glUniformMatrix4fv(matrixLoc, 1, false, projectionMatrix, 0);
        //绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,3);
    }

}