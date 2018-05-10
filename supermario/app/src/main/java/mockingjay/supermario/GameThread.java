package mockingjay.supermario;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    GameView gameView;
    Mario mario;
    protected boolean gameRunning = true;

    public GameThread(GameView gameView, Mario mario){
        this.gameView = gameView;
        this.mario = mario;

    }
    public void run(){
        SurfaceHolder holder;
        Canvas canvas;
        gameView.pixel = 0;
        while(gameRunning) {
            //Log.d("tag", "gamethread running");
            holder = gameView.getHolder();
            canvas = holder.lockCanvas();

            if(canvas != null) {
                if(gameView.keyPressed == true){
                    //assumes mario cannot go back in right direction like in the original super mario game
                    switch (gameView.direction) {
                        case "left":
                            mario.moveLeft(gameView);
                            break;
                        case "right":
                            mario.moveRight(gameView);
                            break;
                        /*case "up":
                            mario.jump(gameView);
                            break;*/
                    }
                }
                if(gameView.jumpPressed == true){

                    mario.jump(gameView);
                }
                gameView.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
                //Log.d("tag", "pixel: " + gameView.pixel);
            }
        }
        if(gameRunning == false){
            //game over
        }
    }
}

