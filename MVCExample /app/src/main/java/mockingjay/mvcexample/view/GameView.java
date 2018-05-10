package mockingjay.mvcexample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import mockingjay.mvcexample.R;
import mockingjay.mvcexample.controller.GameThread;
import mockingjay.mvcexample.model.DigDug;
import mockingjay.mvcexample.model.GameMap;
import mockingjay.mvcexample.model.Monster;
import mockingjay.mvcexample.model.Rock;

/**
 * Created by Jay on 5/17/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DigDug digDug;
    private Rock[] rocks;
    private Monster[] monsters;
    private Bitmap dug;

    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        dug = BitmapFactory.decodeResource(getResources(), R.drawable.androidlogo);

    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.RED);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //GameThread gameThread = new GameThread(this);
        //gameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        return false;
    }
}
