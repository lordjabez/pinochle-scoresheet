// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

/**
 * Provides the main activity for the Scoresheet application.
 * 
 * @see ScoresheetActivity
 * @author Judson D Neer
 */
public class MainActivity extends ScoresheetActivity {

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    /**
     * Performs the actions required to start a new game.
     * 
     * @param button The clicked button that called this method
     */
    public void startGame(View button) {
        Intent intent = new ScoresheetIntent(this, PlayersActivity.class, game);
        startActivity(intent);
    }
    
}
