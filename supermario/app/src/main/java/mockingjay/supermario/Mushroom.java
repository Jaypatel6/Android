package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;



public class Mushroom extends StaticObject {
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    public void draw(Canvas canvas, Bitmap mushroomIcon, Rect mushroomRect){
        mushroomRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(mushroomIcon, null, mushroomRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
