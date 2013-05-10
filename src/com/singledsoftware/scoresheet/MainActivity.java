package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View button) {
        Game game = new Game();
        Intent intent = new Intent(this, PlayersActivity.class);
        intent.putExtra("game", game);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
    
}
