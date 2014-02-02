package com.mycompany.socketstry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class App implements PageHistory
{
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
        URL homepage = new URL("http://sheldonbrown.com/web_sample1.html");
        Request conexion=new Request(homepage.getHost(),homepage.getPath());
        Pantalla browser = new Pantalla(conexion.initClient());
        browser.actual = homepage; browser.homepage = homepage; browser.guardarHist(homepage); browser.addressBar.setText(homepage.toString());
    }
}
