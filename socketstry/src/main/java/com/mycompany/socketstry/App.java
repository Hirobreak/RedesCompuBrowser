package com.mycompany.socketstry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class App implements PageHistory
{
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
        URL homepage = new URL("http://www.serebii.net/index2.shtml");
        Request conexion=new Request(homepage.getHost(),homepage.getPath());
        url_history.add(homepage); 
        Pantalla browser = new Pantalla(conexion.initClient());
        browser.actual = homepage; browser.homepage = homepage;
    }
}
