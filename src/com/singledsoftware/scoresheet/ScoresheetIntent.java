// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.content.Context;
import android.content.Intent;

/**
 * Custom intent that provides some basic functionality such as
 * passing the game data object and disabling transition animations.
 * 
 * @author Judson D Neer
 * @see android.app.Intent
 */
public class ScoresheetIntent extends Intent {

    /**
     * Custom constructor for storing the game object.
     * 
     * @param packageContext {@inheritDoc}
     * @param cls {@inheritDoc}
     * @param game The game object to be passed to the next activity.
     */
    public ScoresheetIntent(Context packageContext, Class<?> cls, Game game) {
        super(packageContext, cls);
        // Prevent transition animation when starting the new activity.
        this.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        // Store the game object for the new activity.
        this.putExtra("game", game);
    }

}
