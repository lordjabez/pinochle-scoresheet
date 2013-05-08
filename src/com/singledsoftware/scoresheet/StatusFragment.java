package com.singledsoftware.scoresheet;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.ParseObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatusFragment extends Fragment {
    
    private TextView[] playerName = new TextView[4];
    
    private ParseObject game = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.fragment_status, container, false);
        playerName[0] = (TextView)resultView.findViewById(R.id.player0_name);
        playerName[1] = (TextView)resultView.findViewById(R.id.player1_name);
        playerName[2] = (TextView)resultView.findViewById(R.id.player2_name);
        playerName[3] = (TextView)resultView.findViewById(R.id.player3_name);
        return resultView;
    }
    
    public void setGame(ParseObject game) {
        this.game = game;
    }
    
    public void updateScore() {
        if (game != null) {
            JSONArray players = game.getJSONArray("players");
            if (players != null) {
                try {
                    for (int p = 0; p < 4; p++) {
                        playerName[p].setText((String)players.get(p));
                    }
                }
                catch (JSONException e) {
                    // TODO: deal with no game data fetched
                }
            }
        }
    }
    
}
