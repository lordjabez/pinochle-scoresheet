package com.singledsoftware.scoresheet;

import java.io.Serializable;

public class Game implements Serializable {

    private static final long serialVersionUID = -5278104013850773427L;

    private class Hand implements Serializable {
        
        private static final long serialVersionUID = -5719893226770609068L;
        
        private int bid;
        private int bidder;
        private int trump;
        
        private int[] meld = null;
        private int[] points = null;
        
        public Hand(int b, int d, int u) {
            bid = b;
            bidder = d;
            trump = u;
        }
        
        public int getBidder() {
            return bidder;
        }
        
        public boolean bidderOnTeam(int t) {
            return (t == 0 && (bidder % 2 == 0)) || (t == 1 && (bidder % 2 == 1));
        }
        
        public String getBidStr() {
            switch(trump) {
                case 0: return bid + " S ";
                case 1: return bid + " D ";
                case 2: return bid + " C ";
                case 3: return bid + " H ";
                default: return "";
            }
        }
        
        public int getScore(int t) {
            if (meld != null) {
                if (points != null) {
                    int score = meld[t] + points[t];
                    if (score >= bid || !bidderOnTeam(t)) {
                        return score;
                    }
                    else {
                        return -bid;
                    }
                }
                else {
                    return meld[t];
                }
            }
            else {
                return 0;
            }
        }
        
    }
    
    private String[] players = {"Malcolm", "Zoe", "Jayne", "Kaylee"};
    private Hand[] hands = {null, null, null, null};
    
    private enum Phases {BID, MELD, POINTS, FINISHED};
    private Phases phase = Phases.BID;
    private int hand = 0;
    
    public Game() {}
    
    public boolean isDealer(int p) {
        return p == hand;
    }
    
    public String getPlayer(int p) {
        return players[p];
    }
    
    public void setPlayer(int p, String player) {
        players[p] = player;
    }
    
    public String getScore(int h, int t) {
        return hands[h] != null ? hands[h].getScore(t) + "" : "";
    }
    
    public int getTotal(int t) {
        int total = 0;
        for (int h = 0; h < 4; h++) {
            total += hands[h] != null ? hands[h].getScore(t) : 0;
        }
        return total;
    }
    
    public String getTotalStr(int t) {
        return getTotal(t) + "";
    }
    
    public String getBid(int h) {
        return hands[h] != null ? hands[h].getBidStr() + players[hands[h].getBidder()].substring(0, 3) : "";
    }
    
    public void setBid(int bid, int bidder, int trump) {
        hands[hand] = new Hand(bid, bidder, trump);
        phase = Phases.MELD;
    }
    
    public void setMeld(int meld0, int meld1) {
        hands[hand].meld = new int[]{meld0, meld1};
        phase = Phases.POINTS;
    }
    
    public void setPoints(int points0, int points1) {
        hands[hand].points = new int[]{points0, points1};
        if (hand < 3) {
            hand++;
            phase = Phases.BID;
        }
        else {
            phase = Phases.FINISHED;
        }
    }
    
    public boolean bidderOnTeam(int t) {
        return hands[hand] != null ? hands[hand].bidderOnTeam(t) : false;
    }
    
    public boolean isBidPhase() {
        return phase == Phases.BID;
    }
    
    public boolean isMeldPhase() {
        return phase == Phases.MELD;
    }
    
    public boolean isPointsPhase() {
        return phase == Phases.POINTS; 
    }
    
    public boolean isGameFinished() {
        return phase == Phases.FINISHED;
    }
    
    public int getWinningTeam() {
        int total0 = getTotal(0);
        int total1 = getTotal(1);
        if (total0 > total1) return 0;
        if (total1 > total0) return 1;
        return -1;
    }
    
}
