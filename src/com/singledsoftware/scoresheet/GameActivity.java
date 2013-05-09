package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends Activity {
    
    private StatusFragment statusFragment;
    private BidFragment bidFragment;
    private MeldFragment meldFragment;
    //private PointsFragment pointsFragment;
    //private FinishedFragment finishedFragment;
        
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FragmentManager fragments = getFragmentManager();
        statusFragment = (StatusFragment)fragments.findFragmentById(R.id.status_fragment);
        bidFragment = (BidFragment)fragments.findFragmentById(R.id.bid_fragment);
        meldFragment = (MeldFragment)fragments.findFragmentById(R.id.meld_fragment);
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
        update();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable("game", game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_actionbar, menu);
        return true;
    }
    
    private void update() {
        statusFragment.update(game);
        bidFragment.update(game);
        meldFragment.update(game);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (game.isBidPhase()) {
            ft.show(bidFragment);
        }
        else {
            ft.hide(bidFragment);
        }
        if (game.isMeldPhase()) {
            ft.show(meldFragment);
        }
        else {
            ft.hide(meldFragment);
        }
        /*if (game.isPointsPhase()) {
            ft.show(pointsFragment);
        }
        else {
            ft.hide(pointsFragment);
        }
        if (game.isGameFinished()) {
            ft.show(finishedFragment);
        }
        else {
            ft.hide(finishedFragment);
        }*/
        ft.commit();
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                // TBD
                break;
            case R.id.ok_action:
                if (!bidFragment.isHidden()) {
                    bidFragment.setBid(game);
                }
                else if (!meldFragment.isHidden()) {
                    meldFragment.setMeld(game);
                }
                /*else if (!pointsFragment.isHidden()) {
                    pointsFragment.setPoints(game);
                }
                else if (!finishedFragment.isHidden()) {
                    // TBD
                }*/
                update();
                break;
        }
    }

}
