package com.zxs.jin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.zxs.jin.https.Https;
import com.zxs.jin.https.HttpsEngine;
import com.zxs.jin.https.StartHttps;
import com.zxs.jin.init.JinMethod;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HttpsEngine engine = HttpsEngine.NEW();
        
        Https https = engine.group("v1/test");
        https.post("/test1", new JinMethod("com.zxs.jin.test.TestController", "PostTest"));
        https.get("/test1", new JinMethod("com.zxs.jin.test.TestController", "GetTest"));
        https.post("/test2/${c}", new JinMethod("com.zxs.jin.test.TestController", "UrlParamsTest"));
        StartHttps.Start(engine);
    }
}
