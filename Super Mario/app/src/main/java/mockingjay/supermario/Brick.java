package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Brick extends StaticObject {
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    protected int xCoord, yCoord;       //grid coordinates

    public void draw(Canvas canvas, Bitmap brickIcon, Rect brickRect){
        brickRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(brickIcon, null, brickRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
