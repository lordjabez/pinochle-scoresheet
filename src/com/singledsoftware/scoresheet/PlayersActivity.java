// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Prompts user to enter the names of all four players.
 * 
 * @author Judson D Neer
 * @see ScoresheetActivity
 */
public class PlayersActivity extends ScoresheetActivity {

    // These four text fields contain the player names.
    private final EditText[] playerName = new EditText[4];

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        // Grab references to each of the player name edit widgets.
        playerName[0] = (EditText)this.findViewById(R.id.player0name_edit);
        playerName[1] = (EditText)this.findViewById(R.id.player1name_edit);
        playerName[2] = (EditText)this.findViewById(R.id.player2name_edit);
        playerName[3] = (EditText)this.findViewById(R.id.player3name_edit);
        // Populate the edit fields with names from the existing game object. This is
        // done as a convenience so the user doesn't need to retype them between games.
        for (int p = 0; p < 4; p++) {
            playerName[p].setText(game.getPlayer(p));
        }
        // Finally create a new game object.
        game = new Game();
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.players_actionbar, menu);
        return true;
    }

    /**
     * Executes an action based on menu selection.
     * 
     * @param item The clicked menu item that called this methods
     */
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action:
                startActivity(new ScoresheetIntent(this, MainActivity.class, game));
                break;
            case R.id.clear_action:
                // Erase all player names from the view widgets.
                for (int p = 0; p < 4; p++) {
                    playerName[p].setText("");
                }
                break;
            case R.id.ok_action:
                // Copy the names from the edit fields to the game object.
                for (int p = 0; p < 4; p++) {
                    game.setPlayer(p, playerName[p].getText().toString());
                }
                startActivity(new ScoresheetIntent(this, BidActivity.class, game));
                break;
        }
    }

}
