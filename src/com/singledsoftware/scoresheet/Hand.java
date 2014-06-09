// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import java.io.Serializable;

/**
 * Stores data for a particular hand of a game.
 * 
 * @author Judson D Neer
 * @see java.io.Serializable
 */
class Hand implements Serializable {

    // Required for object to be serializable.
    private static final long serialVersionUID = -5719893226770609068L;

    // Bid value
    private final int bid;

    // Index of bid winner (0-3 for players around the table, starting to the left of scorekeeper)
    private final int bidder;

    // Index of trump suit (0 = spades, 1 = diamonds, 2 = clubs, 3 = hearts)
    private final int trump;

    // Stores meld and point values for both teams, where
    // team 0 is players 0 & 2 and team 1 is players 1 & 3.
    private int[] meld = null;
    private int[] points = null;

    /**
     * Constructor.
     * 
     * @param b New bid value
     * @param d New bidder index
     * @param u New trump index
     */
    public Hand(int b, int d, int u) {
        bid = b;
        bidder = d;
        trump = u;
    }

    /**
     * @return The bid value
     */
    public int getBid() {
        return bid;
    }

    /**
     * @return Player index for the bidder (0-3 for players around the table, starting to the left of scorekeeper)
     */
    public int getBidder() {
        return bidder;
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return True if the bidder is on the given team
     */
    public boolean bidderOnTeam(int t) {
        return (t == 0 && (bidder % 2 == 0)) || (t == 1 && (bidder % 2 == 1));
    }

    /**
     * @return A string representation of the hand's bid value and trump suit
     */
    public String getBidStr() {
        switch (trump) {
            case 0:
                return bid + " S";
            case 1:
                return bid + " D";
            case 2:
                return bid + " C";
            case 3:
                return bid + " H";
            default:
                return "";
        }
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return A team's total score for this hand based on meld, points, and bid.
     */
    public int getScore(int t) {
        // Check to see if we have meld values.
        if (meld != null) {
            // Check to see if we have point values.
            if (points != null) {
                // Sum the meld and points, and if it's equal to or larger than
                // the bid, or if this is the non-bidding team, return that sum.
                int score = meld[t] + points[t];
                if (score >= bid || !bidderOnTeam(t)) {
                    return score;
                }
                // Otherwise the team got set, so they go down by the bid value.
                else {
                    return -bid;
                }
            }
            // If we haven't gotten any points yet, a team's score is their meld value.
            else {
                return meld[t];
            }
        }
        // If we haven't melded yet the team's score is zero.
        else {
            return 0;
        }
    }

    /**
     * @return True if the hand has meld values assigned
     */
    public boolean meldValid() {
        return meld != null;
    }

    /**
     * @return True if the hand has point values assigned
     */
    public boolean pointsValid() {
        return points != null;
    }

    /**
     * Sets new meld values.
     * 
     * @param meld0 Meld for team 0
     * @param meld1 Meld for team 1
     */
    public void setMeld(int meld0, int meld1) {
        meld = new int[] {meld0, meld1};
    }

    /**
     * Sets new point values.
     * 
     * @param points0 Points for team 0
     * @param points1 Points for team 1
     */
    public void setPoints(int points0, int points1) {
        points = new int[] {points0, points1};
    }

}
