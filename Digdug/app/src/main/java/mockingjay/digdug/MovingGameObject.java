package mockingjay.digdug;

import android.graphics.Bitmap;

/**
 * Created by pbjkr_000 on 5/18/2017.
 */

public abstract class MovingGameObject {
    int xtop, ytop;
    int xbot, ybot;

    String direction; //direction to move character ("left", "right", "up", or "down")

    //size of characters
    int charH;
    int charW;
    int numMonster = 4;

    protected Bitmap icon; //icon for every object/character
}
