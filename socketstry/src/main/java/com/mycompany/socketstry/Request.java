
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
import static com.mycompany.socketstry.Pantalla.*;

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
         
         
         if(estado==100)
         {
              System.out.println("CONTINUE");
         }
         if(estado==101)
         {
              System.out.println("SWITCHING PROTOCOLS");
         }
         if(estado==102)
         {
              System.out.println("PROCESSING");
         }
         if(estado==200)
         {
              System.out.println("OK");
         }
         if(estado==201)
         {
              System.out.println("CREATED");
         }
         if(estado==202)
         {
             System.out.println("ACCEPTED");
         }
         if(estado==203)
         {
              System.out.println("Non-Authoritative Information");
         }
         if(estado==204)
         {
              System.out.println("No Content");
         }
         if(estado==205)
         {
              System.out.println("Reset Content");
         }
         if(estado==206)
         {
              System.out.println("Partial Content");
         }
         if(estado==207)
         {
              System.out.println("Multi-Status");
         }
         if(estado==208)
         {
              System.out.println("Already Reported");
         }
         if(estado==226)
         {
              System.out.println("IM Used");
         }
         
        if(estado==301){
            if (redirect== true){
            Request reconexion=new Request(this.host,this.path);
            text= reconexion.initClient();
            URL location = null;
                try {
                    location = new URL(locat);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
            reconexion.getCookie(location);
            reconexion.setCookie(location);
            
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
            URL location = null;
                try {
                    location = new URL(locat);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
            reconexion2.getCookie(location);
            reconexion2.setCookie(location);
            //addressBar.setText(locat);
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
    
    public void getCookie(URL url) { 
        try {       
           
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

          
            URLConnection connection = url.openConnection();
            connection.getContent();

           
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
    public void setCookie(URL url) {
        try {
            
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar =  manager.getCookieStore();

            
            HttpCookie cookie = new HttpCookie("usuario", "Edison");

            
            cookieJar.add(url.toURI(), cookie);
            System.out.println("Added cookie using cookie handler");
        } catch(Exception e) {
            System.out.println("Unable to set cookie using CookieHandler");
            e.printStackTrace();
        }
    }
}
