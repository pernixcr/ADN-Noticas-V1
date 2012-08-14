package com.example.myapp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HTMLParser {
    static InputStream is = null;
    static String Html = null;
 
    public HTMLParser() { }
    public String getURLContent(String url)
    {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);            
            ResponseHandler<String> resHandler = new BasicResponseHandler();
            String page = httpClient.execute(httpGet, resHandler);
            return page;
        } catch (ClientProtocolException e) {
            return "";
        }  catch (IOException e) {
            return "";
        }
    }
}
