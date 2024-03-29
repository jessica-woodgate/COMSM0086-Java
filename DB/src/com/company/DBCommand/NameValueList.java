package com.company.DBCommand;

import com.company.DBExceptions.CommandException;
import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;

import java.util.ArrayList;

public class NameValueList extends Parser{

    private final ArrayList<String> command;
    private int index;
    private String attributeName;
    private final ArrayList<String> attributeList;
    private final ArrayList<String> valueList;
    private final ArrayList<Value> valueListObject;

    public NameValueList(ArrayList<String> command, int index) throws DBException {
        this.command = command;
        this.index = index;
        valueList = new ArrayList<>();
        attributeList = new ArrayList<>();
        valueListObject = new ArrayList<>();
        try{
            parseNameValueList();
        }catch(DBException e){
            throw new CommandException(command.get(index), index, "name value list", e);
        }
    }

    private void parseNameValueList() throws DBException{
        if(command!=null) {
            String nextToken = command.get(index);
            while (!nextToken.equals(";") && !nextToken.equals("where")) {
                parseNameValuePair();
                nextToken = command.get(index);
                if (nextToken.equals(",")) {
                    index++;
                    //if comma, it's a list so need to call recursively
                    parseNameValueList();
                    return;
                }
            }
        }else{
            throw new EmptyData("command in name value list");
        }
    }

    private void parseNameValuePair() throws DBException{
        attributeName = parseAttributeName(command, index);
        attributeList.add(attributeName);
        index++;
        String nextToken = command.get(index);
        checkNextToken(nextToken, "=", index);
        index++;
        Value value = new Value(command, index);
        valueListObject.add(value);
        String valueString = value.getValue();
        valueList.add(valueString);
        index = value.getIndex()+1;
    }

    public ArrayList<String> getAttributeList() throws EmptyData {
        if(attributeList!=null){
            return attributeList;
        }
        throw new EmptyData("attribute list");
    }

    public ArrayList<String> getValueList() throws EmptyData {
        if(valueList!=null){
            return valueList;
        }
        throw new EmptyData("value list");
    }

    public ArrayList<Value> getValueListObject() throws EmptyData {
        if(valueListObject!=null){
            return valueListObject;
        }
        throw new EmptyData("value list");
    }

    public String getAttributeName() throws EmptyData {
        if(attributeName!=null){
            return attributeName;
        }
        throw new EmptyData("attribute name");
    }

    public int getIndex(){
        return index;
    }
}
