package com.singledsoftware.scoresheet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatusFragment extends Fragment {
    
    private TextView[] playerName = new TextView[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.fragment_status, container, false);
        playerName[0] = (TextView)resultView.findViewById(R.id.player0_name);
        playerName[1] = (TextView)resultView.findViewById(R.id.player1_name);
        playerName[2] = (TextView)resultView.findViewById(R.id.player2_name);
        playerName[3] = (TextView)resultView.findViewById(R.id.player3_name);
        return resultView;
    }
    
    public void update(Game game) {
        if (game != null) {
            for (int p = 0; p < 4; p++) {
                playerName[p].setText(game.getPlayer(p));
            }
        }
    }
    
}
