package mockingjay.digdug;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import static android.os.SystemClock.sleep;

public class Dug extends MovingGameObject {
    String direction;
    protected boolean alive;
    private boolean isAttacking;
    protected boolean rockfall;
    boolean pressed;
    private final int pixel = 15;
    protected int xTop, yTop;
    protected int xBot, yBot;
    protected int xCoord, yCoord;
    protected int width, height; //device spec
    protected int[][] grid;
    protected int numRow, numCol;
    protected int charW, charH;


    public void drawDug(Canvas canvas, Bitmap dugicon, Rect dugRect){
        dugRect.set(xTop, yTop, xBot, yBot);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
        canvas.drawBitmap(dugicon, null, dugRect, null);
    }

    public void stopAttack() {
    }

    public void attack(){

    }
    public void moveRight(Rock rock1, Rock rock2, Rock rock3){
        if((xBot + pixel < (int)(width * 0.7)) && (xTop + pixel < (int)(width * 0.7)) && (grid[xCoord+1][yCoord]!= 2)) {
            xBot = xBot + pixel;   //update new location
            xTop = xTop + pixel;
            //Log.d("tag", "Xtop: " + xTop + "    Xbot: " + xBot);
            //Log.d("tag", "Ytop: " + yTop + "    Ybot: " + yBot);
            //set dirt to black when digging tunnel
            if(xCoord + 1 < numCol) {
                //grid[xCoord + 1][yCoord] = 1;
                if (xTop >= ((xCoord + 1) * charW)){
                        /*Log.d("tag", "xcoord pixels:  " + (xCoord * charW));
                        Log.d("tag", "xcoord: " + xCoord);
                        Log.d("tag", "ycoord: " + yCoord);
                        Log.d("tag", "xtop: " + xTop);*/
                    grid[xCoord + 1][yCoord] = 1;
                    xCoord = xCoord + 1;
                    //check if rock above
                       /* if((grid[xCoord][yCoord-1]== 2) && ((grid[xCoord][yCoord+1])== 1)){
                            //set rock1.fall to true
                            rock1.fall = true;
                        }*/
                    if(yCoord - 1 >= 0) {
                        if ((xCoord == rock1.xCoord) && (yCoord - 1 == rock1.yCoord)) {
                            rock1.fall = true;
                        }
                        if ((xCoord == rock2.xCoord) && (yCoord - 1 == rock2.yCoord)) {
                            rock2.fall = true;
                        }
                        if ((xCoord == rock3.xCoord) && (yCoord - 1 == rock3.yCoord)) {
                            rock3.fall = true;
                        }
                    }

                }
                if(xBot == ((xCoord + 1) * charW)){
                    grid[xCoord][yCoord] = 1;
                }
            }
        }
    }
    public void moveLeft(Rock rock1, Rock rock2, Rock rock3){
        if((xBot - pixel > 0) && (xTop - pixel > 0)&& (grid[xCoord-1][yCoord]!= 2)) {
            xBot = xBot - pixel;   //update new location
            xTop = xTop - pixel;
            //Log.d("tag", "Xtop: " + xTop + "    Xbot: " + xBot);
            //Log.d("tag", "Ytop: " + yTop + "    Ybot: " + yBot);
            //set dirt to black when digging tunnel
            if(xCoord - 1 < numCol) {
                //grid[xCoord - 1][yCoord] = 1;
                if (xTop <= ((xCoord - 1) * charW)){
                        /*Log.d("tag", "xcoord pixels:  " + (xCoord * charW));
                        Log.d("tag", "xcoord: " + xCoord);
                        Log.d("tag", "ycoord: " + yCoord);
                        Log.d("tag", "xtop: " + xTop);*/
                    grid[xCoord - 1][yCoord] = 1;
                    xCoord = xCoord - 1;
                    //check if rock above
                    if(yCoord - 1 >= 0) {
                        if ((xCoord == rock1.xCoord) && (yCoord - 1 == rock1.yCoord)) {
                            rock1.fall = true;
                        }
                        if ((xCoord == rock2.xCoord) && (yCoord - 1 == rock2.yCoord)) {
                            rock2.fall = true;
                        }
                        if ((xCoord == rock3.xCoord) && (yCoord - 1 == rock3.yCoord)) {
                            rock3.fall = true;
                        }
                    }
                }
                if(xTop <= ((xCoord) * charW)){
                    grid[xCoord - 1][yCoord] = 1;
                }
            }
        }
    }
    public void moveUp(Rock rock1, Rock rock2, Rock rock3){
        if((yBot - pixel > 0) && (yTop - pixel > 0)&& (grid[xCoord][yCoord-1]!= 2)){
            yBot = yBot - pixel;
            yTop = yTop - pixel;
            //Log.d("tag", "Xtop: " + xTop + "    Xbot: " + xBot);
            //Log.d("tag", "Ytop: " + yTop + "    Ybot: " + yBot);
            //set dirt to black when digging tunnel
            if(yCoord - 1 < numRow) {
                //grid[xCoord][yCoord-1] = 1;
                if (yTop <= ((yCoord - 1) * charH)){
                        /*Log.d("tag", "ycoord pixels:  " + (yCoord * charW));
                        Log.d("tag", "xcoord: " + xCoord);
                        Log.d("tag", "ycoord: " + yCoord);
                        Log.d("tag", "ytop: " + yTop);*/
                    grid[xCoord][yCoord-1] = 1;
                    yCoord = yCoord - 1;
                    //check if rock above
                    if(yCoord - 1 >= 0) {
                        if ((xCoord == rock1.xCoord) && (yCoord - 1 == rock1.yCoord)) {
                            rock1.fall = true;
                        }
                        if ((xCoord == rock2.xCoord) && (yCoord - 1 == rock2.yCoord)) {
                            rock2.fall = true;
                        }
                        if ((xCoord == rock3.xCoord) && (yCoord - 1 == rock3.yCoord)) {
                            rock3.fall = true;
                        }
                    }
                }
                if(yTop <= ((yCoord) * charW)){
                    grid[xCoord][yCoord -1] = 1;
                }
            }

        }
    }
    public void moveDown(Rock rock1, Rock rock2, Rock rock3){
        if((yBot + pixel < height) && (yTop + pixel < height) && (grid[xCoord][yCoord+1]!= 2)){
            yBot = yBot + pixel;
            yTop = yTop + pixel;
            //Log.d("tag", "Xtop: " + xTop + "    Xbot: " + xBot);
            //Log.d("tag", "Ytop: " + yTop + "    Ybot: " + yBot);
            //set dirt to black when digging tunnel
            if(yCoord + 1 < numRow) {
                //grid[xCoord][yCoord+1] = 1;
                if (yTop >= ((yCoord + 1) * charH)){
                        /*Log.d("tag", "xcoord pixels:  " + (yCoord * charW));
                        Log.d("tag", "xcoord: " + xCoord);
                        Log.d("tag", "ycoord: " + yCoord);
                        Log.d("tag", "ytop: " + yTop);*/
                    grid[xCoord][yCoord+1] = 1;
                    yCoord = yCoord + 1;
                }
                if(yTop <= ((yCoord) * charW)){
                    grid[xCoord][yCoord + 1] = 1;
                }
            }
        }
    }
}

