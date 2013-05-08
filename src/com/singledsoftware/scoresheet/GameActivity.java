package com.singledsoftware.scoresheet;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends Activity {
    
    private StatusFragment statusFragment;
    
    private ParseObject game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FragmentManager fragments = getFragmentManager();
        statusFragment = (StatusFragment)fragments.findFragmentById(R.id.status_fragment);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String gameId = extras.getString("gameId");
            if (gameId != null) {
                ParseQuery query = new ParseQuery("Game");
                query.whereEqualTo("gameId", gameId);
                List<ParseObject> gameList;
                try {
                    gameList = query.find();
                    if (gameList.size() > 0) {
                        game = gameList.get(0);
                    }
                }
                catch (ParseException e) {}
                statusFragment.setGame(game);
                statusFragment.updateScore();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);
        return true;
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
