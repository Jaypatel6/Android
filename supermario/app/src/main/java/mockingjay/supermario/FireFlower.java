package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;



public class FireFlower extends StaticObject {
    protected int objectWidth;
    protected int objectHeight;
    protected int xTop, yTop;
    protected int xBot, yBot;

    public void draw(Canvas canvas, Bitmap flowerIcon, Rect flowerRect){
        flowerRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(flowerIcon, null, flowerRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }
}
