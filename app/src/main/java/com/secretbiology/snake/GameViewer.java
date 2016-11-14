package com.secretbiology.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * NCBSinfo © 2016, Secret Biology
 * https://github.com/NCBSinfo/NCBSinfo
 * Created by Rohit Suratekar on 25-07-16.
 */
public class GameViewer extends SurfaceView implements
        SurfaceHolder.Callback {
    private DrawThread drawThread;
    private Paint paint = new Paint();
    private Point location;

    public GameViewer(Context context) {
        super(context);
        initialize();
    }

    public GameViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GameViewer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        getHolder().addCallback(this);
        setFocusable(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStyle(Paint.Style.FILL);
        location = new Point(0, 200);
    }

    public void startThread() {
        drawThread = new DrawThread(getHolder(), this);
        drawThread.setRunning(true);
        drawThread.start();
    }

    public void stopThread() {
        drawThread.setRunning(false);
        drawThread.stop();
    }

    public void update() {
        location.x = location.x + 10;
        if (location.x > getWidth()) {
            location.x = 0;
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawCircle(location.x, location.y, 15, paint);
    }

    class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        GameViewer mySurfaceView;
        private boolean run = false;

        public DrawThread(SurfaceHolder surfaceHolder,
                          GameViewer mySurfaceView) {
            this.surfaceHolder = surfaceHolder;
            this.mySurfaceView = mySurfaceView;
            run = false;
        }

        public void setRunning(boolean run) {
            this.run = run;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (run) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        mySurfaceView.update();
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

}