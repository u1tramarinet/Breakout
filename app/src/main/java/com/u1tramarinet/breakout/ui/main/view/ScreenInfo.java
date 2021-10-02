package com.u1tramarinet.breakout.ui.main.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

class ScreenInfo extends DrawObject {
    private int width;
    private int height;

    ScreenInfo(int width, int height, @NonNull Paint paint) {
        super(new Position(width / 2f, height / 2f), paint);
        this.width = width;
        this.height = height;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    void update(int width, int height) {
        this.width = width;
        this.height = height;
        setCenter(width / 2f, height / 2f);
    }

    @Override
    protected void draw(@NonNull Canvas canvas) {
        drawRect(canvas, width, height);
    }
}
