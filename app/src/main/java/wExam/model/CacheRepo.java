package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import model.ContentModel;
import model.ContentSearchResultModel;

public class CacheRepo{

    private static final String connString ="jdbc:sqlite:cache.db";


    public static void createCacheDB(){
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection(connString);
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists tag");
          statement.executeUpdate("create table tag (input string, tagid string)");

          statement.executeUpdate("drop table if exists content");
          statement.executeUpdate("create table content (input string, webtitle string, weburl string, webpublicationdate string)");
        
         
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.out.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.out.println(e.getMessage());
          }
        }
    }

    public static void clearCache(){
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection(connString);
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("delete from content");
          
         
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.out.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.out.println(e.getMessage());
          }
        }
    }

    public static void saveContentsToCache(String input, List<ContentModel> contents){
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection(connString);
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          for (ContentModel temp : contents) {
     
            statement.executeUpdate(String.format("insert into content values('%s', '%s','%s','%s')",input, 
            temp.getWebTitle().replace("'","''"),
            temp.getWebUrl(),
            temp.getWebPublicationDate()));
          }
          
         
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.out.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.out.println(e.getMessage());
          }
        }
    }

    public static ContentSearchResultModel getContentsFromCache(String input){
        Connection connection = null;
        
        ContentSearchResultModel result = new ContentSearchResultModel();
        List<ContentModel> contents = new ArrayList<ContentModel>();
        
        try
        {
          // create a database connection
          connection = DriverManager.getConnection(connString);
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          ResultSet rs = statement.executeQuery(String.format("select * from content where input='%s' ", input));
          while(rs.next())
          {
            // read the result set
            ContentModel t = new ContentModel();
            t.setWebTitle(rs.getString("webtitle"));
            t.setWebUrl(rs.getString("weburl"));
            t.setWebPublicationDate(rs.getString("webpublicationdate"));
            contents.add(t);
          
          }

          result.setTotal(contents.size());
          result.setResults(contents);
         
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.out.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.out.println(e.getMessage());
          }
        }

        return result;
    }
}