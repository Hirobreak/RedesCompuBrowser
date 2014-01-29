package com.mycompany.socketstry;
 
 import java.net.*;
 import java.io.*;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
 
 public class Conex{
 
     final String HOST = "www.hyperlinkcode.com";
     final int PUERTO=80;
     Socket sc;
     DataOutputStream out;
     DataInputStream in;
    PrintWriter outw;
     public void initClient(){
         try{
             sc = new Socket(InetAddress.getByName(HOST), PUERTO); /*conectar a un servidor en localhost con puerto 5000*/
             outw=new PrintWriter(sc.getOutputStream());  
             outw.println("GET / HTTP/1.1");
             outw.println("Host: www.hyperlinkcode.com");
             outw.println("");
             outw.flush();
             //out = new DataOutputStream(sc.getOutputStream());
             //in = new DataInputStream(sc.getInputStream());
             //out.writeBytes("GET / HTTP/1.1");
             //out.writeBytes("host: www.serebii.net");
             //out.flush();
             //String resultado=in.readLine();
             String html= null;
             BufferedReader br=new BufferedReader(new InputStreamReader(sc.getInputStream()));
             while((br.readLine()) != null){
                html+=br.readLine();                
             }               
            
            Pantalla explorador=new Pantalla(html);
                        
           

  


                sc.close();
                br.close();
             
             
         }catch(Exception e ){
             System.out.println("Error: "+e.getMessage());
     }
 
     }
 
 }