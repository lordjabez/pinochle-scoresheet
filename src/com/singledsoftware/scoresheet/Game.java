// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import java.io.Serializable;

/**
 * Stores all game data.
 *
 * @author Judson D Neer
 * @see java.io.Serializable
 */
public class Game implements Serializable {

    // Required for object to be serializable.
    private static final long serialVersionUID = -5278104013850773427L;

    /**
     * Stores data for a particular hand of a game.
     *
     * @author Judson D Neer
     * @see java.io.Serializable
     */
    private class Hand implements Serializable {

        // Required for object to be serializable.
        private static final long serialVersionUID = -5719893226770609068L;

        // Bid value
        private final int bid;

        // Index of bid winner (0-4)
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
         * @return Player index for the bidder (0-4 for players around the table, starting to the left of scorekeeper)
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
            switch(trump) {
                case 0: return bid + " S";
                case 1: return bid + " D";
                case 2: return bid + " C";
                case 3: return bid + " H";
                default: return "";
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

    }

    // A game contains four players, whose names are stored here.
    private final String[] players = {"", "", "", ""};

    // A game also contains four hands, which are indexed 0 to 3.
    private final Hand[] hands = {null, null, null, null};
    private int hand = 0;

    // A game has four phases as stored below.
    private enum Phases {BID, MELD, POINTS, FINISHED};
    private Phases phase = Phases.BID;

    /**
     * @param p Player index (0-4 for players around the table, starting to the left of scorekeeper)
     * @return True if the player is the dealer
     */
    public boolean isDealer(int p) {
        return p == hand;
    }

    /**
     * @param p Player index (0-4 for players around the table, starting to the left of scorekeeper)
     * @return The name of the player
     */
    public String getPlayer(int p) {
        return players[p];
    }

    /**
     * @param p Player index (0-4 for players around the table, starting to the left of scorekeeper)
     * @param player New name for the player
     */
    public void setPlayer(int p, String player) {
        players[p] = player;
    }

    /**
     * @param h Hand index (0-3)
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return A team's score for a given hand
     */
    public String getScore(int h, int t) {
        return hands[h] != null ? hands[h].getScore(t) + "" : "";
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return A team's current total score
     */
    public int getTotal(int t) {
        int total = 0;
        // To find the total we simply sum up each played (i.e. non-null) hand.
        for (int h = 0; h < 4; h++) {
            total += hands[h] != null ? hands[h].getScore(t) : 0;
        }
        return total;
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return A team's current total score as a string
     */
    public String getTotalStr(int t) {
        return getTotal(t) + "";
    }

    /**
     * @param h Hand index (0-3)
     * @return A string representation of the bid and bidder for the given hand
     */
    public String getBid(int h) {
        // Check if the hand has been played
        if (hands[h] != null) {
            // Get the player name, and abbreviate it to 3 characters if necessary.
            String bidder = players[hands[h].getBidder()];
            if (bidder.length() > 3) {
                bidder = bidder.substring(0, 3);
            }
            // The final result is the bid value and suit plus the bidder name.
            return hands[h].getBidStr() + " " + bidder;
        }
        // If it hasn't been played, return an empty string.
        else {
            return "";
        }
    }

    /**
     * Sets the bid values for the current hand.
     *
     * @param bid Bid value
     * @param bidder Player index for bidder (0-4 for players around the table, starting to the left of scorekeeper)
     * @param trump Trump index (0 = spades, 1 = diamonds, 2 = clubs, 3 = hearts)
     */
    public void setBid(int bid, int bidder, int trump) {
        hands[hand] = new Hand(bid, bidder, trump);
        // Now that the bid is set we move on to the meld phase.
        phase = Phases.MELD;
    }

    /**
     * Sets the meld values for the current hand.
     *
     * @param meld0 Meld value for team 0
     * @param meld1 Meld value for team 1
     */
    public void setMeld(int meld0, int meld1) {
        hands[hand].meld = new int[]{meld0, meld1};
        // We check to see if the hand can be won given the meld values
        // before deciding which phase to move to next. To be possible
        // the bidding team must be within 25 points of their bid value.
        if ((bidderOnTeam(0) && hands[hand].bid - meld0 > 25) ||
            (bidderOnTeam(1) && hands[hand].bid - meld1 > 25)) {
            // If they can't make it, both teams score zero points.
            setPoints(0, 0);
        }
        // Otherwise we go to the points phase.
        else {
            phase = Phases.POINTS;
        }
    }

    /**
     * Set the point values for the current hand.
     *
     * @param points0 Points value for team 0
     * @param points1 Points value for team 1
     */
    public void setPoints(int points0, int points1) {
        hands[hand].points = new int[]{points0, points1};
        // If we're not on the last hand, increment our hand
        // counter and go to the bid phase for the next hand.
        if (hand < 3) {
            hand++;
            phase = Phases.BID;
        }
        // Otherwise the game is over.
        else {
            phase = Phases.FINISHED;
        }
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return True if the bidder is on the given team
     */
    public boolean bidderOnTeam(int t) {
        return hands[hand] != null ? hands[hand].bidderOnTeam(t) : false;
    }

    /**
     * @return True if the game is in a bid phase
     */
    public boolean isBidPhase() {
        return phase == Phases.BID;
    }

    /**
     * @return True if the game is in a meld phase
     */
    public boolean isMeldPhase() {
        return phase == Phases.MELD;
    }

    /**
     * @return True if the game is in a points phase
     */
    public boolean isPointsPhase() {
        return phase == Phases.POINTS;
    }

    /**
     * @return True if the game is completed
     */
    public boolean isGameFinished() {
        return phase == Phases.FINISHED;
    }

    /**
     * @return The index of the winning team, or -1 in the case of a tie
     */
    public int getWinningTeam() {
        int total0 = getTotal(0);
        int total1 = getTotal(1);
        if (total0 > total1) return 0;
        if (total1 > total0) return 1;
        return -1;
    }

}
