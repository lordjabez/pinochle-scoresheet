package com.singledsoftware.scoresheet;

import com.parse.Parse;
import com.parse.ParseAnalytics;

import android.os.Bundle;
import android.view.View;
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
        Intent intent = new Intent(this, PlayersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    
}
