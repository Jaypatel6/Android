package mockingjay.supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Mario extends MovingObject {
    protected boolean alive;
    protected int xTop, yTop;
    protected int xBot, yBot;
    protected int objectHeight;
    protected int objectWidth;
    protected int pixel;
    private int pixelToSkip = 16;
    protected int jumpPixel;
    private int grav = 23; //default should be about 25
    double time;
    double endTime;
    double startTime;
    static int initVel = 135;   //default should be about 130
    private int jumpVel = initVel;
    private boolean falling = false;
    protected int groundTopY;
    protected int groundBotY;

    protected int xCoord;
    protected int yCoord;       //grid coordinates
    protected int pxlCount;

    protected boolean fireMario;
    protected boolean superMario;
    protected boolean regMario;

    public void draw(Canvas canvas, Bitmap marioIcon, Rect marioRect) {
        marioRect.set(xTop, yTop, xBot, yBot);
        canvas.drawBitmap(marioIcon, null, marioRect, null);
        //Log.d("tag", "xTop: "+xTop+" yTop: "+yTop + " xBot: "+ xBot+" yBot: " + yBot);
    }

    public void attack() {
        //shoot fire when fire mario
    }

    public void moveLeft(GameView gameView) {

        if (xTop > (int) (gameView.width * 0.5)) {
            if(gameView.gameGrid[xCoord - 1][yCoord] == 'X') {
                pixel -= pixelToSkip;          //move mario and keep map static when on right side of screen
                gameView.pixel -= 0;
                xTop -= pixelToSkip;
                xBot -= pixelToSkip;
            }
            else if(gameView.gameGrid[xCoord - 1][yCoord] == 'O') {     //coin
                pixel -= pixelToSkip;          //move mario and keep map static when on right side of screen
                gameView.pixel -= 0;
                xTop -= pixelToSkip;
                xBot -= pixelToSkip;
                gameView.gameGrid[xCoord - 1][yCoord] = 'X';
                gameView.readMap1.mapGrid[xCoord - 1][yCoord] = 'X';
                gameView.score += 200;
            }
            else{
                Log.d("tag", "object to left: " + gameView.gameGrid[xCoord][yCoord]);
            }
        }
        else if (xTop > 0) {
            if (gameView.pixel >= (gameView.offset * objectWidth)) {     //move mario and keep map static when left edge of level shown
                if(gameView.gameGrid[xCoord - 1][yCoord] == 'X') {
                    pixel -= pixelToSkip;
                    gameView.pixel += 0;
                }
                else if(gameView.gameGrid[xCoord - 1][yCoord] == 'O') {
                    pixel -= pixelToSkip;
                    gameView.pixel += 0;
                    gameView.gameGrid[xCoord - 1][yCoord] = 'X';
                    gameView.readMap1.mapGrid[xCoord - 1][yCoord] = 'X';
                    gameView.score += 200;
                }
                else{
                    Log.d("tag", "object to left: " + gameView.gameGrid[xCoord][yCoord]);
                }
            }
            else {               //shift map to right and keep mario "static" when mario in middle
                if(gameView.gameGrid[xCoord - 1][yCoord] == 'X') {
                    pixel -= 0;
                    gameView.pixel += pixelToSkip;
                    pxlCount++;
                    //Log.d("tag", "pxlCount: "+ pxlCount);
                }
                else if(gameView.gameGrid[xCoord - 1][yCoord] == 'O') {
                    pixel -= 0;
                    gameView.pixel += pixelToSkip;
                    pxlCount++;
                    gameView.gameGrid[xCoord - 1][yCoord] = 'X';
                    gameView.readMap1.mapGrid[xCoord - 1][yCoord] = 'X';
                    gameView.score += 200;
                    //Log.d("tag", "pxlCount: "+ pxlCount);
                }
            }
        }
        updateMarioCoordLeft(gameView);
    }

    public void moveRight(GameView gameView) {       //map stays static and mario shifts to right
        if (xBot < gameView.width) {
            if(gameView.gameGrid[xCoord + 1][yCoord] == 'X') {
                gameView.pixel -= 0;
                pixel += pixelToSkip;
                xTop += pixelToSkip;
                xBot += pixelToSkip;
            }
            else if(gameView.gameGrid[xCoord + 1][yCoord] == 'O') {     //coin
                gameView.pixel -= 0;
                pixel += pixelToSkip;
                xTop += pixelToSkip;
                xBot += pixelToSkip;
                gameView.gameGrid[xCoord + 1][yCoord] = 'X';
                gameView.readMap1.mapGrid[xCoord + 1][yCoord] = 'X';
                gameView.score += 200;
            }
            else{
                Log.d("tag", "object to right: " + gameView.gameGrid[xCoord][yCoord]);
            }
            updateMarioCoordRight(gameView);
        }
    }

    public void jump(GameView gameView) {
        endTime = System.nanoTime();
        time = (endTime - startTime) * 0.000000001;
        updateMarioCoordJump(gameView);
        //Log.d("tag", "delta time: " + time);
        if(gameView.gameGrid[xCoord][yCoord - 1] == 'X') {
            jumpVel -= grav * time;
            jumpPixel -= jumpVel * time;
            //Log.d("tag", "jumpPixel: " + jumpPixel);

            if (jumpVel <= 0) {
                falling = true;
            }
        }
        else if(gameView.gameGrid[xCoord][yCoord - 1] == 'O'){  //collect coin
            jumpVel -= grav * time;
            jumpPixel -= jumpVel * time;
            gameView.gameGrid[xCoord][yCoord - 1] = 'X';
            gameView.readMap1.mapGrid[xCoord][yCoord - 1] = 'X';
            gameView.score += 200;
            if (jumpVel <= 0) {
                falling = true;
            }
        }
        if (falling) {
            char c = gameView.gameGrid[xCoord][yCoord + 1]; //check for objects below
            //Log.d("tag", "object below: " + c);
            if (c != 'X') {
                falling = false;
                yTop = yCoord * objectHeight;
                yBot = (yCoord * objectHeight) + objectHeight;
                jumpPixel = 0;      //reset
                jumpVel = initVel;      //reset
                gameView.jumpPressed = false;
                //Log.d("tag", "jumpPressed is false");
            }
        }
        //update mario's grid coordinate
        updateMarioCoordJump(gameView);
    }

    private void updateMarioCoordRight(GameView gameView) {
        for (int y = gameView.readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = gameView.readMap1.mapWidth - 1; x >= 0; x--) {
                if(gameView.gameGrid[x][y] == 'M') {
                    //move right
                    if( (xTop >= ((x + 1 - gameView.offset) * objectWidth)) && (gameView.pixel == 0) ){         //only when map hasn't shifted yet
                        gameView.gameGrid[x + 1][y] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        xCoord += 1;
                        //Log.d("tag", "xTop: " + xTop);
                        //Log.d("tag", "x + 1 top: " + ((x + 1 - gameView.offset) * objectWidth));
                        Log.d("tag", "xCoord: " + xCoord);
                        //Log.d("tag", "x: " + x);
                    }
                    else if(gameView.pixel > 0){
                        int pixelOffset = (int)((float)gameView.pixel/(float)objectWidth);
                        int newOffset = gameView.offset - pixelOffset;
                        //Log.d("tag", "newoffset: " + newOffset);
                        if(xTop >= ((x + 1 - newOffset) * objectWidth)){
                            gameView.gameGrid[x + 1][y] = 'M';
                            gameView.gameGrid[x][y] = 'X';
                            xCoord += 1;
                            Log.d("tag", "xCoord: " + xCoord);
                            //Log.d("tag", "x: " + x);
                        }
                    }
                }
            }
        }
    }

    private void updateMarioCoordLeft(GameView gameView) {
        for (int y = gameView.readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = gameView.readMap1.mapWidth - 1; x >= 0; x--) {
                if(gameView.gameGrid[x][y] == 'M') {
                    //move left when mario on right side of screen
                    if( xTop <= ((x - 1 - gameView.offset) * objectWidth) ){
                        gameView.gameGrid[x - 1][y] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        xCoord -= 1;
                    }
                    //move left when mario in middle (map shifts and mario static)
                    else if(pxlCount == 4){
                        gameView.gameGrid[x - 1][y] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        xCoord -= 1;
                        pxlCount = 0; //reset
                    }
                    else if( (xTop <= ((x - 1) * objectWidth)) && (gameView.pixel >= (gameView.offset * objectWidth)) ){
                        gameView.gameGrid[x - 1][y] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        xCoord -= 1;
                    }
                    Log.d("tag", "xCoord: " + xCoord);
                    Log.d("tag", "x: " + x);
                    //for debug
                        /*for (int j = gameView.readMap1.mapHeight - 1; j >= 7; j--) {
                            for (int i = gameView.readMap1.mapWidth - 1; i >= 0; i--) {
                                Log.d("tag", "grid: " + gameView.gameGrid[i][j]);
                            }
                        }*/
                }
            }

        }
    }
    private void updateMarioCoordJump(GameView gameView) {

        for (int y = gameView.readMap1.mapHeight - 1; y >= 0; y--) {
            for (int x = gameView.readMap1.mapWidth - 1; x >= 0; x--) {
                if(gameView.gameGrid[x][y] == 'M') {
                    //when jumping up
                    int yAbove = (y - 1) * gameView.rowHeight + objectHeight;
                    if ( (yBot <=  yAbove)  && ( ((y - 1) * objectHeight) > 0) ) {
                        gameView.gameGrid[x][y - 1] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        yCoord = y - 1;
                        //Log.d("tag", "yTop: " + mario.yTop);
                        //Log.d("tag", "yTop above grid: " + ((y - 1) * rowHeight));
                        //Log.d("tag", "jumping yCoord: " + yCoord);

                    }
                    //when falling down
                    int yBelow = ((y + 1) * objectHeight) + objectHeight;
                    if ( (yBot >= yBelow) && (yBot - yBelow <= objectHeight) ){
                        gameView.gameGrid[x][y + 1] = 'M';
                        gameView.gameGrid[x][y] = 'X';
                        yCoord = y + 1;
                        //Log.d("tag", "yTop: " + mario.yTop);
                        //Log.d("tag", "yTop above grid: " + ((y - 1) * rowHeight));
                        //Log.d("tag", "falling yCoord: " + yCoord);
                        //Log.d("tag", "yBot: " + yBot);
                        //Log.d("tag", "y + 1 bot: " + yBelow);
                        //Log.d("tag", "diff: " + (yBot - yBelow));
                    }
                    else if( yBot - yBelow >= objectHeight){
                        gameView.gameGrid[x][y + 2] = 'M';
                        gameView.gameGrid[x][y + 1] = 'X';
                        gameView.gameGrid[x][y] = 'X';
                        yCoord = y + 2;
                        //Log.d("tag", "diff: " + (yBot - yBelow));
                    }
                }
            }
        }
    }
}
