package com.singledsoftware.scoresheet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BidFragment extends Fragment implements OnClickListener {

    private static final int MINIMUM_BID = 15;
    private static final int DEFAULT_BID = 33;
    
    private Button bidUpButton;
    private Button bidDownButton;
    private TextView bidText;
    private RadioGroup bidderGroup;
    private RadioGroup trumpGroup;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View resultView = inflater.inflate(R.layout.fragment_bid, container, false);
        bidUpButton = (Button)resultView.findViewById(R.id.bid_up_button);
        bidDownButton = (Button)resultView.findViewById(R.id.bid_down_button);
        bidText = (TextView)resultView.findViewById(R.id.bid_text);
        bidderGroup = (RadioGroup)resultView.findViewById(R.id.bidder_radiogroup);
        trumpGroup = (RadioGroup)resultView.findViewById(R.id.trump_radiogroup);
        bidUpButton.setOnClickListener(this);
        bidDownButton.setOnClickListener(this);
        return resultView;
    }
    
    private int getCheckedIndex(RadioGroup group) {
        RadioButton radio = (RadioButton)group.findViewById(group.getCheckedRadioButtonId());
        return group.indexOfChild(radio);
    }
    
    public void update(Game game) {
        bidText.setText(DEFAULT_BID + "");
        for (int p = 0; p < 4; p++) {
            RadioButton bidderRadio = (RadioButton)bidderGroup.getChildAt(p);
            bidderRadio.setText(game.getPlayer(p));
        }
    }
    
    public void setBid(Game game) {
        int bid = Integer.parseInt(bidText.getText().toString());
        int bidder = getCheckedIndex(bidderGroup);
        int trump = getCheckedIndex(trumpGroup);
        game.setBid(bid, bidder, trump);
    }

    @Override
    public void onClick(View button) {
        int bid = Integer.parseInt(bidText.getText().toString());
        switch (button.getId()) {
            case R.id.bid_up_button: bid++; break;
            case R.id.bid_down_button: bid--; break;
        }
        if (bid <= MINIMUM_BID) {
            bid = MINIMUM_BID;
            bidDownButton.setEnabled(false);            
        }
        else {
            bidDownButton.setEnabled(true);
        }
        bidText.setText(bid + "");
    }
    
}
