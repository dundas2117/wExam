package model;

public class AIIntent{
    private String name;
    private float confidence;

    public AIIntent(){

    }

    public String getName(){
        return this.name;
    }

    public float getConfidence(){
        return this.confidence;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setConfidence(float c){
        this.confidence = c;
    }

}