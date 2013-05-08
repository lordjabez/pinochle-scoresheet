package com.singledsoftware.scoresheet;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "S65lKnE2tCJp2SLMVyVyUgLwopj0KSAt8afp9CaW", "H5gzjMnsUGxhxbCOeCu3R6GhX6Fjr5OPamkdkLpS");
        ParseAnalytics.trackAppOpened(getIntent());
    }

    public void startGame(View button) {
        ParseObject game = new ParseObject("Game");
        try {
            game.save();
        }
        catch (ParseException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to make new game", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Intent intent = new Intent(this, PlayersActivity.class);
        intent.putExtra("gameId", game.getObjectId());
        startActivity(intent);
    }
    
    public void resumeGame(View button) {
        Toast toast = Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT);
        toast.show();
    }
    
}
