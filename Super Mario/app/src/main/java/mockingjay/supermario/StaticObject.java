package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public abstract class StaticObject {

    protected int objectHeight;
    protected int objectWidth;
    protected int xTop, yTop;
    protected int xBot, yBot;

    public abstract void draw(Canvas canvas, Bitmap icon, Rect rect);
}
