package com.company.Element;

import java.util.ArrayList;

public class Player implements Element {

    private final String playerName;
    private final ArrayList<Subject> inventory;
    private Location location;
    private int health;

    public Player(String playerName){
        this.playerName = playerName;
        health = 3;
        inventory = new ArrayList<>();
    }

    public void changeHealth(boolean minusOrPlus){
        if(minusOrPlus){
            health++;
        }else{
            health--;
        }
    }

    public void resetHealth(){
        health = 3;
    }

    public int getHealth(){
        return health;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public ArrayList<Subject> getInventory(){
        return inventory;
    }

    public String getName() {
        return playerName;
    }

    public String getDescription() {
        return null;
    }
}
