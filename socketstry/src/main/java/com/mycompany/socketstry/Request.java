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
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Kevin
 */
public class Request {
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
    public void initClient(){
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
            outw.println("Host: "+host);
            outw.println("");
            outw.flush();
            BufferedReader br=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            StringBuffer builder = new StringBuffer();
            String aux = "";
            while ((aux = br.readLine()) != null) {
                builder.append(aux);
                //builder.append(System.getProperty("line.separator"));
            }
            String text = builder.toString();
            System.out.println(text);
            new Pantalla(text);
            sc.close();
            br.close();
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
     }
}
