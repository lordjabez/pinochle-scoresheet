package com.singledsoftware.scoresheet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class PlayersActivity extends Activity {
    
    private EditText[] playerName = new EditText[4];
    
    private ParseObject game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        playerName[0] = (EditText)this.findViewById(R.id.player0name_edit);
        playerName[1] = (EditText)this.findViewById(R.id.player1name_edit);
        playerName[2] = (EditText)this.findViewById(R.id.player2name_edit);
        playerName[3] = (EditText)this.findViewById(R.id.player3name_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String gameId = extras.getString("gameId");
            if (gameId != null) {
                ParseQuery query = new ParseQuery("Game");
                query.whereEqualTo("id", gameId);
                List<ParseObject> gameList;
                try {
                    gameList = query.find();
                    if (gameList.size() > 0) {
                        game = gameList.get(0);
                    }
                }
                catch (ParseException e) {}
                if (game != null) {
                    JSONArray players = game.getJSONArray("players");
                    for (int p = 0; p < 4; p++) {
                        try {
                            playerName[p].setText(players.getInt(p));
                        }
                        catch (JSONException e) {}
                    }
                }
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
                startGame();
                break;
        }
    }

    public void startGame() {
        JSONArray players = new JSONArray();
        for (int p = 0; p < 4; p++) {
            players.put(playerName[p].getText().toString());
        }
        game.put("players", players);
        game.saveEventually();
        Intent intent = new Intent(this, GameActivity.class);
        String gameId = game.getString("id");
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }
    
}
