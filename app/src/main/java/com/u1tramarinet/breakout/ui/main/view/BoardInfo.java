package com.u1tramarinet.breakout.ui.main.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

class BoardInfo extends MoveObject {
    private final float width = 200;
    private final float height = 50;
    @NonNull
    private Range range;

    BoardInfo(@NonNull Range range, @NonNull Paint paint) {
        this(range.minX + (range.maxX - range.minX) / 2, range.minY + (range.maxY - range.minY) / 2, range, paint);
    }

    BoardInfo(float initialCenterX, float initialCenterY, @NonNull Range range, @NonNull Paint paint) {
        super(new Position(initialCenterX, initialCenterY), new Velocity(20, 20), paint);
        this.range = range;
    }

    @Override
    protected void draw(@NonNull Canvas canvas) {
        drawRect(canvas, width, height);
    }

    void move(float cx, float cy) {
        setCenter(cx, cy, false);
        validatePosition();
    }

    void updateRange(@NonNull Range range) {
        this.range = range;
        validatePosition();
    }

    private void validatePosition() {
        setCenter(
                validatePosition(getCenterX(), width, range.minX, range.maxX),
                validatePosition(getCenterY(), height, range.minY, range.maxY), true);
    }

    private float validatePosition(float position, float size, float rangeMin, float rangeMax) {
        float halfSize = size / 2;
        float leftEdgePos = position - halfSize;
        float rightEdgePos = position + halfSize;
        if (leftEdgePos < rangeMin) {
            return rangeMin + halfSize;
        } else if (rightEdgePos > rangeMax) {
            return rangeMax - halfSize;
        }
        return position;
    }

    static class Range {
        private float minY;
        private float maxY;
        private float minX;
        private float maxX;

        Range(float minX, float maxX, float minY, float maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}
