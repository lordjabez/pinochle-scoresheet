// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.app.Activity;
import android.os.Bundle;

/**
 * Base activity from which all other activities inherit. This
 * class provides some basic functionality such as saving/restoring
 * the game data object and disabling transition animations.
 * 
 * @author Judson D Neer
 * @see android.app.Activity
 */
public abstract class ScoresheetActivity extends Activity {
    
    // Stores game data.
    protected Game game = null;

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disable transition animation.
        getWindow().setWindowAnimations(0);
        // Try to get the game object out of the intent. If it isn't
        // there then try pulling it from the saved instance object.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game = (Game)extras.getSerializable("game");
        }
        else if (savedInstanceState != null) {
            game = (Game)savedInstanceState.getSerializable("game");
        }
        // If we've arrived here and game is still null, just make a new one.
        if (game == null) {
            game = new Game();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        // Save our game object to the instance state.
        instanceState.putSerializable("game", game);
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        super.finish();
        // Prevent transition animation when leaving the activity.
        overridePendingTransition(0, 0);
    }
    
}
