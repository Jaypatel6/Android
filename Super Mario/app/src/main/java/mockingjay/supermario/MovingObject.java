package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public abstract class MovingObject {
    boolean alive;
    protected int objectHeight;
    protected int objectWidth;

    public abstract void attack();

    public abstract void draw(Canvas canvas, Bitmap icon, Rect rect);


}
