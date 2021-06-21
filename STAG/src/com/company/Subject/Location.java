package com.company.Subject;

import java.util.ArrayList;

public class Location implements Subject {
    private String name;
    private String description;
    private final ArrayList<Artefact> artefacts;
    private final ArrayList<Furniture> furniture;
    private final ArrayList<Character> characters;
    private final ArrayList<String> paths;

    public Location(){
        artefacts = new ArrayList<>();
        furniture = new ArrayList<>();
        characters = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setArtefact(String name, String description){
        Artefact artefact = new Artefact(name, description);
        artefacts.add(artefact);
    }

    public ArrayList<Artefact> getArtefacts(){
        return artefacts;
    }

    //visitor? --> similar in Player
    public void removeArtefact(Artefact artefact){
        artefacts.remove(artefact);
    }

    //visitor? --> similar in Player  --> similar for Artefact, Furniture, and Character
    public void removeArtefact(String artefact){
        artefacts.removeIf(
                a -> a.getName().equals(artefact)
                || a.getDescription().equals(artefact));
    }

    public void setFurniture(String name, String description){
        Furniture newFurniture = new Furniture(name, description);
        furniture.add(newFurniture);
    }

    public ArrayList<Furniture> getFurniture(){
        return furniture;
    }

    //visitor? --> similar for Artefact, Furniture, and Character
    public void removeFurniture(String furnitureName){
        furniture.removeIf(
                f -> f.getName().equals(furnitureName)
                || f.getDescription().equals(furnitureName));
    }

    public void setCharacter(String name, String description){
        Character newCharacter = new Character(name, description);
        characters.add(newCharacter);
    }

    public ArrayList<Character> getCharacters(){
        return characters;
    }

    //visitor?  --> similar for Artefact, Furniture, and Character
    public void removeCharacter(String character){
        characters.removeIf(
                c -> c.getName().equals(character)
                        || c.getDescription().equals(character));
    }

    //other locations accessible from the current location
    public void setPath(String target){
        paths.add(target);
    }

    public ArrayList<String> getPaths(){
        return paths;
    }
}