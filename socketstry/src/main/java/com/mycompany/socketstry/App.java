package com.mycompany.socketstry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class App implements PageHistory
{
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
        URL homepage = new URL("https://www.thepiratebay.org");        
        System.out.println("protocol = " + homepage.getProtocol());
        System.out.println("authority = " + homepage.getAuthority());
        System.out.println("host = " + homepage.getHost());
        System.out.println("port = " + homepage.getPort());
        System.out.println("path = " + homepage.getPath());
        System.out.println("query = " + homepage.getQuery());
        System.out.println("filename = " + homepage.getFile());
        System.out.println("ref = " + homepage.getRef());
        Request conexion=new Request(homepage.getHost(),homepage.getPath());
        conexion.getCookie(homepage);
        conexion.setCookie(homepage);
        Pantalla browser = new Pantalla(conexion.initClient());
        browser.actual = homepage; browser.homepage = homepage; browser.guardarHist(homepage); browser.addressBar.setText(homepage.toString());
    }
}
