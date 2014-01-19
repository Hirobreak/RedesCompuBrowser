package com.mycompany.socketstry;

import java.net.*;
import java.io.*;

public class Conex {

    final String HOST = "serebii.net";
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
            outw.println("Host: serebii.net");
            outw.println("");
            outw.flush();
            //out = new DataOutputStream(sc.getOutputStream());
            //in = new DataInputStream(sc.getInputStream());
            //out.writeBytes("GET / HTTP/1.1");
            //out.writeBytes("host: www.serebii.net");
            //out.flush();
            //String resultado=in.readLine();
            BufferedReader br=new BufferedReader(new InputStreamReader(sc.getInputStream()));
            while((br.readLine()) != null){
                System.out.println(br.readLine());
            }
            sc.close();
            br.close();
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
    }

    }

}

