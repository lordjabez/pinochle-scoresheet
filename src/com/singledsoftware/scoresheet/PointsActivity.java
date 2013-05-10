package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PointsActivity extends Activity {

    private static final int MINIMUM_POINTS = 0;
    private static final int MAXIMUM_POINTS = 25;
    private static final int DEFAULT_POINTS_BIDDER = 17;
    private static final int DEFAULT_POINTS_NONBIDDER = 8;
    
    private TextView team0Text;
    private Button points0UpButton;
    private Button points0DownButton;
    private TextView points0Text;
    
    private TextView team1Text;
    private Button points1UpButton;
    private Button points1DownButton;
    private TextView points1Text;
    
    private StatusFragment statusFragment;
        
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_points);
        team0Text = (TextView)this.findViewById(R.id.team0_text);
        points0UpButton = (Button)this.findViewById(R.id.points0_up_button);
        points0DownButton = (Button)this.findViewById(R.id.points0_down_button);
        points0Text = (TextView)this.findViewById(R.id.points0_text);
        team1Text = (TextView)this.findViewById(R.id.team1_text);
        points1UpButton = (Button)this.findViewById(R.id.points1_up_button);
        points1DownButton = (Button)this.findViewById(R.id.points1_down_button);
        points1Text = (TextView)this.findViewById(R.id.points1_text);
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
            points0Text.setText(DEFAULT_POINTS_BIDDER + "");
            points1Text.setText(DEFAULT_POINTS_NONBIDDER + "");
        }
        else {
            points0Text.setText(DEFAULT_POINTS_NONBIDDER + "");
            points1Text.setText(DEFAULT_POINTS_BIDDER + "");
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
        int points0 = Integer.parseInt(points0Text.getText().toString());
        int points1 = Integer.parseInt(points1Text.getText().toString());
        switch (button.getId()) {
            case R.id.points0_up_button: points0++; points1--; break;
            case R.id.points0_down_button: points0--; points1++; break;
            case R.id.points1_up_button: points1++; points0--; break;
            case R.id.points1_down_button: points1--; points0++; break;
        }
        if (points0 <= MINIMUM_POINTS) {
            points0 = MINIMUM_POINTS;
            points0DownButton.setEnabled(false);            
        }
        else {
            points0DownButton.setEnabled(true);
        }
        if (points0 >= MAXIMUM_POINTS) {
            points0 = MAXIMUM_POINTS;
            points0UpButton.setEnabled(false);            
        }
        else {
            points0UpButton.setEnabled(true);
        }
        if (points1 <= MINIMUM_POINTS) {
            points1 = MINIMUM_POINTS;
            points1DownButton.setEnabled(false);            
        }
        else {
            points1DownButton.setEnabled(true);
        }
        if (points1 >= MAXIMUM_POINTS) {
            points1 = MAXIMUM_POINTS;
            points1UpButton.setEnabled(false);            
        }
        else {
            points1UpButton.setEnabled(true);
        }
        points0Text.setText(points0 + "");
        points1Text.setText(points1 + "");
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.ok_action:
                int points0 = Integer.parseInt(points0Text.getText().toString());
                int points1 = Integer.parseInt(points1Text.getText().toString());
                game.setPoints(points0, points1);
                Intent intent = game.isGameFinished() ? new Intent(this, FinishedActivity.class) : new Intent(this, BidActivity.class);
                intent.putExtra("game", game);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

}
