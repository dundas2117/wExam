package model;

public class TagModel {
    private String webTitle;
    private String id;

    public TagModel(){

    }
    public TagModel(String webTitle, String id){
        this.webTitle = new String(webTitle);
        this.id = new String(id);
    }

    public String getWebTitle(){
        return this.webTitle;
    }

    public String getId(){
        return this.id;
    }

    public void setWebTitle(String title){
        this.webTitle = title;
    }

    public void setId(String id){
        this.id = id;
    }

}