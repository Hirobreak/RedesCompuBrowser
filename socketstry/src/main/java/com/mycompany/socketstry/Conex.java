package com.mycompany.socketstry;

import java.net.*;
import java.io.*;

public class Conex {

    final String HOST = "www.cs.bham.ac.uk";
    final int PUERTO=80;
    Socket sc;
    DataOutputStream out;
    DataInputStream in;
    PrintWriter outw;
    public void initClient(){
        try{
            sc = new Socket(InetAddress.getByName(HOST), PUERTO); /*conectar a un servidor en localhost con puerto 5000*/
            outw=new PrintWriter(sc.getOutputStream());  
            outw.println("GET /~tpc/testpages/ HTTP/1.1");
            outw.println("Host: www.cs.bham.ac.uk");
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
            Pantalla explorador=new Pantalla(text);
            
            sc.close();
            br.close();
        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
    }

    }

}

//System.getProperty("line.separator")