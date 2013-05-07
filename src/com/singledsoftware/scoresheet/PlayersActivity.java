package com.singledsoftware.scoresheet;

import org.json.JSONArray;

import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class PlayersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
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
                startGame();
                break;
        }
    }

    public void startGame() {
        JSONArray players = new JSONArray();
        players.put(((EditText)this.findViewById(R.id.player0name_edit)).getText().toString());
        players.put(((EditText)this.findViewById(R.id.player1name_edit)).getText().toString());
        players.put(((EditText)this.findViewById(R.id.player2name_edit)).getText().toString());
        players.put(((EditText)this.findViewById(R.id.player3name_edit)).getText().toString());
        ParseObject game = new ParseObject("Game");
        game.put("players", players);
        try {
            game.save();
        } catch (ParseException e) {
            // TODO: deal with failed save
        }
        /*Intent intent = new Intent(this, GameActivity.class);
        String gameId = game.getObjectId();
        intent.putExtra("gameId", gameId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/
    }
    
}
