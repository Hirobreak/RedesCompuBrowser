package com.mycompany.socketstry;

import java.net.URL;


public class App 
{
    public static void main( String[] args )
    {
        Request conexion=new Request("www.cs.bham.ac.uk","/~tpc/testpages/");
        new Pantalla(conexion.initClient());
        //System.out.println( "Hello World!" );
    }
}
