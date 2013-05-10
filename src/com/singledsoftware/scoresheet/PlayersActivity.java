package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class PlayersActivity extends Activity {
    
    private EditText[] playerName = new EditText[4];
    
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_players);
        playerName[0] = (EditText)this.findViewById(R.id.player0name_edit);
        playerName[1] = (EditText)this.findViewById(R.id.player1name_edit);
        playerName[2] = (EditText)this.findViewById(R.id.player2name_edit);
        playerName[3] = (EditText)this.findViewById(R.id.player3name_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game = (Game)extras.getSerializable("game");
        }
        if (savedInstanceState != null) {
            game = (Game)savedInstanceState.getSerializable("game");
        }
        if (game == null) {
            game = new Game();
        }
        for (int p = 0; p < 4; p++) {
            playerName[p].setText(game.getPlayer(p));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable("game", game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.players_actionbar, menu);
        return true;
    }

    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action:
                this.finish();
                break;
            case R.id.ok_action:
                for (int p = 0; p < 4; p++) {
                    game.setPlayer(p, playerName[p].getText().toString());
                }
                Intent intent = new Intent(this, BidActivity.class);
                intent.putExtra("game", game);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
    
}
