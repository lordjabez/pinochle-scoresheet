// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Provides interface for user to enter the meld for a hand.
 * 
 * @author Judson D Neer
 * @see ScoresheetActivity
 */
public class MeldActivity extends ScoresheetActivity {

    // Meld can't be less than zero. The default melds were chosen
    // based on anecdotally typical melds in the Neer household.
    private static final int MINIMUM_MELD = 0;
    private static final int DEFAULT_MELD_BIDDER = 15;
    private static final int DEFAULT_MELD_NONBIDDER = 7;
    
    // References to various view widgets.
    private TextView team0Text;
    private TextView team1Text;
    private TextView meld0Text;
    private TextView meld1Text;
    private Button meld0DownButton;
    private Button meld1DownButton;
    private StatusFragment statusFragment;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meld);
        // Grab references to various view widgets.
        team0Text = (TextView)this.findViewById(R.id.team0_text);
        team1Text = (TextView)this.findViewById(R.id.team1_text);
        meld0Text = (TextView)this.findViewById(R.id.meld0_text);
        meld1Text = (TextView)this.findViewById(R.id.meld1_text);
        meld0DownButton = (Button)this.findViewById(R.id.meld0_down_button);
        meld1DownButton = (Button)this.findViewById(R.id.meld1_down_button);
        statusFragment = (StatusFragment)getFragmentManager().findFragmentById(R.id.status_fragment);
        // Set the headers above the meld adjustment buttons.
        team0Text.setText(game.getPlayer(0) + " & " + game.getPlayer(2));
        team1Text.setText(game.getPlayer(1) + " & " + game.getPlayer(3));
        // Set the meld value indicators.
        if (game.bidderOnTeam(0)) {
            meld0Text.setText(DEFAULT_MELD_BIDDER + "");
            meld1Text.setText(DEFAULT_MELD_NONBIDDER + "");
        }
        else {
            meld0Text.setText(DEFAULT_MELD_NONBIDDER + "");
            meld1Text.setText(DEFAULT_MELD_BIDDER + "");
        }
        // Update the status widget with new game data.
        statusFragment.update(game);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_actionbar, menu);
        return true;
    }

    /**
     * Adjusts the meld values up or down.
     * 
     * @param button The clicked button that called this method 
     */
    public void onClick(View button) {
        // Grab the relevant meld values and adjust accordingly.
        int meld0 = Integer.parseInt(meld0Text.getText().toString());
        int meld1 = Integer.parseInt(meld1Text.getText().toString());
        switch (button.getId()) {
            case R.id.meld0_up_button: meld0++; break;
            case R.id.meld0_down_button: meld0--; break;
            case R.id.meld1_up_button: meld1++; break;
            case R.id.meld1_down_button: meld1--; break;
        }
        // Ensure meld values are within acceptable ranges,
        // and also disable the down buttons if needed.
        meld0 = Math.max(meld0, MINIMUM_MELD);
        meld1 = Math.max(meld1, MINIMUM_MELD);
        meld0DownButton.setEnabled(meld0 > MINIMUM_MELD);
        meld1DownButton.setEnabled(meld1 > MINIMUM_MELD);
        // Set the meld indicators appropriately.
        meld0Text.setText(meld0 + "");
        meld1Text.setText(meld1 + "");
    }
    
    /**
     * Executes an action based on menu selection.
     * 
     * @param item The clicked menu item that called this method
     */
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.ok_action:
                // Save meld data to the game object before proceeding.
                int meld0 = Integer.parseInt(meld0Text.getText().toString());
                int meld1 = Integer.parseInt(meld1Text.getText().toString());
                game.setMeld(meld0, meld1);
                // It's possible that the outcome of the hand is decided after melding,
                // and if so jump right to the next bid. Otherwise go to the points round.
                Class<?> activityClass = game.isPointsPhase() ? PointsActivity.class : BidActivity.class;
                ScoresheetIntent intent = new ScoresheetIntent(this, activityClass, game);
                startActivity(intent);
                break;
        }
    }
    
}
