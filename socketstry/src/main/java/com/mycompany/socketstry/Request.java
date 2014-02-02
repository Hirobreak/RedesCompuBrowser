/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.socketstry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class Request implements PageHistory{
    String contents;
    String host,path;
    int puerto = 80;
    DataOutputStream out;
    DataInputStream in;
    PrintWriter outw;
    Socket sc;
    Request(String host, String path){
        this.host = host;
        this.path = path;
    }
    public String initClient(){
        String text = "";
        int estado=0;
        String locat="";
         try{
            sc = new Socket(InetAddress.getByName(host), puerto); /*conectar a un servidor en localhost con puerto 5000*/
            outw=new PrintWriter(sc.getOutputStream());  
            System.out.println("AQUI ESTA EL URL : "+host+path + " pathlength="+path.length());
            if (path==""){
                System.out.println("GET / HTTP/1.1");
                outw.println("GET / HTTP/1.1");
            }
            else{
                System.out.println("GET "+path+" HTTP/1.1");
                outw.println("GET "+path+" HTTP/1.1");
            }
            System.out.println("Host: "+host);
            System.out.println("");
            outw.println("Cookies: name=value");
            outw.println("Host: "+host);
            outw.println("");
            outw.flush();
            BufferedReader br=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            StringBuffer builder = new StringBuffer();
            String aux = "";
            int count=0;
            for (int i=0; i<4; i++){
                aux=br.readLine();
                if(i==0){
                    System.out.println(aux);
                    estado=Character.getNumericValue(aux.charAt(9))*100+Character.getNumericValue(aux.charAt(10))*10+Character.getNumericValue(aux.charAt(11));
                }else if(i==3){
                    System.out.println(aux);
                    locat=aux.toString();
                }else{
                    System.out.println(aux);
                }    
            }
            while ((aux = br.readLine()) != null) {
                if (aux.isEmpty()){
                    count=1;
                }
                if (count==1){
                    builder.append(aux);
                }
                if (count==0){
                    System.out.println(aux);
                }
            }
            text = builder.toString();
            System.out.println(estado);
            sc.close();
            br.close();
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error Indeterminado</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, the page you are trying to load may not exist or has an invalid format.</P>\n" +
                "</BODY>\n" +
                "</HTML>";
        }
        if(estado>=300 && estado<=399){
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error Indeterminado</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, the page you are trying to load has moved to "+locat +".</P>\n" +
                "</BODY>\n" +
                "</HTML>";
        }
        if(estado>=400 && estado<=499){
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error Indeterminado</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, Error 404, page not found.</P>\n" +
                "</BODY>\n" +
                "</HTML>";
        }
        return text;
     }
    
    public void getCookieUsingCookieHandler(URL url) { 
        try {       
            // Instantiate CookieManager;
            // make sure to set CookiePolicy
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

            // get content from URLConnection;
            // cookies are set by web site
            URLConnection connection = url.openConnection();
            connection.getContent();

            // get cookies from underlying
            // CookieStore
            CookieStore cookieJar =  manager.getCookieStore();
            List <HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie: cookies) {
              System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        } catch(Exception e) {
            System.out.println("Unable to get cookie using CookieHandler");
            e.printStackTrace();
        }
    }
    public void setCookieUsingCookieHandler(URL url) {
        try {
            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar =  manager.getCookieStore();

            // create cookie
            HttpCookie cookie = new HttpCookie("UserName", "John Doe");

            // add cookie to CookieStore for a
            // particular URL
            cookieJar.add(url.toURI(), cookie);
            System.out.println("Added cookie using cookie handler");
        } catch(Exception e) {
            System.out.println("Unable to set cookie using CookieHandler");
            e.printStackTrace();
        }
    }
}
