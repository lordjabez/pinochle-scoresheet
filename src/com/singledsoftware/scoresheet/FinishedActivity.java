package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class FinishedActivity extends Activity {

    private TextView winningTeamText;
    private TextView defeatedText;
    private TextView losingTeamText;
    private TextView scoreText;
    private TextView finalScoreText;
    
    private Game game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_finished);
        winningTeamText = (TextView)this.findViewById(R.id.winningTeam_text);
        defeatedText = (TextView)this.findViewById(R.id.defeated_text);
        losingTeamText = (TextView)this.findViewById(R.id.losingTeam_text);
        scoreText = (TextView)this.findViewById(R.id.score_text);
        finalScoreText = (TextView)this.findViewById(R.id.finalscore_text);
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
        String team0 = game.getPlayer(0) + " & " + game.getPlayer(2);
        String team1 = game.getPlayer(1) + " & " + game.getPlayer(3);
        int total0 = game.getTotal(0);
        int total1 = game.getTotal(1);
        String score = Math.max(total0, total1) + " to " + Math.min(total0, total1);
        finalScoreText.setText(score);
        int winningTeam = game.getWinningTeam();
        if (winningTeam >= 0) {
            String winners = winningTeam == 0 ? team0 : team1;
            String losers = winningTeam == 0 ? team1 : team0;
            winningTeamText.setText(winners);
            losingTeamText.setText(losers);
            defeatedText.setText("defeated");
            scoreText.setText("by a score of");
        }
        else {
            winningTeamText.setText(team0);
            losingTeamText.setText(team1);
            defeatedText.setText("tied");
            scoreText.setText("with a score of");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_actionbar, menu); // TODO: change to custom actionbar
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putSerializable("game", game);
    }
    
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.ok_action:
                // TODO: add ability to restart game with same players
                // (put reset scores call in Game by nulling out everything and going to bid)s
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("game", game);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
    
}
