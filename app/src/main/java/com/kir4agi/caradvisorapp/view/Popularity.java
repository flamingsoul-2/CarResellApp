package com.kir4agi.caradvisorapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Popularity extends View {

    private int popularity;
    private Paint paint;
    private Rect rectangle;
    private final int offset = 120;
    private float drawn;

    public Popularity(Context context) {
        super(context);
        init();

    }

    public Popularity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public Popularity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        rectangle = new Rect(0 ,0,20, 110);
        drawn = 0;
    }



    public void setPopularity(int popularity) {
        this.popularity = popularity;

        paint.setColor(Color.GREEN);

        if(popularity < 6){
            paint.setColor(Color.DKGRAY);
        }
        if(popularity < 3){
            paint.setColor(Color.RED);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        incrementDrawn();

        for (int i = 0; i < drawn; i++) {
            canvas.drawRect(rectangle.left + i * offset, rectangle.top + 25, rectangle.right, rectangle.bottom, paint);
        }


        this.invalidate();

    }

    private void incrementDrawn() {
        if(drawn != popularity)
            drawn += 0.25;
    }
}

