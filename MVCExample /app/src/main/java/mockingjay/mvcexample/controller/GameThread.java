package mockingjay.mvcexample.controller;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import mockingjay.mvcexample.view.GameView;

/**
 * Created by Jay on 5/17/17.
 */

public class GameThread extends Thread {

    private GameView gameView;
    private GameController controller;

    public GameThread(GameView gameView){
        this.gameView =  gameView;
    }

    public void run(){
        SurfaceHolder sh = gameView.getHolder();
        while (true){
            Canvas canvas = sh.lockCanvas();
            if (canvas!= null){
                controller.update();
                gameView.draw(canvas);
                sh.unlockCanvasAndPost(canvas);

            }
        }


    }


}
