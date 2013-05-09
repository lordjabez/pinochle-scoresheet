package com.singledsoftware.scoresheet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MeldFragment extends Fragment implements OnClickListener {

    private static final int MINIMUM_MELD = 0;
    private static final int DEFAULT_MELD_BIDDER = 15;
    private static final int DEFAULT_MELD_NONBIDDER = 7;
    
    private TextView team0Text;
    private Button meld0UpButton;
    private Button meld0DownButton;
    private TextView meld0Text;
    
    private TextView team1Text;
    private Button meld1UpButton;
    private Button meld1DownButton;
    private TextView meld1Text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.fragment_meld, container, false);
        team0Text = (TextView)resultView.findViewById(R.id.team0_text);
        meld0UpButton = (Button)resultView.findViewById(R.id.meld0_up_button);
        meld0DownButton = (Button)resultView.findViewById(R.id.meld0_down_button);
        meld0Text = (TextView)resultView.findViewById(R.id.meld0_text);
        team1Text = (TextView)resultView.findViewById(R.id.team1_text);
        meld1UpButton = (Button)resultView.findViewById(R.id.meld1_up_button);
        meld1DownButton = (Button)resultView.findViewById(R.id.meld1_down_button);
        meld1Text = (TextView)resultView.findViewById(R.id.meld1_text);
        meld0UpButton.setOnClickListener(this);
        meld0DownButton.setOnClickListener(this);
        meld1UpButton.setOnClickListener(this);
        meld1DownButton.setOnClickListener(this);
        return resultView;
    }
    
    public void update(Game game) {
        team0Text.setText(game.getPlayer(0) + " & " + game.getPlayer(2));
        team1Text.setText(game.getPlayer(1) + " & " + game.getPlayer(3));
        if (game.bidderOnTeam(0)) {
            meld0Text.setText(DEFAULT_MELD_BIDDER + "");
            meld1Text.setText(DEFAULT_MELD_NONBIDDER + "");
        }
        else {
            meld0Text.setText(DEFAULT_MELD_NONBIDDER + "");
            meld1Text.setText(DEFAULT_MELD_BIDDER + "");
        }
    }
    
    public void setMeld(Game game) {
        int meld0 = Integer.parseInt(meld0Text.getText().toString());
        int meld1 = Integer.parseInt(meld1Text.getText().toString());
        game.setMeld(meld0, meld1);
    }

    @Override
    public void onClick(View button) {
        int meld0 = Integer.parseInt(meld0Text.getText().toString());
        int meld1 = Integer.parseInt(meld1Text.getText().toString());
        switch (button.getId()) {
            case R.id.meld0_up_button: meld0++; break;
            case R.id.meld0_down_button: meld0--; break;
            case R.id.meld1_up_button: meld1++; break;
            case R.id.meld1_down_button: meld1--; break;
        }
        if (meld0 <= MINIMUM_MELD) {
            meld0 = MINIMUM_MELD;
            meld0DownButton.setEnabled(false);            
        }
        else {
            meld0DownButton.setEnabled(true);
        }
        if (meld1 <= MINIMUM_MELD) {
            meld1 = MINIMUM_MELD;
            meld1DownButton.setEnabled(false);            
        }
        else {
            meld1DownButton.setEnabled(true);
        }
        meld0Text.setText(meld0 + "");
        meld1Text.setText(meld1 + "");
    }
    
}
