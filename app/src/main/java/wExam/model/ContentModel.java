package model;

public class ContentModel {
    private String webTitle;
    private String webPublicationDate;
    private String id;
    private String webUrl;

    public ContentModel(){

    }
    

    public String getWebTitle(){
        return this.webTitle;
    }

    public String getId(){
        return this.id;
    }

    public String getWebPublicationDate(){
        return this.webPublicationDate;
    }

    public String getWebUrl(){
        return this.webUrl;
    }


    public void setWebTitle(String title){
        this.webTitle = title;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setWebUrl(String webUrl){
        this.webUrl = webUrl;
    }

    public void setWebPublicationDate(String webPublicationDate){
        this.webPublicationDate = webPublicationDate;
    }
}