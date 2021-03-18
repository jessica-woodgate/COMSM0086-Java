package com.company.DBCommand;

import com.company.DBExceptions.CommandException;
import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;

import java.util.ArrayList;

public class Value {

    private int intLiteral;
    private boolean booleanLiteral;
    private float floatLiteral;
    private String stringLiteral;
    private final ArrayList<String> command;
    private int index;
    private LiteralType type;
    private String value;

    public Value(ArrayList<String> command, int index) throws DBException {
        System.out.println("entered Value with "+command);
        this.command = command;
        this.index = index;
        if(command!=null){
            setLiteralType();
            value = command.get(index);
        }
        else {
            throw new EmptyData("Command");
        }
    }

    public String getValue() throws EmptyData {
        if(value!=null) {
            return value;
        }
        throw new EmptyData("value");
    }

    public LiteralType getLiteralType() {
        return type;
    }

    public boolean getBooleanLiteral(){
        return booleanLiteral;
    }

    public float getFloatLiteral(){
        return floatLiteral;
    }

    public int getIntLiteral(){
        return intLiteral;
    }

    public String getStringLiteral(){
        return stringLiteral;
    }

    private void setLiteralType() throws DBException{
        System.out.println("entered setLiteralType with "+command);
        if(integerLiteral(command.get(index))){
            type = LiteralType.INTEGER;
        }
        else if(floatLiteral(command.get(index))){
            type = LiteralType.FLOAT;
        }
        else if(boolLiteral(command.get(index))){
            type = LiteralType.BOOLEAN;
        }
        else if(stringLiteral(command, index)){
            type = LiteralType.STRING;
        }
        else{
            throw new CommandException(command.get(index), index, "value");
        }
    }

    private boolean stringLiteral(ArrayList<String> tokenizedCommand, int index) throws DBException {
        System.out.println("entered stringLiteral with "+tokenizedCommand +"\nand index "+index);
        String token = tokenizedCommand.get(index);
        if(token.charAt(0)!='\''){
            return false;
        }
        int lastIndex = token.length() - 1;
        if(token.charAt(lastIndex)!= '\''){
            //if the end of that token isn't a single quote, we need to concat the tokens back together
            stringLiteral = concatStringLiteral(tokenizedCommand, index);
            return true;
        }
        stringLiteral = token;
        return true;
    }

    public String concatStringLiteral(ArrayList<String> tokenizedCommand, int index){
        System.out.println("entered getStringLiteral");
        //store the first token of the stringLiteral
        String currentToken = tokenizedCommand.get(index);
        String stringLiteral = currentToken;

        //loop through until we find the end of the stringLiteral
        while(currentToken.charAt(currentToken.length()-1)!='\''){
            index++;
            //get the next token in the list
            currentToken = tokenizedCommand.get(index);
            //concat it to the end of our stringLiteral
            stringLiteral = stringLiteral.concat(currentToken);
        }

        //update the index to be pointing to the end of the stringLiteral
        // in the tokenizedCommand list
        this.index = index;
        System.out.println("end of getStringLiteral with " + stringLiteral);
        System.out.println("tokenizedCommand is " + tokenizedCommand);
        System.out.println("index is " + index);
        return stringLiteral;
    }

    private boolean floatLiteral(String token) throws DBException {
        if (token != null) {
            try {
                //getting the float value from the string
                floatLiteral = Float.parseFloat(token);
                return true;
            } catch (NumberFormatException e) {
                //if we can't get a float from the string, return false
                return false;
            }
        }
        else {
            throw new EmptyData("Command in floatLiteral");
        }
    }

    private boolean integerLiteral(String token) throws DBException {
        if(token!=null){
            try{
                //getting the integer value from the string
                intLiteral = Integer.parseInt(token);
                return true;
            } catch (NumberFormatException e) {
                //if we can't get an integer from the string, return false
                return false;
            }
        }
        else {
            throw new EmptyData("Command in integerLiteral");
        }
    }

    private boolean boolLiteral(String token) throws DBException{
        if(token!=null){
            if(token.equals("true")){
                //if the string is true, setting the boolean variable to true
                booleanLiteral = true;
                //returning true because the token is a boolean value
                return true;
            }
            else if(token.equals("false")){
                //if the string is true, setting the boolean variable to false
                booleanLiteral = false;
                //returning true because the token is a boolean value
                return true;
            }
            else{
                //otherwise return false because it is not a boolean value
                return false;
            }
        }
        else {
            //catching null pointers
            throw new EmptyData("Command in boolLiteral");
        }
    }

}