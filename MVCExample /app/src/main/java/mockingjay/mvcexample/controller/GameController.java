package mockingjay.mvcexample.controller;

import mockingjay.mvcexample.model.DigDug;
import mockingjay.mvcexample.model.GameMap;
import mockingjay.mvcexample.model.Monster;
import mockingjay.mvcexample.model.Rock;
import mockingjay.mvcexample.view.GameView;

/**
 * Created by Jay on 5/17/17.
 */

public class GameController {
    private long time;
    private GameView gameView;
    private DigDug digDug;
    private Rock[] rocks;
    private Monster[] monsters;
    private GameMap map;

    private GameThread gameThread;

    GameController(GameView gameView){
        gameThread = new GameThread(gameView);
        gameThread.start();

    }

    public void processInput(/*...*/){
        //if(moveRight){
        //digDug.moveRight();
        //}

    }

    public void update(){
        for (int i =0; i < monsters.length; i++) {
            monsters[i].attack();
        }
        for (int i =0; i < rocks.length; i++) {
            if (rocks[i].shouldFall()) {
                rocks[i].fall();
            }
        }
    }


}
