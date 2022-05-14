package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class Util{

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static String getResponse(HttpURLConnection conn)
    {
        StringBuilder str = new StringBuilder();
        BufferedReader reader;
        try
        {
            System.out.print(conn.getResponseCode());
            /*
            if (conn.getResponseCode() != StatusCode.SUCCESS.getHttpCode())
            {
                throw new WebException(conn.getResponseCode());
            }*/
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();;
        }
        if (str.toString().equals(""))
        {
            System.out.print("unknown error");
        }
        return str.toString();
    }
}