package mockingjay.example;


        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.SurfaceView;
        import android.view.SurfaceHolder;
        import android.graphics.Rect;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

/**
 * Created by yashaswiniamaresh on 4/24/17.
 */

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap icons[];
    private int prevX;
    private int prevY;
    private List<Integer> indices;

    private int startRowNum;
    private int startColNum;
    public int numR = 3;
    public int numC = 2;

    public BoardView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        System.out.println("In Constructor");

        icons = new Bitmap[3];
        prevX = 0;
        prevY = 0;
        indices = new ArrayList<Integer>();
        startRowNum = 0;
        startColNum = 0;

        for(int i = 0; i < 3; i++) {
            indices.add(i);
            indices.add(i);
            indices.add(i);
            indices.add(i);
        }
        Collections.shuffle(indices);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("Touch event occured");

        int currX;
        int currY;
        int endRowNum = 0;
        int endColNum = 0;
        int width = getWidth();
        int height = getHeight();

        int rowHeight = height / numR;
        int columnWidth = width / numC;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = (int) event.getX();
            prevY = (int) event.getY();

            startRowNum = prevY / rowHeight;
            startColNum = prevX / columnWidth;
        }

        else if(event.getAction() == MotionEvent.ACTION_UP) {
            currX = (int) event.getX();
            currY = (int) event.getY();

            endRowNum = currY / rowHeight;
            endColNum = currX / columnWidth;

            System.out.println("StartRowNum : " + startRowNum + " StartColNum: " + startColNum);
            System.out.println("EndRowNum: " + endRowNum + " EndColNum: " + endColNum);


            if(startRowNum == endRowNum) {
                if(startColNum > endColNum) {
                    System.out.println("R to L");
                    swapImages(startRowNum, startColNum, endRowNum, endColNum);
                }
                else if(startColNum < endColNum) {
                    System.out.println("L to R");
                    swapImages(startRowNum, startColNum, endRowNum, endColNum);
                }
                else {
                    System.out.println("Unrecognized action");
                }
            }

            else if(startColNum == endColNum) {
                if(startRowNum < endRowNum) {
                    System.out.println("Top to bottom");
                    swapImages(startRowNum, startColNum, endRowNum, endColNum);
                }
                else if(startRowNum > endRowNum) {
                    System.out.println("Bottom to top");
                    swapImages(startRowNum, startColNum, endRowNum, endColNum);
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
        int srcIndex = startRow * 2 + startCol;
        int destIndex = endRow * 2 + endCol;

        int srcVal = indices.get(srcIndex);
        int destVal = indices.get(destIndex);

        indices.set(srcIndex, destVal);
        indices.set(destIndex, srcVal);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        System.out.println("OnDraw called");
        canvas.drawColor(Color.WHITE);
        Rect rect = new Rect();

        int width = getWidth();
        int height = getHeight();

        int rowHeight = height / numR;
        int columnWidth = width / numC;

        for(int i = 0; i < numR; ++i) {
            for(int j = 0; j < numC; ++j) {
                rect.set(j * columnWidth, i * rowHeight, (j + 1) * columnWidth, (i + 1) * rowHeight);
                canvas.drawBitmap(icons[indices.get(i * 2 + j)], null, rect, null);
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("Surface created");
        setWillNotDraw(false);

        icons[0] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_1);
        icons[1] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_2);
        icons[2] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_3);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
