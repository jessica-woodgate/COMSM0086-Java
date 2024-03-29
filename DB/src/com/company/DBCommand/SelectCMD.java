package com.company.DBCommand;

import com.company.DBExceptions.CommandException;
import com.company.DBExceptions.DBException;
import com.company.DBExceptions.EmptyData;
import com.company.DBExceptions.StorageType;

import java.util.ArrayList;

public class SelectCMD extends Parser implements DBCommand{

    private final ArrayList<String> command;
    private ArrayList<String> attributeList;
    private int index;
    private final StorageType type;
    private String tableName;
    private ConditionList conditionListObject;
    private boolean hasCondition;
    private boolean multipleConditions;

    public SelectCMD(ArrayList<String> command, int index) throws DBException{
        this.command = command;
        this.index = index;
        type = StorageType.TABLE;
        attributeList = new ArrayList<>();
        if(command == null) {
            throw new EmptyData("SELECT command");
        }
        if(!parseSelect()) {
            throw new CommandException(
                    command.get(index), index, "table name");
        }
    }

    private boolean parseSelect() throws DBException{
        index++;
        WildAttributeList wildAttributeList = new WildAttributeList(command, index);
        //updating index and storing attributes
        index = wildAttributeList.getIndex();
        attributeList = wildAttributeList.getAttributeList();
        checkNextToken(command.get(index), "from", index);
        tableName = parseTableName(command, index);
        index+=2;
        switch(command.get(index)){
            case(";"):
                hasCondition = false;
                break;
            case("where"):
                hasCondition = true;
                index++;
                //get conditions, store as object and array for testing
                conditionListObject = new ConditionList(command, index);
                index = conditionListObject.getIndex();
                multipleConditions = conditionListObject.isMultipleConditions();
                break;
            default:
                throw new CommandException(
                        command.get(index), index, "; or WHERE");
        }
        //point index to end of command
        index++;
        return true;
    }

    public boolean isMultipleConditions(){
        return multipleConditions;
    }

    public boolean getHasCondition(){
        return hasCondition;
    }

    public StorageType getType(){
        return type;
    }

    public ConditionList getConditionListObject()throws EmptyData {
        if(conditionListObject!=null){
            return conditionListObject;
        }
        throw new EmptyData("condition list in DeleteCMD");
    }

    public ArrayList<String> getAttributeList() throws EmptyData {
        if(attributeList!=null){
            return attributeList;
        }
        throw new EmptyData("wild attribute list");
    }

    public String getTableName() throws EmptyData {
        if(tableName!=null) {
            return tableName;
        }
        throw new EmptyData("table name");
    }

    public int getIndex(){
        return index;
    }
}