package mockingjay.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public int score;
    //for levels
    public int counter = 3;
    private String map1 = "maptest.txt";
    private String map2 = "maptest2.txt";
    private String map3 = "maptest3.txt";
    protected int lives;
    private int numRow = 10;
    private int numCol = 20;
    protected char[][] gameGrid;        //copy of map level grid
    int columnWidth;
    int rowHeight;
    public boolean keyPressed;
    public  boolean jumpPressed;
    String direction;

    //device width and height
    int width, height;

    //character size
    int charH, charW;

    //coordinates for arrow keys
    private int upXtop, upYtop, upXbot, upYbot;
    private int attackXtop, attackYtop, attackXbot, attackYbot;
    private int rXtop, rYtop, rXbot, rYbot;
    private int lftXtop, lftYtop, lftXbot, lftYbot;

    //level maps
    ReadMap readMap1 = new ReadMap();

    //static objects
    private Bitmap gndBlockIcon;
    Rect gndBlockRect = new Rect();
    GroundBlock gndBlock = new GroundBlock();

    private Bitmap brickIcon;
    Rect brickRect = new Rect();
    Brick brick = new Brick();

    private Bitmap coinIcon;
    Rect coinRect = new Rect();
    Coin coin = new Coin();

    private Bitmap pipeIcon;
    Rect pipeRect = new Rect();
    Pipe pipe = new Pipe();

    private Bitmap flowerIcon;
    Rect flowerRect = new Rect();
    FireFlower fireFlower = new FireFlower();

    private Bitmap mushroomIcon;
    Rect mushroomRect = new Rect();
    Mushroom mushroom = new Mushroom();

    private Bitmap powerupIcon;
    Rect powerupRect = new Rect();
    Powerup powerup = new Powerup();

    private Bitmap flagIcon;
    Rect flagRect = new Rect();
    Flag flag = new Flag();

    //moving objects
    //Regular mario
    private Bitmap marioIcon;
    Rect marioRect = new Rect();
    Mario mario = new Mario();

    //Super mario
    private Bitmap superMarioIcon;
    Rect superMarioRect = new Rect();
    Mario supermario = new Mario();

    //Fire mario
    private Bitmap fireMarioIcon;
    Rect fireMarioRect = new Rect();
    Mario firemario = new Mario();

    private Bitmap koopaIcon;
    Rect koopaRect = new Rect();
    Koopa koopa = new Koopa();

    private Bitmap plantIcon;
    Rect plantRect = new Rect();
    Plant plant = new Plant();

    private Bitmap goombaIcon;
    Rect goombaRect = new Rect();
    Goomba goomba = new Goomba();

    protected int pixel;
    protected int offset;       //difference in number of grids between screen size and actual level map

    List<Brick> brickArray = new ArrayList<>();
    int numBrick = 0;       //index to access bricks in array

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        //load the level map into internal grid
        if(counter ==1) {
            readMap1.createMap(map1, context);
        }
        else if(counter ==2){
            readMap1.createMap(map2, context);
        }
        else if(counter == 3){
            readMap1.createMap(map3, context);
        }
        gameGrid = new char[readMap1.mapWidth][readMap1.mapHeight];
        for(int x = 0; x < readMap1.mapWidth; x++){
            for(int y = 0; y < readMap1.mapHeight; y++){
                gameGrid[x][y] = readMap1.mapGrid[x][y];        //copy grid
                //Log.d("tag", " " + gameGrid[x][y]);
            }
        }
        //initialize mario's internal coordinates
        for (int y = readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = readMap1.mapWidth - 1; x >= 0; x--) {
                if (gameGrid[x][y] == 'M') {
                    mario.yCoord = y;
                    mario.xCoord = x;
                }
            }
        }
        offset = readMap1.mapWidth - numCol;

        //create map objects
        //createObjects();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.rgb(80, 250, 240));

        drawMap(canvas);
        drawKeys(canvas);
        drawScoreLife(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        height = getHeight();
        width = getWidth();
        columnWidth = (int)((float)width*1.0/(float)numCol);
        rowHeight = (int)((float)height*1.0/(float)numRow);
        //Log.d("tag", "colWidth: " + columnWidth + " rowHeight: "+ rowHeight);
        score = 0;
        lives = 3;

        //ground block
        gndBlockIcon = BitmapFactory.decodeResource(getResources(), R.drawable.groundblock);
        gndBlock.objectWidth = columnWidth;
        gndBlock.objectHeight = rowHeight;

        //brick
        brickIcon = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
        brick.objectWidth = columnWidth;
        brick.objectHeight = rowHeight;

        //Regular mario
        mario.alive = true;
        marioIcon = BitmapFactory.decodeResource(getResources(), R.drawable.regularmario);
        mario.objectWidth = columnWidth;
        mario.objectHeight = rowHeight;

        //Super mario
        superMarioIcon = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        supermario.objectWidth = columnWidth;
        supermario.objectHeight = rowHeight;

        //Fire mario
        fireMarioIcon = BitmapFactory.decodeResource(getResources(), R.drawable.firemario);
        firemario.objectWidth = columnWidth;
        firemario.objectHeight = rowHeight;

        //coin
        coinIcon = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        coin.objectWidth = columnWidth;
        coin.objectHeight = rowHeight;

        //powerup block
        powerupIcon = BitmapFactory.decodeResource(getResources(), R.drawable.powerupblock);
        powerup.objectWidth = columnWidth;
        powerup.objectHeight = rowHeight;

        //pipe
        pipeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);
        pipe.objectWidth = columnWidth;
        pipe.objectHeight = rowHeight;

        //Mushroom(for Super Mario)
        mushroomIcon = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);
        mushroom.objectWidth = columnWidth;
        mushroom.objectHeight= rowHeight;

        //FireFlower(for Fire Mario)
        flowerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fireflower);
        fireFlower.objectWidth = columnWidth;
        fireFlower.objectHeight = rowHeight;

        //Koopa
        koopaIcon = BitmapFactory.decodeResource(getResources(), R.drawable.koopa);
        koopa.objectWidth = columnWidth;
        koopa.objectHeight = rowHeight;

        //Goomba
        goombaIcon = BitmapFactory.decodeResource(getResources(), R.drawable.goomba);
        goomba.objectWidth = columnWidth;
        goomba.objectHeight = rowHeight;

        //Plant
        plantIcon = BitmapFactory.decodeResource(getResources(), R.drawable.plant);
        plant.objectWidth = columnWidth;
        plant.objectHeight = rowHeight;

        //Flag
        flagIcon = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        flag.objectWidth = columnWidth;
        flag.objectHeight = rowHeight;

        GameThread gameThread = new GameThread(this, mario);    //start thread AFTER fields initialized
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d("tag", "onTouchEvent occured");
        int Xcoord;
        int Ycoord;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            Xcoord = (int) event.getX();
            Ycoord = (int) event.getY();
            //Log.d("tag", "Xcoord: " + Xcoord);
            //Log.d("tag", "Ycoord: " + Ycoord);

            //detect button pressed
            if ((Xcoord >= lftXtop) && (Xcoord <= lftXbot) && (Ycoord >= lftYtop) && (Ycoord <= lftYbot)) {
                //Log.d("tag", "left detected");
                keyPressed = true;
                direction = "left";
            }
            else if ((Xcoord >= rXtop) && (Xcoord <= rXbot) && (Ycoord >= rYtop) && (Ycoord <= rYbot)) {
                //Log.d("tag", "right detected");
                keyPressed = true;
                direction = "right";
            }
            if ( (Xcoord >= upXtop) && (Xcoord <= upXbot) && (Ycoord >= upYtop) && (Ycoord <= upYbot) && (jumpPressed == false) ) {
                //Log.d("tag", "up detected");
                mario.groundTopY = mario.yTop;      //y coordinates at which mario jumps from - should return to this height once jumped
                mario.groundBotY = mario.yBot;
                mario.startTime = System.nanoTime();
                //Log.d("tag", "start time: " + mario.startTime);
                jumpPressed = true;
                direction = "up";
                //Log.d("tag", "yBot before jump: " + mario.yBot);
                //Log.d("tag", "groundBotY in gameView: " + mario.groundBotY);
            }
            if ((Xcoord >= attackXtop) && (Xcoord <= attackXbot) && (Ycoord >= attackYtop) && (Ycoord <= attackYbot)) {
                Log.d("tag", "attack detected");
                /*dug.pressed = true;
                dug.direction = "down";*/
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            keyPressed = false;
        }
        invalidate();
        return true;
    }

    private void drawKeys(Canvas canvas) {
        //up/jump key
        Bitmap upKey = BitmapFactory.decodeResource(getResources(), R.drawable.jump);
        Rect upDst = new Rect();
        upXtop = (int) (getWidth() * 0.8);
        upYtop = (int) (getHeight() * 0.8);
        upXbot = (int) (getWidth() * 0.93);
        upYbot = (int) (getHeight() * 0.95);
        upDst.set(upXtop, upYtop, upXbot, upYbot);
        canvas.drawBitmap(upKey, null, upDst, null);

        //attack key
        Bitmap attackKey = BitmapFactory.decodeResource(getResources(), R.drawable.attack);
        Rect attackDst = new Rect();
        attackXtop = (int) (getWidth() * 0.65);
        attackYtop = (int) (getHeight() * 0.8);
        attackXbot = (int) (getWidth() * 0.75);
        attackYbot = (int) (getHeight() * 0.95);
        attackDst.set(attackXtop, attackYtop, attackXbot, attackYbot);
        canvas.drawBitmap(attackKey, null, attackDst, null);

        //left key
        Bitmap leftKey = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        Rect leftDst = new Rect();
        lftXtop = (int) (getWidth() * 0.07);
        lftYtop = (int) (getHeight() * 0.80);
        lftXbot = (int) (getWidth() * 0.22);
        lftYbot = (int) (getHeight() * 0.95);
        leftDst.set(lftXtop, lftYtop, lftXbot, lftYbot);
        canvas.drawBitmap(leftKey, null, leftDst, null);

        //right key
        Bitmap rightKey = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        Rect rightDst = new Rect();
        rXtop = (int) (getWidth() * 0.23);
        rYtop = (int) (getHeight() * 0.80);
        rXbot = (int) (getWidth() * 0.38);
        rYbot = ((int) (getHeight() * 0.95));
        rightDst.set(rXtop, rYtop, rXbot, rYbot);
        canvas.drawBitmap(rightKey, null, rightDst, null);
    }

    public void drawScoreLife(Canvas canvas) {
        //life indicator
        Bitmap life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        Rect lifeDst = new Rect();
        int lifeXtop = (int) (getWidth() * 0.6);
        int lifeYtop = (int) (getHeight() * 0.05);
        int lifeXbot = (int) (getWidth() * 0.65);
        int lifeYbot = (int) (getHeight() * 0.1);
        lifeDst.set(lifeXtop, lifeYtop, lifeXbot, lifeYbot);
        canvas.drawBitmap(life, null, lifeDst, null);
        int lifeX = (int) (getWidth() * 0.65);
        int lifeY = (int) (getHeight() * 0.10);
        Paint lifePnt = new Paint();
        lifePnt.setColor(Color.BLACK);
        lifePnt.setTextSize(80);
        lifePnt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        String l = " x" + lives;
        canvas.drawText(l, lifeX, lifeY, lifePnt);

        int scoreX = (int) (getWidth() * 0.25);
        int scoreY = (int) (getHeight() * 0.1);
        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(80);
        scorePaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        String s = "Score: " + score;
        //canvas.drawRect(scoreXtop, scoreYtop, scoreXbot, scoreYbot, scoreRectPnt);
        canvas.drawText(s, scoreX, scoreY, scorePaint);
    }

    public void drawMap(Canvas canvas){
        for (int y = readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = readMap1.mapWidth - 1; x >= 0; x--) {
                char c = readMap1.mapGrid[x][y];
                switch (c) {
                    case ('Z'):      //ground block
                        gndBlock.xTop = ((x - offset) * columnWidth) + pixel;
                        gndBlock.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        gndBlock.yTop = y * rowHeight;
                        gndBlock.yBot = (y + 1) * rowHeight;
                        gndBlock.draw(canvas, gndBlockIcon, gndBlockRect);
                        break;
                    case ('B'):      //brick
                        brick.xTop = ((x - offset) * columnWidth) + pixel;
                        brick.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        brick.yTop = y * rowHeight;
                        brick.yBot = (y + 1) * rowHeight;
                        brick.draw(canvas, brickIcon, brickRect);
                        break;
                    case ('M'):      //regular mario
                        mario.xTop = ((x - offset) * columnWidth + mario.pixel);
                        mario.xBot = ((x - offset + 1) * columnWidth + mario.pixel);
                        mario.yTop = (y * rowHeight) + mario.jumpPixel;
                        mario.yBot = ((y + 1) * rowHeight) + mario.jumpPixel;

                        mario.draw(canvas, marioIcon, marioRect);
                        break;
                    case('S'):      //Super mario
                        supermario.xTop = ((x - offset) * columnWidth + mario.pixel);
                        supermario.xBot = ((x - offset + 1) * columnWidth + mario.pixel);
                        supermario.yTop = (y * rowHeight) + mario.jumpPixel;
                        supermario.yBot = ((y + 1) * rowHeight) + mario.jumpPixel;
                        supermario.draw(canvas, superMarioIcon, superMarioRect);
                        break;
                    case('F'):      //Fire mario
                        firemario.xTop = ((x - offset) * columnWidth + mario.pixel);
                        firemario.xBot = ((x - offset + 1) * columnWidth + mario.pixel);
                        firemario.yTop = (y * rowHeight) + mario.jumpPixel;
                        firemario.yBot = ((y + 1) * rowHeight) + mario.jumpPixel;
                        firemario.draw(canvas, fireMarioIcon, fireMarioRect);
                        break;
                    case('L'):      //pipe
                        pipe.xTop = ((x - offset) * columnWidth) + pixel;
                        pipe.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        pipe.yTop = y * rowHeight;
                        pipe.yBot = (y + 1) * rowHeight;
                        pipe.draw(canvas, pipeIcon, pipeRect);
                        break;
                    case('N'):      //plant
                        plant.xTop = ((x - offset) * columnWidth) + pixel;
                        plant.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        plant.yTop = y * rowHeight;
                        plant.yBot = (y + 1) * rowHeight;
                        plant.draw(canvas, plantIcon, plantRect);
                        break;
                    case('P'):      //powerup block
                        powerup.xTop = ((x - offset) * columnWidth) + pixel;
                        powerup.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        powerup.yTop = y * rowHeight;
                        powerup.yBot = (y + 1) * rowHeight;
                        powerup.draw(canvas, powerupIcon, powerupRect);
                        break;
                    case('O'):      //Coin
                        coin.xTop = ((x - offset) * columnWidth) + pixel;
                        coin.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        coin.yTop = y * rowHeight;
                        coin.yBot = (y + 1) * rowHeight;
                        coin.draw(canvas, coinIcon, coinRect);
                        break;
                    case('K'):      //Koopa
                        koopa.xTop = ((x - offset) * columnWidth) + pixel;
                        koopa.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        koopa.yTop = y * rowHeight;
                        koopa.yBot = (y + 1) * rowHeight;
                        koopa.draw(canvas, koopaIcon, koopaRect);
                        break;
                    case('G'):      //Goomba
                        goomba.xTop = ((x - offset) * columnWidth) + pixel;
                        goomba.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        goomba.yTop = y * rowHeight;
                        goomba.yBot = (y + 1) * rowHeight;
                        goomba.draw(canvas, goombaIcon, goombaRect);
                        break;
                    case('f'):      //flag
                        flag.xTop = ((x - offset) * columnWidth) + pixel;
                        flag.xBot = ((x - offset + 1) * columnWidth) + pixel;
                        flag.yTop = y * rowHeight;
                        flag.yBot = (y + 1) * rowHeight;
                        flag.draw(canvas, flagIcon, flagRect);
                        break;
                    default:
                        //Log.d("tag", "Unrecognized char in map level");
                }
            }
        }
    }

    public void createObjects(){
        for (int y = readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = readMap1.mapWidth - 1; x >= 0; x--) {
                char c = readMap1.mapGrid[x][y];

                switch (c) {
                    case ('Z'):      //ground block
                        //don't need to create each object for ground blocks
                        break;
                    case ('B'):      //brick
                        Brick brick = new Brick();
                        brickArray.add(brick);
                        brickArray.get(numBrick).xCoord = x;
                        brickArray.get(numBrick).yCoord = y;
                        /*Log.d("tag", "brick x: " + brickArray.get(numBrick).xCoord);
                        Log.d("tag", "brick y: " + brickArray.get(numBrick).yCoord);*/
                        numBrick++;
                        Log.d("tag", "numBrick: " + numBrick);

                        break;
                    case ('M'):      //regular mario

                        break;
                    case('L'):      //pipe

                        break;
                    case('N'):      //plant

                        break;
                    case('P'):      //powerup block

                        break;
                    case('O'):      //Coin

                        break;
                    case('K'):      //Koopa

                        break;
                    case('G'):      //Goomba

                        break;
                    default:
                        //Log.d("tag", "Unrecognized char in map level");
                }
            }
        }
    }
}
