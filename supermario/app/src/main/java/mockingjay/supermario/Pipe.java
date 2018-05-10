package mockingjay.supermario;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pipe extends StaticObject{
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    public void draw(Canvas canvas, Bitmap pipeIcon, Rect pipeRect){
        pipeRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(pipeIcon, null, pipeRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
