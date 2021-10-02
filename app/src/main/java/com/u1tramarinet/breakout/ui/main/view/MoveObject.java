package com.u1tramarinet.breakout.ui.main.view;

import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class MoveObject extends DrawObject {
    @NonNull
    private final Velocity velocity;
    @NonNull
    private final List<Position> positionHistory;
    private final int HISTORY_COUNT;

    MoveObject(@NonNull Position initialPosition, @NonNull Velocity initialVelocity, @NonNull Paint paint) {
        this(initialPosition, initialVelocity, 10, paint);
    }

    MoveObject(@NonNull Position initialPosition, @NonNull Velocity initialVelocity, int historyCount, @NonNull Paint paint) {
        super(initialPosition, paint);
        this.velocity = initialVelocity;
        this.positionHistory = new ArrayList<>();
        HISTORY_COUNT = historyCount;
    }

    @Override
    void setCenter(float x, float y) {
        setCenter(x, y, true);
    }

    void setCenter(float x, float y, boolean history) {
        if (history) enqueueHistory(getCenterX(), getCenterY());
        super.setCenter(x, y);
    }

    void setVelocityX(float velocityX) {
        velocity.x = velocityX;
    }

    float getVelocityX() {
        return velocity.x;
    }

    void setVelocityY(float velocityY) {
        velocity.y = velocityY;
    }

    float getVelocityY() {
        return velocity.y;
    }

    @NonNull
    List<Position> getPositionHistory() {
        return Collections.unmodifiableList(positionHistory);
    }

    private void enqueueHistory(float x, float y) {
        if (positionHistory.size() == HISTORY_COUNT) {
            positionHistory.remove(HISTORY_COUNT - 1);
        }
        positionHistory.add(0, new Position(x, y));
    }
}
