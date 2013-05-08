package com.singledsoftware.scoresheet;

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
    }

    public void startGame(View button) {
        Game game = new Game();
        Intent intent = new Intent(this, PlayersActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
    
    public void resumeGame(View button) {
        Toast toast = Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_SHORT);
        toast.show();
    }
    
}
