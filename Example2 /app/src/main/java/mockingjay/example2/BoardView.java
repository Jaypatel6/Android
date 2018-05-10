package mockingjay.example2;

/**
 * Created by Jay on 4/28/17.
 */



        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Rect;
        import android.view.MotionEvent;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap icon;

    private boolean[][] grids;

    BoardView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        setWillNotDraw(false);
        System.out.println("Constructor");
    }

    @Override
    public void onDraw(Canvas canvas) {
        System.out.println("onDraw()");
        canvas.drawColor(Color.WHITE);
        Rect rect = new Rect();

        int width = getWidth();
        int height = getHeight();
        int rowSize = width/3;
        int columnSize = height/4;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++) {
                if (grids[i][j]) {
                    rect.set(i * rowSize, j * columnSize, (i + 1) * rowSize, (j + 1) * columnSize);
                    canvas.drawBitmap(icon, null, rect, null);
                }
            }

        //rect.set(0, 0, getWidth(), getHeight());
        //canvas.drawBitmap(icon, null, rect, null);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("SurfaceCreated()");
        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        grids = new boolean[3][4];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                grids[i][j] = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("touch event");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            int width = getWidth();
            int height = getHeight();
            int rowSize = width/3;
            int columnSize = height/4;

            grids[x/rowSize][y/columnSize] = !grids[x/rowSize][y/columnSize];
            invalidate();
        }

        return false;
    }
}