package mockingjay.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoardView board = new BoardView(this);
        setContentView(board);
        System.out.println("Completed onCreate");

    }
}
