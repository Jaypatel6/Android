package mockingjay.digdug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class DigDugView extends SurfaceView implements SurfaceHolder.Callback {
    //dirt
    private int numRow = 10;
    private int numCol = 10;
    protected int[][] grid = new int[numCol][numRow];
    public boolean rockalive;
    private Bitmap dirtTiles[];
    //private Bitmap blackTiles;
    private int numDirt = 3;
    //pookas
    private Bitmap pookaIcon;
    Pooka pooka1;
    Pooka pooka2;
    Rect pooka1Rect = new Rect();
    Rect pooka2Rect = new Rect();

    //fygars
    private Bitmap fygarIcon;
    Fygar fygar1;
    Fygar fygar2;
    Rect fygar1Rect = new Rect();
    Rect fygar2Rect = new Rect();

    //dug
    private Bitmap dugicon;
    Dug dug;
    Rect dugRect = new Rect();

    //rock
    private Bitmap rockicon;
    Rect rock1Rect = new Rect();
    Rect rock2Rect = new Rect();
    Rect rock3Rect = new Rect();
    Rock rock1;
    Rock rock2;
    Rock rock3;

    //device width and height
    int width, height;

    //character size
    int charH, charW;
    //coordinates for dpad
    private int upXtop, upYtop, upXbot, upYbot;
    private int downXtop, downYtop, downXbot, downYbot;
    private int rXtop, rYtop, rXbot, rYbot;
    private int lftXtop, lftYtop, lftXbot, lftYbot;

    public DigDugView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        dirtTiles = new Bitmap[numDirt];

        for(int x = 0; x < numCol; x++){
            for(int y = 0; y < numRow; y++){
                grid[x][y] = 0;     //0 means dirt
            }
        }
        //instantiate moving objects
        dug = new Dug();
        pooka1 = new Pooka();
        pooka2 = new Pooka();
        fygar1 = new Fygar();
        fygar2 = new Fygar();
        rock1 = new Rock();
        rock2 = new Rock();
        rock3 = new Rock();

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

         /*dirt*/
        drawDirt(canvas);

        //draw dig dug
        dug.grid = grid;
        dug.drawDug(canvas, dugicon, dugRect);
        //draw pookas
        pooka1.grid = grid;
        pooka1.drawPooka(canvas, pookaIcon, pooka1Rect);
        pooka2.grid = grid;
        pooka2.drawPooka(canvas, pookaIcon, pooka2Rect);
        //draw fygars
        fygar1.grid = grid;
        fygar1.drawFygar(canvas, fygarIcon, fygar1Rect);
        fygar2.grid = grid;
        fygar2.drawFygar(canvas, fygarIcon, fygar2Rect);

        //draw rocks
        rock1.drawRock(canvas, rockicon, rock1Rect);
        rock1.grid = grid;

        rock2.drawRock(canvas, rockicon, rock2Rect);
        rock2.grid = grid;
        rock3.drawRock(canvas, rockicon, rock3Rect);
        rock3.grid = grid;

        //draw line
        /*Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(8);
        canvas.drawLine((int) (getWidth()* 0.7), 0, (int)(getWidth()* 0.7), getHeight(), linePaint);
    */
        /* dpad */
        drawDpad(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        MonsterThread monsterThread = new MonsterThread(this, dug, pooka1, pooka2, fygar1, fygar2, rock1, rock2, rock3);
        monsterThread.start();      //start the monster thread

        width = getWidth();
        height = getHeight();
        charH = (int)(height * (0.1));
        charW = (int)(width * (0.7) * (0.1));
        //dug
        dugicon = BitmapFactory.decodeResource(getResources(), R.drawable.androidlogo);
        dug.xTop = 4 * charW;
        dug.yTop = 3 * charH;
        dug.xBot = 5 * charW;
        dug.yBot = 4 * charH;
        dug.height = height;
        dug.width = width;
        dug.alive = true;
        dug.grid = grid;
        dug.rockfall =rockalive;
        //Log.d("tag", "rockfall:" +dug.rockfall);
        dug.xCoord = 4;
        dug.yCoord = 3;
        dug.numCol = numCol;
        dug.numRow = numRow;
        dug.charH = charH;
        dug.charW = charW;

        //pookas
        pookaIcon =  BitmapFactory.decodeResource(getResources(), R.drawable.pooka);
        pooka1.grid = grid;
        pooka2.grid = grid;
        pooka1.charH = charH;
        pooka1.charW = charW;
        pooka2.charH = charH;
        pooka2.charW = charW;
        pooka1.height = height;
        pooka1.width = width;
        pooka2.height = height;
        pooka2.width = width;
        pooka1.numCol = numCol;
        pooka1.numRow = numRow;
        pooka2.numCol = numCol;
        pooka2.numRow = numRow;
        //pooka1 initialization in upper left tunnel
        pooka1.alive = true;
        pooka1.xTop = 1 * charW;
        pooka1.xBot = 2 * charW;
        pooka1.yTop = 2 * charH;
        pooka1.yBot = 3 * charH;
        pooka1.xCoord = 1;
        pooka1.yCoord = 2;
        //pooka2 initialization in lower right tunnel
        pooka2.alive = true;
        pooka2.xTop = 7 * charW;
        pooka2.xBot = 8 * charW;
        pooka2.yTop = 5 * charH;
        pooka2.yBot = 6 * charH;
        pooka2.xCoord = 7;
        pooka2.yCoord = 5;

        //fygars
        fygarIcon =  BitmapFactory.decodeResource(getResources(), R.drawable.fygar);
        fygar1.charH = charH;
        fygar1.charW = charW;
        fygar2.charH = charH;
        fygar2.charW = charW;
        fygar1.height = height;
        fygar1.width = width;
        fygar2.height = height;
        fygar2.width = width;
        fygar1.numCol = numCol;
        fygar1.numRow = numRow;
        fygar2.numCol = numCol;
        fygar2.numRow = numRow;
        fygar1.grid = grid;
        fygar2.grid = grid;

        //fygar1 initialization
        fygar1.alive = true;
        fygar1.xTop = 2 * charW;
        fygar1.xBot = 3 * charW;
        fygar1.yTop = 7 * charH;
        fygar1.yBot = 8 * charH;
        fygar1.xCoord = 2;
        fygar1.yCoord = 7;
        //fygar2 initialization
        fygar2.alive = true;
        fygar2.xTop = 7 * charW;
        fygar2.xBot = 8 * charW;
        fygar2.yTop = 2 * charH;
        fygar2.yBot = 3 * charH;
        fygar2.xCoord = 7;
        fygar2.yCoord = 2;

        rockicon = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
        rock1.charH = charH;
        rock1.charW = charW;
        rock2.charH = charH;
        rock2.charW = charW;
        rock3.charH = charH;
        rock3.charW = charW;

        rock1.height = height;
        rock1.width = width;
        rock2.height = height;
        rock2.width = width;
        rock3.height = height;
        rock3.width = width;

        //rock1 initialization
        rock1.alive = true;
        rock1.fall = rockalive;
        rock1.xTop = 2 * charW;
        rock1.xBot = 3 * charW;
        rock1.yTop = 0 * charH;
        rock1.yBot = 1 * charH;
        rock1.grid = grid;
        rock1.xCoord = 2;
        rock1.yCoord = 0;
        rock1.numCol = numCol;

        //rock2 initialization
        rock2.alive = true;
        rock2.fall = false;
        rock2.xTop = 8 * charW;
        rock2.xBot = 9 * charW;
        rock2.yTop = 3 * charH;
        rock2.yBot = 4 * charH;
        rock2.grid = grid;
        rock2.xCoord = 8;
        rock2.yCoord = 3;
        rock2.numCol = numCol;

        //rock3 initialization
        rock3.alive = true;
        rock3.fall = false;
        rock3.xTop = 5 * charW;
        rock3.xBot = 6 * charW;
        rock3.yTop = 7 * charH;
        rock3.yBot = 8 * charH;
        rock3.grid = grid;
        rock3.xCoord = 5;
        rock3.yCoord = 7;
        rock3.numCol = numCol;

        //dirt initialization
        dirtTiles[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dirt);
        dirtTiles[1] = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        dirtTiles[2] = BitmapFactory.decodeResource(getResources(), R.drawable.rock);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

   @Override
    public boolean onTouchEvent(MotionEvent event) {
       //Log.d("tag", "onTouchEvent occured");
       int Xcoord = 0;
       int Ycoord = 0;

       if(event.getAction() == MotionEvent.ACTION_DOWN) {
           Xcoord = (int) event.getX();
           Ycoord = (int) event.getY();
           /*Log.d("tag", "Xcoord: " + Xcoord);
           Log.d("tag", "Ycoord: " + Ycoord);*/

           //detect dpad direction
           if ((Xcoord >= lftXtop) && (Xcoord <= lftXbot) && (Ycoord >= lftYtop) && (Ycoord <= lftYbot)) {
               //Log.d("tag", "left detected");
               dug.pressed = true;
               dug.direction = "left";
           }
           else if ((Xcoord >= rXtop) && (Xcoord <= rXbot) && (Ycoord >= rYtop) && (Ycoord <= rYbot)) {
               //Log.d("tag", "right detected");
               dug.pressed = true;
               dug.direction = "right";
           }
           else if ((Xcoord >= upXtop) && (Xcoord <= upXbot) && (Ycoord >= upYtop) && (Ycoord <= upYbot)) {
               //Log.d("tag", "up detected");
               dug.pressed = true;
               dug.direction = "up";
           }
           else if ((Xcoord >= downXtop) && (Xcoord <= downXbot) && (Ycoord >= downYtop) && (Ycoord <= downYbot)) {
               //Log.d("tag", "down detected");
               dug.pressed = true;
               dug.direction = "down";
           }
       }
       else if(event.getAction() == MotionEvent.ACTION_UP){
           dug.pressed = false;
       }
       invalidate();
       return true;
    }

    public void drawDpad(Canvas canvas){
        //up key
        Bitmap upKey = BitmapFactory.decodeResource(getResources(), R.drawable.up);
        Rect upDst = new Rect();
        upXtop = (int) (getWidth()* 0.8);
        upYtop = (int) (getHeight()* 0.50);
        upXbot = (int) (getWidth()* 0.9);
        upYbot = (int) (getHeight()* 0.60);
        upDst.set(upXtop, upYtop, upXbot, upYbot);
        canvas.drawBitmap(upKey, null, upDst, null);

        //down key
        Bitmap downKey = BitmapFactory.decodeResource(getResources(), R.drawable.down);
        Rect downDst = new Rect();
        downXtop = (int) (getWidth()* 0.8);
        downYtop = (int) (getHeight()* 0.62);
        downXbot = (int) (getWidth()* 0.9);
        downYbot = (int) (getHeight()* 0.72);
        downDst.set(downXtop, downYtop, downXbot, downYbot);
        canvas.drawBitmap(downKey, null, downDst, null);

        //left key
        Bitmap leftKey = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        Rect leftDst = new Rect();
        lftXtop = (int) (getWidth()* 0.71);
        lftYtop = (int) (getHeight() * 0.55);
        lftXbot = (int) (getWidth()* 0.79);
        lftYbot = (int) (getHeight()* 0.68);
        leftDst.set(lftXtop, lftYtop, lftXbot, lftYbot);
        canvas.drawBitmap(leftKey, null, leftDst, null);

        //right key
        Bitmap rightKey = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        Rect rightDst = new Rect();
        rXtop = (int) (getWidth()* 0.91);
        rYtop = (int) (getHeight() * 0.55);
        rXbot = (int) (getWidth()* 0.99);
        rYbot = ((int) (getHeight()* 0.68));
        rightDst.set(rXtop, rYtop, rXbot, rYbot);
        canvas.drawBitmap(rightKey, null, rightDst, null);
    }

    public void drawDirt(Canvas canvas){
        int width_dirt = (int) (getWidth()* 0.7);
        //int width_dirt = getWidth();
        int height_dirt = getHeight();
        int rowHeight = height_dirt / numRow;
        int columnWidth = width_dirt / numCol;
        Rect gridRect = new Rect();
        Bitmap dirt = BitmapFactory.decodeResource(getResources(), R.drawable.dirt);

        for(int i = 0; i < numRow; i++) {       //draw all dirt first
            for(int j = 0; j < numCol; j++) {
                int topX = j * columnWidth;
                int topY = i * rowHeight;
                int botX = (j + 1) * columnWidth;
                int botY = (i + 1) * rowHeight;
                gridRect.set(topX, topY, botX, botY);

                int dirtType = grid[j][i];
                //canvas.drawBitmap(dirt, null, gridRect, null);
                canvas.drawBitmap(dirtTiles[dirtType], null, gridRect, null);


            }
        }

        //draw initial tunnels
        Paint blkPnt = new Paint();
        blkPnt.setColor(Color.BLACK);
        blkPnt.setStyle(Paint.Style.FILL);

        for(int a = 1; a <= 3; a++) {       //upper left pooka tunnel
            int topX = 1 * columnWidth;
            int topY = a * rowHeight;
            int botX = 2 * columnWidth;
            int botY = (a + 1) * rowHeight;
            grid[1][a] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
        for(int b = 4; b <= 7; b++) {       //lower right pooka tunnel
            int topX = 7 * columnWidth;
            int topY = b * rowHeight;
            int botX = 8 * columnWidth;
            int botY = (b + 1) * rowHeight;
            grid[7][b] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
        for(int c = 1; c <= 3; c++) {       //lower left fygar tunnel
            int topX = c * columnWidth;
            int topY = 7 * rowHeight;
            int botX = (c + 1) * columnWidth;
            int botY = 8 * rowHeight;
            grid[c][7] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
        for(int c = 6; c <= 8; c++) {       //upper right fygar tunnel
            int topX = c * columnWidth;
            int topY = 2 * rowHeight;
            int botX = (c + 1) * columnWidth;
            int botY = 3 * rowHeight;
            grid[c][2] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
        for(int c = 3; c <= 5; c++) {       //dig dug tunnel
            int topX = c * columnWidth;
            int topY = 3 * rowHeight;
            int botX = (c + 1) * columnWidth;
            int botY = 4 * rowHeight;
            grid[c][3] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
        for(int b = 0; b <= 3; b++) {       //dig dug tunnel
            int topX = 4 * columnWidth;
            int topY = b * rowHeight;
            int botX = 5 * columnWidth;
            int botY = (b + 1) * rowHeight;
            grid[4][b] = 1;
            gridRect.set(topX, topY, botX, botY);
            canvas.drawRect(gridRect, blkPnt);
        }
    }
}

