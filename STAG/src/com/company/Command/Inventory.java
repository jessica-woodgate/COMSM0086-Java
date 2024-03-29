package com.company.Command;

import com.company.Element.Player;
import com.company.Element.Subject;

import java.util.ArrayList;

public class Inventory implements Command{

    public Inventory(){}

    @Override
    public String runCommand(Player currentPlayer) {
        ArrayList<Subject> inventory = currentPlayer.getInventory();
        StringBuilder message = new StringBuilder();
        for(Subject s : inventory){
            message.append(s.getDescription());
            message.append("\n");
        }
        return (message.toString());
    }
}
