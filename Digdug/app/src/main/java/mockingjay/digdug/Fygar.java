package mockingjay.digdug;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by pbjkr_000 on 5/19/2017.
 */

public class Fygar extends MovingGameObject {
    protected boolean alive;
    protected int xTop, yTop;
    protected int xBot, yBot;
    protected int[][] grid;
    protected int numRow, numCol;
    protected int xCoord, yCoord;
    int width, height; //device spec
    int charH;  //size of characters
    int charW;
    private int pixel = 15;
    private String direc = "up";

    public void drawFygar(Canvas canvas, Bitmap fygarIcon, Rect fygarRect){

        fygarRect.set(xTop, yTop, xBot, yBot);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
        canvas.drawBitmap(fygarIcon, null, fygarRect, null);
    }

    public void attack(){
        //move towards digdug and attack
        //Log.d("tag", "xCoord: " + xCoord);
        //Log.d("tag", "yCoord: " + yCoord);

        switch(direc){
            case "up":
                if((yCoord - 1 > 0)) {
                    if (grid[xCoord][yCoord - 1] == 1) {

                        yBot = yBot - pixel;
                        yTop = yTop - pixel;
                        if (yTop <= ((yCoord - 1) * charH)) {
                            //Log.d("tag", "ycoord - 1 height:  " + (yCoord - 1) * charH);
                            //Log.d("tag", "ytop: " + yTop);
                            yCoord = yCoord - 1;
                        }
                    }
                    else {
                        direc = "down";
                    }
                }
                else{
                    direc = "down";
                }
                break;
            case "down":
                if((yCoord + 1 < numRow)) {
                    if (grid[xCoord][yCoord + 1] == 1) {

                        yBot = yBot + pixel;
                        yTop = yTop + pixel;
                        if (yTop >= ((yCoord + 1) * charH)) {    //detect edge of tunnel
                            yCoord = yCoord + 1;
                        }
                    }
                    else {
                        direc = "left";
                    }
                }
                else{
                    direc = "left";
                }
                break;
            case "left":
                if(xCoord - 1 > 0){
                    if(grid[xCoord - 1][yCoord] == 1) {
                    xBot = xBot - pixel;
                    xTop = xTop - pixel;
                        if (xTop <= ((xCoord - 1) * charW)) {    //detect edge of tunnel
                            xCoord = xCoord - 1;
                        }
                    }
                    else {
                        direc = "right";
                    }
                }
                else {
                    direc = "right";
                }
                break;
            case "right":
                if(xCoord + 1 < numCol){
                    if(grid[xCoord + 1][yCoord] == 1) {
                    xBot = xBot + pixel;
                    xTop = xTop + pixel;
                        if (xTop >= ((xCoord + 1) * charW)) {    //detect edge of tunnel
                            xCoord = xCoord + 1;
                        }
                    }
                else {
                        direc = "up";
                    }
                }
                else {
                    direc = "up";
                }
                break;
        }
    }
}
