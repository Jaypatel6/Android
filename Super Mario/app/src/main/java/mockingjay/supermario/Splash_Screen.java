package mockingjay.supermario;


import android.content.Intent;
import android.os.Bundle;

public class Splash_Screen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
