package mockingjay.digdug;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MonsterThread extends Thread {

    DigDugView digdugView;
    Dug dug;
    Pooka pooka1;
    Pooka pooka2;
    Fygar fygar1;
    Fygar fygar2;
    Rock rock1;
    Rock rock2;
    Rock rock3;
    boolean gameRunning = true;

    public MonsterThread(DigDugView ddview, Dug dug, Pooka pooka1, Pooka pooka2, Fygar fygar1, Fygar fygar2, Rock rock1, Rock rock2, Rock rock3){
        digdugView = ddview;
        this.dug = dug;
        this.pooka1 = pooka1;
        this.pooka2 = pooka2;
        this.fygar1 = fygar1;
        this.fygar2 = fygar2;
        this.rock1 = rock1;
        this.rock2 = rock2;
        this.rock3 = rock3;
    }

    public void run(){
        SurfaceHolder holder;
        Canvas canvas;

        while(gameRunning) {
            holder = digdugView.getHolder();
            canvas = holder.lockCanvas();

            if(canvas != null) {
                try {
                    digdugView.draw(canvas);
                }
                catch(NullPointerException e){
                }
                //Log.d("tag", "pressed: "+ dug.pressed);
                //Log.d("tag", "direc: " + dug.direction);

                //move and draw dig dug
                if(dug.pressed == true){
                    switch (dug.direction) {
                        case "left":
                            dug.moveLeft(rock1, rock2, rock3);
                            break;
                        case "right":
                            dug.moveRight(rock1, rock2, rock3);
                            break;
                        case "up":
                            dug.moveUp(rock1, rock2, rock3);
                            break;
                        case "down":
                            dug.moveDown(rock1, rock2, rock3);
                            break;
                    }

                }
                pooka1.attack();
                pooka2.attack();
                fygar1.attack();
                fygar2.attack();
                rock1.drop();
                rock2.drop();
                rock3.drop();
                holder.unlockCanvasAndPost(canvas);
            }
        }
        if(gameRunning == false){
            //game over
        }
    }
}