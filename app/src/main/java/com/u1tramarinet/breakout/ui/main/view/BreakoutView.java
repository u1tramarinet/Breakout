package com.u1tramarinet.breakout.ui.main.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BreakoutView extends View {
    private ScreenInfo screen;
    private BallInfo ball;
    private BoardInfo board;
    private boolean initialized = false;

    public BreakoutView(Context context) {
        super(context);
    }

    public BreakoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BreakoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BreakoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!initialized) {
            initialize(getWidth(), getHeight());
            initialized = true;
        } else {
            drawObjects(canvas);
            updateObjectsPosition();
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                updateBoardPosition(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void initialize(int width, int height) {
        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.BLACK);
        screen = new ScreenInfo(width, height, backgroundPaint);
        Paint ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballPaint.setColor(Color.WHITE);
        ball = new BallInfo(new Position(100f, 100f), ballPaint, true);
        Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boardPaint.setColor(Color.MAGENTA);
        boardPaint.setStyle(Paint.Style.STROKE);
        BoardInfo.Range range = new BoardInfo.Range(0, width, height - 200, height - 100);
        board = new BoardInfo(range, boardPaint);
    }

    private void updateBoardPosition(float x, float y) {
        board.move(x, y);
    }

    private void drawObjects(@NonNull Canvas canvas) {
        screen.draw(canvas);
        ball.draw(canvas);
        board.draw(canvas);
    }

    private void updateObjectsPosition() {
        CalcResult resultX = calculateNextPosition(ball.getCenterX(), ball.getVelocityX(), ball.getRadius(), screen.getWidth());
        CalcResult resultY = calculateNextPosition(ball.getCenterY(), ball.getVelocityY(), ball.getRadius(), screen.getHeight());
        ball.setCenter(new Position(resultX.position, resultY.position));
        ball.setVelocityX(resultX.velocity);
        ball.setVelocityY(resultY.velocity);
    }

    private CalcResult calculateNextPosition(float currentPosition, float velocity, float radius, float stageSize) {
        Log.d(BreakoutView.class.getSimpleName(), "calculateNextPosition() currentPosition=" + currentPosition + ", velocity = " + velocity);
        float nextPosition = currentPosition + velocity;

        if (velocity > 0) {
            float rightEdgePos = nextPosition + radius;
            if (rightEdgePos > stageSize) {
                float buried = rightEdgePos - stageSize;
                return new CalcResult(stageSize - buried, -velocity);
            }
        } else if (velocity < 0) {
            float leftEdgePos = nextPosition - radius;
            if (leftEdgePos < 0) {
                float buried = 0 - leftEdgePos;
                return new CalcResult(radius + buried, -velocity);
            }
        }
        return new CalcResult(nextPosition, velocity);
    }

    private static class CalcResult {
        private final float position;
        private final float velocity;

        private CalcResult(float position, float velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        @Override
        public String toString() {
            return "CalcResult{" +
                    "position=" + position +
                    ", velocity=" + velocity +
                    '}';
        }
    }
}
