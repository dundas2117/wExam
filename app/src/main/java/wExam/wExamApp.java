package wExam;

import java.util.List;

import core.ModelFactory;
import core.ViewHandler;
import core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.CacheRepo;


public class wExamApp extends Application {
    private boolean isSearchDummy = false;
    private boolean isUploadDummy = false;

    @Override
    public void start(Stage stage) throws Exception {
        Parameters params = getParameters();
        List<String> p = params.getRaw();
        System.out.print(p.size());

        if (p.size() >0){
            this.isSearchDummy = p.get(0).equalsIgnoreCase("offline") ;
        }
        if ( p.size() > 1){
            this.isUploadDummy = p.get(1).equalsIgnoreCase("offline") ;
           
        }

        CacheRepo.createCacheDB();
        ModelFactory mf = new ModelFactory();
        ViewModelFactory vmf = new ViewModelFactory(mf, this);
        ViewHandler viewHandler = new ViewHandler(vmf, this);
        viewHandler.start();
    }

    @Override
    public void stop(){
        System.out.println("remove cache db");
        // remove cache db
        File cacheDB = new File("cache.db"); 
        cacheDB.delete();
    }

    public boolean getIsSearchDummy(){
        return this.isSearchDummy;
    }

    public boolean getIsUploadDummy(){
        return this.isUploadDummy;
    }

   
}
