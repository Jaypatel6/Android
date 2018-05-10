package mockingjay.candycrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Rect;

import static java.lang.Math.abs;

/* EECS 40 Assignment 2: Candy Crush */
/* Authors: Team MockingJay - Hikaru Kasai and Jay Patel */
/* Some code adapted from Yashaswini Amaresh - Discussion 4 example */

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap icons[];
    private int prevX;
    private int prevY;
    private int startRow;
    private int startCol;
    //number of candy types
    private int numCandy = 4;
    //grid size
    private int numRow = 5;
    private int numCol = 10;
    private int[][] grid = new int[numCol][numRow]; //grid containing candy type
    private int[][] gridRemove = new int[numCol][numRow]; //grid containing candies to remove when checking for match
    // Scoring 
    private int maxScore =  1500;
    private int Score = 0;
    // max scoring for winning  the game

    public BoardView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        System.out.println("In Constructor");

        icons = new Bitmap[numCandy];
        //prevX = 0;
        //prevY = 0;
        //startRow = 0;
        //startCol = 0;
        //initialize grid with random candies
        boolean match;
        for(int x = 0; x < numCol; x++){
            for(int y = 0; y < numRow; y++){
                do {
                    grid[x][y] = (int) (Math.random() * numCandy);
                    match = checkSetup(x, y);   //generate new random candy if true since 3 or more match detected
                }while (match == true);
            }
        }
        //copy grid
        for(int x = 0; x < numCol; x++){
            for(int y = 0; y < numRow; y++) {
                gridRemove[x][y] = grid[x][y];
            }
        }
    }
    public boolean checkGenerate(int x, int y){
        int candyType = grid[x][y];
        if((x - 1 >= 0)&&(x - 2 >= 0)) {
            if ((grid[x - 1][y] == candyType) && (grid[x - 2][y] == candyType)) {  //when 3 candies in row match
                return true;
            }
        }
        if((x - 1 >= 0)&&(x + 1 < numCol)) {
            if ((grid[x - 1][y] == candyType) && (grid[x + 1][y] == candyType)) {  //when 3 candies in row match
                return true;
            }
        }
        if((x + 1 < numCol)&&(x + 2 < numCol)) {
            if ((grid[x + 1][y] == candyType) && (grid[x + 2][y] == candyType)) {  //when 3 candies in row match
                return true;
            }
        }

        if ((grid[x][y + 1] == candyType) && (grid[x][y + 2] == candyType)) {  //when 3 candies in column match
            return true;
        }
        return false;
    }

    public boolean checkSetup(int x, int y){
        //returns true when 3 or more of same candy in row/column
        int candyType = grid[x][y];

        if((y - 1 < 0)&&(x - 1 < 0)){    //corner case
            return false;
        }
        if((x - 1 >= 0)&&(x - 2 >= 0)) {
            if ((grid[x - 1][y] == candyType) && (grid[x - 2][y] == candyType)) {  //when 3 candies in row match
                return true;
            }
        }
        if ((y - 1 >= 0) && (y - 2 >= 0)) {
            if ((grid[x][y - 1] == candyType) && (grid[x][y - 2] == candyType)) {  //when 3 candies in column match
                return true;
            }
        }
        return false;   //no 3 in a row/column match found
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("Touch event occured");

        int currX;
        int currY;
        int endRow;
        int endCol;
        int width = getWidth();
        int height = getHeight() - (getHeight()/10);

        int rowHeight = height / numRow;
        int columnWidth = width / numCol;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = (int) event.getX();
            prevY = (int) event.getY();

            startRow = prevY / rowHeight;
            startCol = prevX / columnWidth;
            System.out.println("StartRow: " + startRow + " startcol: " + startCol);
        }

        else if(event.getAction() == MotionEvent.ACTION_UP) {
            currX = (int) event.getX();
            currY = (int) event.getY();

            endRow = currY / rowHeight;
            endCol = currX / columnWidth;

            System.out.println("StartRowNum : " + startRow + " StartColNum: " + startCol);
            System.out.println("EndRowNum: " + endRow + " EndColNum: " + endCol);


            if(startRow == endRow) {
                if(startCol - endCol == 1) {
                    System.out.println("R to L");
                    swapImages(startRow, startCol, endRow, endCol);
                }
                else if(startCol - endCol == -1) {
                    System.out.println("L to R");
                    swapImages(startRow, startCol, endRow, endCol);
                }
                else {
                    System.out.println("Unrecognized action");
                }
            }

            else if(startCol == endCol) {
                if(startRow - endRow == 1) {
                    System.out.println("Top to bottom");
                    swapImages(startRow, startCol, endRow, endCol);
                }
                else if(startRow - endRow == -1) {
                    System.out.println("Bottom to top");
                    swapImages(startRow, startCol, endRow, endCol);
                }
            }
            else {
                System.out.println("Invalid move");
            }
            invalidate();
        }

        return true;
    }

    void swapImages(int startRow, int startCol, int endRow, int endCol){
        System.out.println("Swap called");
        int srcCandy = grid[startCol][startRow];
        int destCandy = grid[endCol][endRow];
        grid[startCol][startRow] = destCandy;
        grid[endCol][endRow] = srcCandy;

        //check for matching candies in all 4 directions
        int count = 1;
        checkMoveLeft(endCol, endRow, count);
        count = 1;
        checkMoveRight(endCol, endRow, count);
        count = 1;
        checkMoveUp(endCol, endRow, count);
        count = 1;
        checkMoveDown(endCol, endRow, count);

        if (checkMove(endCol, endRow) == false){    //no match found
            revertSwap(startRow, startCol, endRow, endCol);
        }
        else{
            shiftDown();
            //checkBoard();
        }
    }

    void revertSwap(int startRow, int startCol, int endRow, int endCol){       //switch candies when swap is illegal
        System.out.println("revertSwap called");
        int destCandy = grid[startCol][startRow];
        int srcCandy = grid[endCol][endRow];
        grid[startCol][startRow] = srcCandy;
        grid[endCol][endRow] = destCandy;
    }

    @Override
    protected void onDraw(Canvas canvas) //called every time motion detected
    {
        System.out.println("OnDraw called");
        canvas.drawColor(Color.CYAN);
        Rect gridRect = new Rect();

        int width = getWidth();
        int height = getHeight() - (getHeight()/10);
        int rowHeight = height / numRow;
        int columnWidth = width / numCol;

        //draw rectangle for score
        Rect scoreRect = new Rect();
        Paint scoreRectPt = new Paint();
        scoreRectPt.setColor(Color.rgb(190, 249, 134));
        scoreRectPt.setStrokeWidth(10);
        canvas.drawRect(0, (height + height/50), width, getHeight(), scoreRectPt);

        //draw lines
        Paint linePaint = new Paint();
        linePaint.setColor(Color.rgb(70, 80, 240));
        linePaint.setStrokeWidth(8);
        canvas.drawLine(0, (height + height/50), width,(height + height/50), linePaint);
        canvas.drawLine(width/2, (height + height/50), width/2, getHeight(), linePaint);

        //draw score
        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);
        if(Score >= maxScore) {
            String s = "Score: You Win";
            System.exit(1);
            int rectHeight = getHeight() - (height + height/50);
            canvas.drawText(s, columnWidth - columnWidth/2, (getHeight() - rectHeight/4), scorePaint);
        }
        else{
            String s = "Score: " + Score;
            int rectHeight = getHeight() - (height + height/50);
            canvas.drawText(s, columnWidth - columnWidth/2, (getHeight() - rectHeight/4), scorePaint);
        }


        //draw target
        Paint tgtPaint = new Paint();
        tgtPaint.setColor(Color.BLACK);
        tgtPaint.setTextSize(50);
        String tgt = "Target: " + maxScore;
        int rectHeight = getHeight() - (height + height/50);
        canvas.drawText(tgt, width/2 + columnWidth/2, (getHeight() - rectHeight/4), tgtPaint);

        int sameCandy = -1;

        //draw candies
        for(int i = 0; i < numRow; i++) {
            for(int j = 0; j < numCol; j++) {
                int topX = j * columnWidth;
                int topY = i * rowHeight;
                int botX = (j + 1) * columnWidth;
                int botY = (i + 1) * rowHeight;
                gridRect.set(topX, topY, botX, botY);

                int candyType = grid[j][i];
                canvas.drawBitmap(icons[candyType], null, gridRect, null);
                //System.out.println("candy at i: " + i + " j: "+ j+ "is: "+ candyType);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("Surface created");
        setWillNotDraw(false);

        icons[0] = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
        icons[1] = BitmapFactory.decodeResource(getResources(), R.drawable.red);
        icons[2] = BitmapFactory.decodeResource(getResources(), R.drawable.green);
        icons[3] = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void checkMoveLeft(int endCol, int endRow, int count) {
        //System.out.println("checkMoveLeft called");
        int candyType = grid[endCol][endRow];

        while (endCol - count >= 0) {  //look for matching candies to left side of row
            if (grid[endCol - count][endRow] == candyType) {
                gridRemove[endCol][endRow] = -1;
                gridRemove[endCol - count][endRow] = -1;  //store candy to remove
                //System.out.println("match to left: grid " + (endCol - count) + " " + endRow);
                //System.out.println("set grid value to: " + gridRemove[endCol - count][endRow]);
                count++;
                checkMoveLeft(endCol, endRow, count);  //keep looking for more matches
            }
            else {
                break;  //neighboring candy is not the same
            }
        }
    }

    public void checkMoveRight(int endCol, int endRow, int count) {
        //System.out.println("checkMoveRight called");
        int candyType = grid[endCol][endRow];

        while (endCol + count < numCol) {  //look for matching candies to right
            if (grid[endCol + count][endRow] == candyType) {
                gridRemove[endCol][endRow] = -1;
                gridRemove[endCol + count][endRow] = -1;  //store candy to remove
                //System.out.println("match to right: grid" + (endCol + count) + " " + endRow);
                //System.out.println("set grid value to: " + gridRemove[endCol + count][endRow]);
                count++;
                checkMoveRight(endCol, endRow, count);  //keep looking for more matches
            }
            else {
                break;  //neighboring candy is not the same
            }
        }
    }
    public void checkMoveUp(int endCol, int endRow, int count) {
        //System.out.println("checkMoveUp called");
        int candyType = grid[endCol][endRow];

        while (endRow - count >= 0) {  //look for matching candies to top
            if (grid[endCol][endRow - count] == candyType) {
                gridRemove[endCol][endRow] = -1;
                gridRemove[endCol][endRow - count] = -1;  //store candy to remove
                //System.out.println("match to top: grid " + endCol + " " + (endRow - count));
                //System.out.println("set grid value to: " + gridRemove[endCol][endRow - count]);
                count++;
                checkMoveUp(endCol, endRow, count);  //keep looking for more matches
            }
            else {
                break;  //neighboring candy is not the same
            }
        }
    }
    public void checkMoveDown(int endCol, int endRow, int count) {
        //System.out.println("checkMoveDown called");
        int candyType = grid[endCol][endRow];

        while (endRow + count < numRow) {  //look for matching candies to top
            if (grid[endCol][endRow + count] == candyType) {
                gridRemove[endCol][endRow] = -1;
                gridRemove[endCol][endRow + count] = -1;  //store candy to remove
                //System.out.println("match to bottom: grid " + endCol + " " + (endRow + count));
                //System.out.println("set grid value to: " + gridRemove[endCol][endRow + count]);
                count++;
                checkMoveDown(endCol, endRow, count);  //keep looking for more matches
            }
            else {
                break;  //neighboring candy is not the same
            }
        }
    }
    public boolean checkMove(int endCol, int endRow) {
        int combo = 0;
        boolean matchFound = false;
        //check row
        for(int x = 0; x < numCol; x++){
            if (gridRemove[x][endRow] == -1) {    //find matching candies, increase combo when found
                combo++;
            }
            else {       //no matching candy to next, reset combo
                combo = 0;
            }
            if (combo == 3) {                 //remove 3 or greater combo
                //call shiftCandy()
                Score = Score + (5 * combo);
                gridRemove[x - 2][endRow] = -2;     //grids set as -2 means combo to remove for row
                gridRemove[x - 1][endRow] = -2;
                gridRemove[x][endRow] = -2;

                matchFound = true;
                System.out.println("3 or greater combo in row");
            }
            else if(combo > 3){
                gridRemove[x][endRow] = -2;
                Score = Score + (5 * combo);
            }
        }
        //check column
        combo = 0;
        for(int y = 0; y < numRow; y++){
            if (gridRemove[endCol][y] == -1) {    //find matching candies, increase combo when found
                combo++;
            }
            else {       //no matching candy to next, reset combo
                combo = 0;
            }
            if (combo >= 3) {                 //remove 3 or greater combo
                //call shiftCandy()
                Score = Score + (5 * combo);
                gridRemove[endCol][y - 2] = -3;     //-3 means remove for column
                gridRemove[endCol][y - 1] = -3;
                gridRemove[endCol][y] = -3;

                matchFound = true;
                System.out.println("3 or greater combo in col");
            }
        }
        return matchFound;
    }

    //loop thru gridRemove, if equals to -2 (row) or -3 (col), remove it and shift everything down
    protected void shiftDown(){
        for(int x = 0; x < numCol; x++){    //row case
            for(int y = 0; y < numRow; y++){
                if(gridRemove[x][y] == -2){
                    shiftDownRowRest(x, y);
                    gridRemove[x][y] = -4;      //mark coord as shifted
                }
                if(gridRemove[x][y] == -3){
                    shiftDownCol(x, y);
                    gridRemove[x][y] = -4;
                }
            }
        }
    }
    public void shiftDownCol(int col, int row){
        //shift down by number of combo
        int combo = countColCombo(col);
        System.out.println("score: " + Score);
        for(int y = row; y >= 0; y--){
            grid[col][y] = shiftDownRow(col, (y - combo));
        }
    }

    public Integer shiftDownRow(int endCol, int endRow){
        //return candy type above removed candy
        boolean match;
        if(endRow > 0) {
            int candyAbove = grid[endCol][endRow - 1];
            return candyAbove;
        }
        else if(endRow == 0){
            //System.out.println("generated new candies for top row");
            do {
                grid[endCol][endRow] = (int) (Math.random() * numCandy);
                match = checkGenerate(endCol, endRow);   //generate new random candy if true since 3 or more match detected
            }while (match == true);
            return grid[endCol][endRow];
        }
        return 0;   //should not return from here
    }

    protected void shiftDownRowRest(int col, int row){
        for(int y = row; y >= 0; y--){
            grid[col][y] = shiftDownRow(col, y);
        }
    }

    public Integer countColCombo(int endCol) {
        int combo = 0;
        for (int y = 0; y < numRow; y++) {
            if (gridRemove[endCol][y] == -1) {    //find matching candies, increase combo when found
                combo++;
            } else {       //no matching candy to next, reset combo
                combo = 0;
            }

        }
        System.out.println("combo is " + combo);
        return combo;
    }
    public Integer countRowCombo(int endRow) {
        int combo = 0;
        for (int x = 0; x < numCol; x++) {
            if (gridRemove[endRow][x] == -1) {    //find matching candies, increase combo when found
                combo++;
            } else {       //no matching candy to next, reset combo
                combo = 0;
            }

        }
        System.out.println("combo is " + combo);
        return combo;
    }

    public void checkBoard(){   //check for any matching candies after board is shifted upon swipe
        System.out.println("checkBoard called");
        for(int x = 0; x < numCol; x++) {
            for (int y = 0; y < numRow; y++) {
                int count = 1;
                checkMoveLeft(x, y, count);
                count = 1;
                checkMoveRight(x, y, count);
                count = 1;
                checkMoveUp(x, y, count);
                count = 1;
                checkMoveDown(x, y, count);

                if(checkMove(x, y) == true){    //new combo found
                    shiftDown();
                }
            }
        }
    }

}