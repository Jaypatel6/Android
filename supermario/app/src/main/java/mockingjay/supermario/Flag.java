package mockingjay.supermario;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Flag extends StaticObject{
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    protected int xCoord, yCoord;       //grid coordinates
    protected int prevXtop;


    public void draw(Canvas canvas, Bitmap flagIcon, Rect flagRect){
        flagRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(flagIcon, null, flagRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
