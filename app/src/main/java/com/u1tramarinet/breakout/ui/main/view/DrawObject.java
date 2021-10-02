package com.u1tramarinet.breakout.ui.main.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

abstract class DrawObject {
    @NonNull
    private final Position position;
    @NonNull
    private final Paint paint;

    DrawObject(@NonNull Position initialPosition, @NonNull Paint paint) {
        this.position = initialPosition;
        this.paint = paint;
    }

    final void setCenter(@NonNull Position position) {
        setCenter(position.x, position.y);
    }

    void setCenter(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    float getCenterX() {
        return position.x;
    }

    float getCenterY() {
        return position.y;
    }

    @NonNull
    Paint getPaint() {
        return paint;
    }

    protected abstract void draw(@NonNull Canvas canvas);

    protected final void drawCircle(@NonNull Canvas canvas, float radius) {
        drawCircle(canvas, position, radius, paint);
    }

    protected final void drawCircle(@NonNull Canvas canvas, @NonNull Position position, float radius, @NonNull Paint paint) {
        canvas.drawCircle(position.x, position.y, radius, paint);
    }

    protected final void drawRect(@NonNull Canvas canvas, float width, float height) {
        drawRect(canvas, position, width, height, paint);
    }

    protected final void drawRect(@NonNull Canvas canvas, @NonNull Position position, float width, float height, @NonNull Paint paint) {
        float left = position.x - (width / 2);
        float top = position.y - (height / 2);
        float right = position.x + (width / 2);
        float bottom = position.y + (height / 2);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
