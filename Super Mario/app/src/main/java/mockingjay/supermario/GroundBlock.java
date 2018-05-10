package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;


public class GroundBlock extends StaticObject {
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    public void draw(Canvas canvas, Bitmap gndBlockIcon, Rect gndBlockRect){
        gndBlockRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(gndBlockIcon, null, gndBlockRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
