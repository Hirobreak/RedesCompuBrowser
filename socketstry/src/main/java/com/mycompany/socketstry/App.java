package com.mycompany.socketstry;

import java.net.MalformedURLException;
import java.net.URL;


public class App implements PageHistory
{
    public static void main( String[] args ) throws MalformedURLException
    {
        URL homepage = new URL("http://www.cs.bham.ac.uk/~tpc/testpages/");
        Request conexion=new Request(homepage.getHost(),homepage.getPath());
        Pantalla browser = new Pantalla(conexion.initClient());
        browser.actual = homepage;
    }
}
