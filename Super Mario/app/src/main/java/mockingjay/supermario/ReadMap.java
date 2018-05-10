package mockingjay.supermario;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ReadMap {

    char[][] mapGrid;
    String line = null;
    BufferedReader br;
    int mapHeight = 0;
    int mapWidth = 0;
    ArrayList<String> lineArray = new ArrayList<>();

    public void createMap(String filename, Context context){
        InputStreamReader InputSR = null;
        BufferedReader br = null;
        String line = null;

        try {
            InputSR = new InputStreamReader(context.getAssets().open(filename));
            br = new BufferedReader(InputSR);

            //find map height and width and create grid
            while ((line = br.readLine()) != null) {
                mapHeight++;
                mapWidth = line.length();
                lineArray.add(line);
            }
            mapGrid = new char[mapWidth][mapHeight];
            br.close();
            //Log.d("tag", "mapheight: " + mapHeight);
            //Log.d("tag", "mapWidth: " + mapWidth);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        copyMap();
    }

    public void copyMap(){
        //copy map layout into grid
        for(int y = 0; y < mapHeight; y++){
            String line = lineArray.get(y);
            for(int x = 0; x < line.length(); x ++){
                char c = line.charAt(x);
                if((c == ' ') || (c == 'X')){   //assign X to blank grid
                    mapGrid[x][y] = 'X';
                }
                else {
                    mapGrid[x][y] = c;      //copy grid char into map grid
                    //Log.d("tag", "position " + x + " : " + c);
                }
                //Log.d("tag", " " + mapGrid[x][y]);
            }
        }
    }
}
