package mockingjay.supermario;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Goomba extends MovingObject {
    protected boolean alive;
    protected int xTop, yTop;
    protected int xBot, yBot;
    protected int objectHeight;
    protected int objectWidth;
    protected int pixel;


    public void draw(Canvas canvas, Bitmap goombaIcon, Rect goombaRect){
        goombaRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(goombaIcon, null, goombaRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }

    public void attack(){
        //kills mario when it touches it.
    }

    /*
    public void move(GameView gameView){
        //kills mario when it touches it.
        switch(direc){
            case "left":
                if(xCoord - 1 > 0){
                    if(gameView.gameGrid[xCoord - 1][yCoord] == 1) {
                        xBot = xBot - pixel;
                        xTop = xTop - pixel;
                        if (xTop <= ((xCoord - 1) * objectWidth)){    //detect edge of tunnel
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
                if(xCoord + 1 < gameView.width){
                    if(gameView.gameGrid[xCoord + 1][yCoord] == 1) {
                        xBot = xBot + pixel;
                        xTop = xTop + pixel;
                        if (xTop >= ((xCoord + 1) * objectWidth)) {    //detect edge of tunnel
                            xCoord = xCoord + 1;
                        }
                    }
                    else {
                        direc = "left";
                    }
                }
                else {
                    direc = "left";
                }
                break;
        }
    }


*/

}