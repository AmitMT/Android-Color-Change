package com.example.threadstry;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.Arrays;
import java.util.Random;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.core.graphics.ColorUtils;

public class ColorSquare extends androidx.appcompat.widget.AppCompatTextView {

    int color = Color.rgb(0, 0, 0);
    Handler handler = new Handler();
    boolean changing = true;
    Thread thread;

    public ColorSquare(Context context, AttributeSet attributeSet) {
        this(context);
    }

    public ColorSquare(Context context) {
        super(context);

        setText(getResources().getString(R.string.white));
        setBackgroundColor(color);
        setTextSize(50);

        thread = createThread();
        thread.start();

        setOnClickListener((View view) -> {
            if (changing) {
                thread.interrupt();
                changing = false;

            } else {
                thread = createThread();
                thread.start();
                changing = true;
            }
        });


    }

    Thread createThread() {
        return new Thread(() -> {
            while (true) {
                Random rnd = new Random();
                color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                double contrast = ColorUtils.calculateContrast(color, Color.BLACK);
                handler.post(() -> {
                    setText(String.format("#%06X", (0xFFFFFF & color)));
                    setTextColor(contrast > 10 ? Color.BLACK : Color.WHITE);
                    setBackgroundColor(color);
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("TAG", "stopp");
                    return;
                }
            }
        });
    }
}
