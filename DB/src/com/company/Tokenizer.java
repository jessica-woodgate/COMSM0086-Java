package com.company;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Tokenizer {

    private String command;
    private ArrayList<String> tokenizedCommand;

    public Tokenizer(String command){
        this.command = command;
        tokenizedCommand = tokenizeCommand(command);
    }

    public String nextToken(int nextIndex){
        return tokenizedCommand.get(nextIndex);
    }

    public ArrayList<String> getTokenizedCommand() {
        return tokenizedCommand;
    }

    private ArrayList<String> tokenizeCommand(String command){
        ArrayList<String> tokenizedCommand = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(command, " \t\n\r\f;");
        while(tokenizer.hasMoreElements()){
            tokenizedCommand.add(tokenizer.nextToken());
        }
        return tokenizedCommand;
    }

}
