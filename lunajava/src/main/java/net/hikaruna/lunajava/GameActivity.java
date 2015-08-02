package net.hikaruna.lunajava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hikaru on 2015/05/08.
 */
public class GameActivity extends AppCompatActivity implements Game.OnGameListener {

    private Game game;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        game = (Game) findViewById(R.id.game);
        if (game == null) {
            setContentView(R.layout.acrivity_game);
            game = (Game) findViewById(R.id.game);
        }
        game.setOnGameListener(this);
    }

    /**
     * get Game instance.
     * this method should call after gameCreated().
     */
    public Game getGame() {
        return game;
    }

    @Override
    public void onGameCreated(Game game) {
    }

    @Override
    public void onGameResume() {
    }

    @Override
    public void onGamePause() {

    }
}
