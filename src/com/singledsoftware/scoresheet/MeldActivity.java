package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MeldActivity extends Activity {

    private static final int MINIMUM_MELD = 0;
    private static final int DEFAULT_MELD_BIDDER = 15;
    private static final int DEFAULT_MELD_NONBIDDER = 7;
    
    private TextView team0Text;
    private Button meld0DownButton;
    private TextView meld0Text;
    
    private TextView team1Text;
    private Button meld1DownButton;
    private TextView meld1Text;
    
    private StatusFragment statusFragment;
        
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meld);
        team0Text = (TextView)this.findViewById(R.id.team0_text);
        meld0DownButton = (Button)this.findViewById(R.id.meld0_down_button);
        meld0Text = (TextView)this.findViewById(R.id.meld0_text);
        team1Text = (TextView)this.findViewById(R.id.team1_text);
        meld1DownButton = (Button)this.findViewById(R.id.meld1_down_button);
        meld1Text = (TextView)this.findViewById(R.id.meld1_text);
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
        team0Text.setText(game.getPlayer(0) + " & " + game.getPlayer(2));
        team1Text.setText(game.getPlayer(1) + " & " + game.getPlayer(3));
        if (game.bidderOnTeam(0)) {
            meld0Text.setText(DEFAULT_MELD_BIDDER + "");
            meld1Text.setText(DEFAULT_MELD_NONBIDDER + "");
        }
        else {
            meld0Text.setText(DEFAULT_MELD_NONBIDDER + "");
            meld1Text.setText(DEFAULT_MELD_BIDDER + "");
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

    public void onClick(View button) {
        int meld0 = Integer.parseInt(meld0Text.getText().toString());
        int meld1 = Integer.parseInt(meld1Text.getText().toString());
        switch (button.getId()) {
            case R.id.meld0_up_button: meld0++; break;
            case R.id.meld0_down_button: meld0--; break;
            case R.id.meld1_up_button: meld1++; break;
            case R.id.meld1_down_button: meld1--; break;
        }
        if (meld0 <= MINIMUM_MELD) {
            meld0 = MINIMUM_MELD;
            meld0DownButton.setEnabled(false);            
        }
        else {
            meld0DownButton.setEnabled(true);
        }
        if (meld1 <= MINIMUM_MELD) {
            meld1 = MINIMUM_MELD;
            meld1DownButton.setEnabled(false);            
        }
        else {
            meld1DownButton.setEnabled(true);
        }
        meld0Text.setText(meld0 + "");
        meld1Text.setText(meld1 + "");
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.ok_action:
                int meld0 = Integer.parseInt(meld0Text.getText().toString());
                int meld1 = Integer.parseInt(meld1Text.getText().toString());
                game.setMeld(meld0, meld1);
                /*Intent intent = new Intent(this, PointsActivity.class);
                intent.putExtra("game", game);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);*/
                break;
        }
    }

}
