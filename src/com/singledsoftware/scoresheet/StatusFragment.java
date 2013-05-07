package com.singledsoftware.scoresheet;

import org.json.JSONArray;
import org.json.JSONException;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    
    public void updateScore(String gameId) {
        ParseQuery query = new ParseQuery("Game");
        query.getInBackground(gameId, new GetCallback() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    JSONArray players = object.getJSONArray("players");
                    try {
                        for (int p = 0; p < 4; p++) {
                            playerName[p].setText((String)players.get(p));
                        }
                    }
                    catch (JSONException j) {
                        // TODO: deal with no game data fetched
                    }
                }
                else {
                    // TODO: deal with no game data fetched
                }
            }
        });      
    }
    
}
