package com.u1tramarinet.breakout.ui.main.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.List;

class BallInfo extends MoveObject {
    private final float radius = 10;
    private final boolean shadow;

    BallInfo(@NonNull Position position, @NonNull Paint paint) {
        this(position, paint, false);
    }

    BallInfo(@NonNull Position position, @NonNull Paint paint, boolean shadow) {
        super(position, new Velocity(15, 15), paint);
        this.shadow = shadow;
    }

    float getRadius() {
        return radius;
    }

    @Override
    protected void draw(@NonNull Canvas canvas) {
        drawCircle(canvas, radius);
        if (!shadow) return;

        Paint paint = new Paint(getPaint());
        List<Position> positions = getPositionHistory();
        int alpha = 255;
        int diff = alpha / (positions.size() + 2);
        for (Position position : getPositionHistory()) {
            alpha -= diff;
            paint.setAlpha(alpha);
            drawCircle(canvas, position, radius, paint);
        }
    }
}
