package com.singledsoftware.scoresheet;

import java.io.Serializable;

public class Game implements Serializable {
    
    private static final long serialVersionUID = -8932615431224281147L;

    private class Hand {
        
        private char trump;
        private int bid;
        private int[] meld = null;
        private int[] points = null;
        
        public Hand(char u, char b) {
            trump = u;
            bid = b;
        }
        
        public String getBid() {
            return bid + "" + trump;
        }
        
        public String getScoreStr(int t) {
            int score = getScore(t);
            return score >= 0 ? score + "" : "";
        }
        
        public int getScore(int t) {
            if (meld != null) {
                if (points != null) {
                    int score = meld[t] + points[t];
                    if (score >= bid) {
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
                return -1;
            }
        }
        
    }
    
    private String[] players = {"Malcolm", "Zoe", "Jayne", "Kaylee"};
    private Hand[] hands = {null, null, null, null};
    
    public Game() {}
    
    public String getPlayer(int p) {
        return players[p];
    }
    
    public void setPlayer(int p, String player) {
        players[p] = player;
    }
    
    public String getScore(int h, int t) {
        return hands[h] != null ? hands[h].getScoreStr(t) : "";
    }
    
    public String getTotal(int t) {
        int total = 0;
        for (int h = 0; h < 4; h++) {
            total += hands[h] != null ? hands[h].getScore(t) : 0;
        }
        return total + "";
    }
    
    public String getBid(int h) {
        return hands[h] != null ? hands[h].getBid() : "";
    }
    
}
