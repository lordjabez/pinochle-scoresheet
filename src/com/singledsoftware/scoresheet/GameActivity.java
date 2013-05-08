package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.MenuItem;

public class GameActivity extends Activity {
    
    private StatusFragment statusFragment;
    
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FragmentManager fragments = getFragmentManager();
        statusFragment = (StatusFragment)fragments.findFragmentById(R.id.status_fragment);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game = (Game)extras.getSerializable("game");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        if (instanceState != null && game == null) {
            game = (Game)instanceState.getSerializable("game");
        }
        if (game == null) {
            game = new Game();
        }
        statusFragment.update(game);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable("game", game);
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_action:
                this.finish();
                break;
            case R.id.next_action:
                //startGame();
                break;
        }
    }

}
