// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.View;

/**
 * Provides the main activity for the Scoresheet application.
 *
 * @see ScoresheetActivity
 * @author Judson D Neer
 */
public class MainActivity extends ScoresheetActivity {

    /**
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
        startActivity(new ScoresheetIntent(this, PlayersActivity.class, new Game()));
    }

    /**
     * Performs the actions required to resume an existing new game.
     *
     * @param button The clicked button that called this method
     */
    public void resumeGame(View button) {
        if (game.isPlayersPhase()) {
            startActivity(new ScoresheetIntent(this, PlayersActivity.class, game));
        }
        else if (game.isBidPhase()) {
            startActivity(new ScoresheetIntent(this, BidActivity.class, game));
        }
        else if (game.isMeldPhase()) {
            startActivity(new ScoresheetIntent(this, MeldActivity.class, game));
        }
        else if (game.isPointsPhase()) {
            startActivity(new ScoresheetIntent(this, PointsActivity.class, game));
        }
        else if (game.isFinished()) {
            startActivity(new ScoresheetIntent(this, FinishedActivity.class, game));
        }
    }

}
