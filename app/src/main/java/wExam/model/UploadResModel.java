package model;

import model.UploadResultModel;

public class UploadResModel{

    private UploadResultModel data;

    private String success;

    public UploadResModel(){

    }

    public UploadResultModel getData(){
        return data;
    }

    public void setData(UploadResultModel data){
        this.data = data;
    }

    public String getSuccess(){
        return this.success;
    }

    public void setSuccess(String s){
        this.success = s;
    }
}