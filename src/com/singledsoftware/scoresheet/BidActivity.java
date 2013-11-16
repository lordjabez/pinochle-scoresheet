// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Provides interface for user to enter the bid for a hand.
 *
 * @author Judson D Neer
 * @see ScoresheetActivity
 */
public class BidActivity extends ScoresheetActivity {

    // The minimum bid is a rule. The default bid was chosen
    // as an anecdotally typical bid in the Neer household.
    private static final int MINIMUM_BID = 15;
    private static final int DEFAULT_BID = 32;

    // References to various view widgets.
    private Button bidDownButton;
    private TextView bidText;
    private RadioGroup bidderGroup;
    private RadioGroup trumpGroup;
    private StatusFragment statusFragment;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);
        // Grab references to various view widgets.
        bidDownButton = (Button)this.findViewById(R.id.bid_down_button);
        bidText = (TextView)this.findViewById(R.id.bid_text);
        bidderGroup = (RadioGroup)this.findViewById(R.id.bidder_radiogroup);
        trumpGroup = (RadioGroup)this.findViewById(R.id.trump_radiogroup);
        statusFragment = (StatusFragment)getFragmentManager().findFragmentById(R.id.status_fragment);
        // Advance to the next hand.
        game.nextHand();
        // Set the activity tile with the proper hand number.
        int hand = game.getHand();
        String title = this.getString(R.string.title_activity_bid, hand);
        this.setTitle(title);
        // Set the player names for the selection radio buttons.
        for (int p = 0; p < 4; p++) {
            RadioButton bidderRadio = (RadioButton)bidderGroup.getChildAt(p);
            bidderRadio.setText(game.getPlayer(p));
        }
        // Populate the bid text item with default info.
        setBid(DEFAULT_BID);
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
     * Convenience method that gets the index value for
     * the currently selected item in a radio button group.
     *
     * @param group The relevant radio button group
     * @return The index value for the selected item
     */
    private int getCheckedIndex(RadioGroup group) {
        RadioButton radio = (RadioButton)group.findViewById(group.getCheckedRadioButtonId());
        return group.indexOfChild(radio);
    }

    /**
     * Helper function to set the new bid value, making sure it is within
     * range and also updating the adjust buttons' enable/disable statuses.
     *
     * @param bid New bid value
     */
    private void setBid(int bid) {
        // Ensure bid values is within acceptable range,
        // and also disable the down button if needed.
        bid = Math.max(bid, MINIMUM_BID);
        bidDownButton.setEnabled(bid > MINIMUM_BID);
        // Set the bid indicator appropriately.
        bidText.setText(bid + "");
    }

    /**
     * Adjusts the bid value up or down.
     *
     * @param button The clicked button that called this method
     */
    public void adjustBid(View button) {
        // Grab the bid value and adjust accordingly.
        int bid = Integer.parseInt(bidText.getText().toString());
        switch (button.getId()) {
            case R.id.bid_up_button:   bid++; break;
            case R.id.bid_down_button: bid--; break;
        }
        // Finally set the new bid value.
        setBid(bid);
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
                // Save the bid data to the game object before proceeding.
                int bid = Integer.parseInt(bidText.getText().toString());
                int bidder = getCheckedIndex(bidderGroup);
                int trump = getCheckedIndex(trumpGroup);
                game.setBid(bid, bidder, trump);
                startActivity(new ScoresheetIntent(this, MeldActivity.class, game));
                break;
        }
    }

}
