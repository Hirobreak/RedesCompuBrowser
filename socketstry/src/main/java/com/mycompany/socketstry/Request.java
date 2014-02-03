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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Request implements PageHistory{
    String contents;
    String host,path;
    int puerto = 80;
    DataOutputStream out;
    DataInputStream in;
    PrintWriter outw;
    Socket sc;
    boolean redirect= false;
    Request(String host, String path){
        this.host = host;
        this.path = path;
    }
    public String initClient(){
        String text = null;
        int estado=0;
        String locat=null;
        System.out.println();
         try{
             
            sc = new Socket(InetAddress.getByName(host), puerto); /*conectar a un servidor en localhost con puerto 5000*/
            outw=new PrintWriter(sc.getOutputStream());  
            System.out.println("URL: "+host+path + " pathlength="+path.length());
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
                System.out.println("A");
                aux=br.readLine();                
               System.out.println("wwww"+i);                
                    if (aux.contains("Location:")){
                     redirect = true;
                     int j1 = 0;
                     char h1 = 'h';                     
                     while (aux.charAt(j1)!= aux.charAt(aux.length()-1)){
                         if (aux.charAt(j1)==h1){
                         locat=aux.substring(j1);
                         URL redir = new URL(locat);
                         this.host=redir.getHost();
                         this.path=redir.getPath();
                        System.out.println("location"+locat);
                         //estado= 302;
                         
                         }
                         j1=j1+1;
                     }
                    // i=4;
                    }
                
                if(i==0){
                    System.out.println(aux);
                   // System.out.println("000000");
                    estado=Character.getNumericValue(aux.charAt(9))*100+Character.getNumericValue(aux.charAt(10))*10+Character.getNumericValue(aux.charAt(11));
                    System.out.println("estado =="+estado);
                    
                }else if(i==3){
                    System.out.println(aux);
                   // System.out.println("33333");
                    //locat=aux.toString();
                }else if (i==2){
                    System.out.println(aux);
                   // System.out.println("22222");  
                }
                else{
                    System.out.println(aux);
                   //System.out.println("i else "+i+"");                   
                }    
            }
            while ((aux = br.readLine()) != null) {
                if (aux.isEmpty()){
                    count=1;
                   // System.out.println("aux is empty"); 
                }
                if (count==1){
                    builder.append(aux);
                   // System.out.println("aux append"); 
                }
                if (count==0){
                    System.out.println(aux);
                   // System.out.println("aux afuera");
                    
                    if (aux.contains("Location")&&estado==301){
                     redirect = true;
                   // System.out.println("aux dentro"); 
                      
                     int j = 0;
                     char h = 'h';
                     
                     while (aux.charAt(j)!= aux.charAt(aux.length()-1)){
                         if (aux.charAt(j)==h){
                         locat=aux.substring(j);
                         URL redir = new URL(locat);
                         this.host=redir.getHost();
                         this.path=redir.getPath();
                         
                         
                         //Request reconexion=new Request(this.host,this.path);
                         //Pantalla browser2 = new Pantalla(reconexion.initClient());
                         
                         }
                     
                     j=j+1;
                     }
                     System.out.println("aqui esta"); 
                     System.out.println(locat);
                     System.out.println("viste pedro");
                     
                    }
                    // System.out.println("print aux tt"); 
                    
                }
            }
            if (redirect== false)
            text = builder.toString();
            
                
           // System.out.println("AQUI ESTA EL TEXT");
           // System.out.println(text);
           // System.out.println("AQUI TERMINA EL TEXT");
            System.out.println("print estadi init"); 
            System.out.println(estado);
            System.out.println("print estadi fin");
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
         
         
        if(estado==301){
            if (redirect== true){
            Request reconexion=new Request(this.host,this.path);
            text= reconexion.initClient();
            }else{
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error "+estado+"</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, the page you are trying to load has moved to "+locat +".</P>\n" +
                "</BODY>\n" +
                "</HTML>";}
        }
        if(estado==302){            
            if (redirect== true){               
            Request reconexion2=new Request(this.host,this.path);
            text= reconexion2.initClient();
            }
            else{    
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error "+estado+"</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, the page you are trying to load has moved to "+locat +".</P>\n" +
                "</BODY>\n" +
                "</HTML>";                    
            }
            }
            
        
        if(estado>=400 && estado<=499){
            text="<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Error "+estado+"</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "<P>We are sorry, Error "+estado+", page not found.</P>\n" +
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
