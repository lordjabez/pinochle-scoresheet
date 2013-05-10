package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BidActivity extends Activity {

    private static final int MINIMUM_BID = 15;
    private static final int DEFAULT_BID = 33;
    
    private Button bidDownButton;
    private TextView bidText;
    private RadioGroup bidderGroup;
    private RadioGroup trumpGroup;
    
    private StatusFragment statusFragment;
        
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);
        bidDownButton = (Button)this.findViewById(R.id.bid_down_button);
        bidText = (TextView)this.findViewById(R.id.bid_text);
        bidderGroup = (RadioGroup)this.findViewById(R.id.bidder_radiogroup);
        trumpGroup = (RadioGroup)this.findViewById(R.id.trump_radiogroup);
        FragmentManager fragments = getFragmentManager();
        statusFragment = (StatusFragment)fragments.findFragmentById(R.id.status_fragment);
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
        bidText.setText(DEFAULT_BID + "");
        for (int p = 0; p < 4; p++) {
            RadioButton bidderRadio = (RadioButton)bidderGroup.getChildAt(p);
            bidderRadio.setText(game.getPlayer(p));
        }
        statusFragment.update(game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_actionbar, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable("game", game);
    }

    private int getCheckedIndex(RadioGroup group) {
        RadioButton radio = (RadioButton)group.findViewById(group.getCheckedRadioButtonId());
        return group.indexOfChild(radio);
    }

    public void onClick(View button) {
        int bid = Integer.parseInt(bidText.getText().toString());
        switch (button.getId()) {
            case R.id.bid_up_button: bid++; break;
            case R.id.bid_down_button: bid--; break;
        }
        if (bid <= MINIMUM_BID) {
            bid = MINIMUM_BID;
            bidDownButton.setEnabled(false);            
        }
        else {
            bidDownButton.setEnabled(true);
        }
        bidText.setText(bid + "");
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.ok_action:
                int bid = Integer.parseInt(bidText.getText().toString());
                int bidder = getCheckedIndex(bidderGroup);
                int trump = getCheckedIndex(trumpGroup);
                game.setBid(bid, bidder, trump);
                Intent intent = new Intent(this, MeldActivity.class);
                intent.putExtra("game", game);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

}
