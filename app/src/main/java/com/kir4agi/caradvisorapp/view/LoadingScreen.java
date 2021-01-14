package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;

import androidx.annotation.Nullable;

import com.kir4agi.caradvisorapp.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class LoadingScreen extends View {

    Context context;
    GifDrawable gifDrawable;
    Matrix matrix;
    Paint gifPaint;
    public static boolean visible;

    public LoadingScreen(Context context) {
        super(context);
        init(context);
    }

    public LoadingScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public LoadingScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context){
        this.context = context;
        try {
            gifDrawable = new GifDrawable(context.getResources().openRawResource(R.raw.loading_gear));
        }catch (IOException e){
            e.printStackTrace();
        }


        gifDrawable.start();
        gifDrawable.setVisible(true,false);
        gifPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gifDrawable.setLoopCount(3000);
        matrix = new Matrix();
        matrix.setScale(3,3);



    }

    public void toggleVisibility(){
        visible = !visible;
    }

    public void setVisible(boolean visibleNew) {
        visible = visibleNew;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(gifDrawable != null && visible){
            canvas.save();
            canvas.translate(-200,-150);
                canvas.drawBitmap(gifDrawable.getCurrentFrame(),matrix, gifPaint);
            canvas.restore();

        }

        invalidate();
    }
}
