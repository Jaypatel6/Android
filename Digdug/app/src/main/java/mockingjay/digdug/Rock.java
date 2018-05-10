package mockingjay.digdug;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import static java.lang.Thread.sleep;

public class Rock extends MovingGameObject {
    protected boolean alive;
    protected boolean fall;
    protected int xTop, yTop;
    protected int xBot, yBot;
    protected int[][] grid;
    int width, height; //device spec
    protected int xCoord, yCoord; //rock grid coordinate
    int pixel = 20;
    int numCol;

    public void drawRock(Canvas canvas, Bitmap rockIcon, Rect rockRect){
        rockRect.set(xTop, yTop, xBot, yBot);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
        canvas.drawBitmap(rockIcon, null, rockRect, null);
    }

    public void drop(){
        //if grid below is tunnel (grid == 1), shift rock 1 grid down
        if((grid[xCoord][yCoord + 1] == 1) && (fall == true)){
            yTop = yTop + pixel;
            yBot = yBot + pixel;;
            if (yTop >= ((yCoord + 1) * charH)) {
                yCoord = yCoord + 1;
                if((yCoord + 1 < numCol) || (grid[xCoord][yCoord + 1] == 0)) {
                    fall = false;
                    //disappear rock
                    try {
                        sleep(1000);
                    }
                    catch(InterruptedException e){
                    }
                    yBot = yTop;
                    xBot = xTop;
                }
            }
        }
    }
}