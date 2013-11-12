// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import java.io.Serializable;

/**
 * Stores all game data.
 *
 * @author Judson D Neer
 * @see java.io.Serializable
 */
class Game implements Serializable {

    // Required for object to be serializable.
    private static final long serialVersionUID = -5278104013850773427L;

    // A game contains four players, whose names are stored here.
    private final String[] players = {"", "", "", ""};

    // A game also contains four hands, which are indexed 0 to 3. We start with an
    // index of -1 so it advances to zero correctly at the start of the first hand.
    private final Hand[] hands = {null, null, null, null};
    private int hand = -1;

    /**
     * @param p Player index (0-4 for players around the table, starting to the left of scorekeeper)
     * @return True if the player is the dealer
     */
    public boolean isDealer(int p) {
        return hand == p;
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
            // The final result is the bid value and suit plus the bidder name.
            return hands[h].getBidStr() + "\n" + players[hands[h].getBidder()];
        }
        // If it hasn't been played, return an empty string.
        else {
            return "";
        }
    }

    /**
     * Advance the game to the next hand.
     */
    public void nextHand() {
        if (hand < 3) {
            hand++;
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
    }

    /**
     * Sets the meld values for the current hand.
     *
     * @param meld0 Meld value for team 0
     * @param meld1 Meld value for team 1
     */
    public void setMeld(int meld0, int meld1) {
        hands[hand].setMeld(meld0, meld1);
        // We check to see if the hand can be won given the meld values. To be
        // possible the bidding team must be within 25 points of their bid value.
        boolean team0Set = bidderOnTeam(0) && hands[hand].getBid() - meld0 > 25;
        boolean team1Set = bidderOnTeam(1) && hands[hand].getBid() - meld1 > 25;
        // If they can't make it, both teams score zero points. Setting
        // the points will also magically advance us to the next hand.
        if (team0Set || team1Set) {
            setPoints(0, 0);
        }
    }

    /**
     * Set the point values for the current hand.
     *
     * @param points0 Points value for team 0
     * @param points1 Points value for team 1
     */
    public void setPoints(int points0, int points1) {
        hands[hand].setPoints(points0, points1);
    }

    /**
     * @param t Team index (0 = players 0 & 2, 1 = players 1 & 3)
     * @return True if the bidder is on the given team
     */
    public boolean bidderOnTeam(int t) {
        return hands[hand] != null ? hands[hand].bidderOnTeam(t) : false;
    }

    /**
     * @return True if the game is in a points phase
     */
    public boolean isPointsPhase() {
        // We're ready to score points if we have a valid
        // hand with valid meld, but not yet any points.
        return hands[hand] != null && hands[hand].meldValid() && !hands[hand].pointsValid();
    }

    /**
     * @return True if the game is completed
     */
    public boolean isGameFinished() {
        // The game is over when it's the last (i.e. index 3)
        // hand, and points have been scored for that hand.
        return hand == 3 && hands[hand] != null && hands[hand].pointsValid();
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
