package com.singledsoftware.scoresheet;

import java.io.Serializable;

public class Game implements Serializable {

    private static final long serialVersionUID = -8932615431224281147L;
    
    private String[] players = new String[4];
    
    public Game() {
        players[0] = "Malcolm";
        players[1] = "Zoe";
        players[2] = "Jayne";
        players[3] = "Kaylee";
    }
    
    public String getPlayer(int p) {
        return players[p];
    }
    
    public void setPlayer(int p, String player) {
        players[p] = player;
    }
    
}
