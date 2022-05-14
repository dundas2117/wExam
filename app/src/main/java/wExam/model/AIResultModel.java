package model;

import java.util.List;

import model.AIEntities;

public class AIResultModel{
    private List<AIIntent> intents;
    private AIEntities entities;

    public List<AIIntent> getIntents(){
        return this.intents;
    }

    public AIEntities getEntities(){
        return this.entities;
    }

    public void setIntents(List<AIIntent> intents){
        this.intents = intents;
    }

    public void setEntities(AIEntities entities){
        this.entities = entities;
    }

}