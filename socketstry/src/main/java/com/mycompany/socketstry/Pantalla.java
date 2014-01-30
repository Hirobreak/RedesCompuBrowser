/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.socketstry;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author user
 */
public class Pantalla extends JFrame implements PageHistory{
    
    //HTMLEditorKit kit;
    JEditorPane mostrador;
    JTextField addressBar;
    JButton okButton;
    JButton refreshButton;
    JButton backButton;
    JButton forwardButton;
    Panel bar;
    URL actual;
    
    Pantalla(String str){ 
        actual=null;
        bar=new Panel(new FlowLayout());
        mostrador=new JEditorPane();
        addressBar = new JTextField("Type URL Here");
        addressBar.setPreferredSize(new Dimension(500, 27));
        addressBar.setVisible(true);
        mostrador.setContentType("text/html");
        mostrador.setText(str);
        JScrollPane scrollPane = new JScrollPane(mostrador);
        mostrador.setEditable(false);
        JFrame ventana = new JFrame("Explorador v0.3");
        okButton = new JButton("Go");
        backButton = new JButton("<<");
        forwardButton = new JButton(">>");
        refreshButton = new JButton("Refresh");
        
        bar.add(addressBar);
        bar.add(okButton);
        bar.add(backButton);
        bar.add(forwardButton);
        bar.add(refreshButton);
        
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //ventana.add(addressBar,BorderLayout.NORTH);
        ventana.add(bar, BorderLayout.NORTH);
        ventana.add(scrollPane,BorderLayout.CENTER);
        
        addressBar.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        try{
                            URL pagina = new URL(addressBar.getText());
                        }
                        catch(MalformedURLException ex){
                            addressBar.setText("http://" + addressBar.getText());
                        }
                        
                        try {
                            URL pagina = new URL(addressBar.getText());
                            String host = pagina.getHost();
                            String path = pagina.getPath();
                            Request r = new Request(host,path);
                            mostrador.setText(r.initClient());
                            if (actual!=null){
                                url_back.push(actual);
                            }
                            actual=pagina;
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                            mostrador.setText("<html> Error:"+ ex.getMessage()+ "<html>");
                            System.out.println("Error URL");
                        }
                    }
                }
        );
        
        mostrador.addHyperlinkListener(
                new HyperlinkListener(){
                    public void hyperlinkUpdate(HyperlinkEvent e){
                        if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
                            try {
                                URL pagina = e.getURL();
                                String host = pagina.getHost();
                                String path = pagina.getPath();
                                Request r = new Request(host,path);
                                mostrador.setText(r.initClient());
                                addressBar.setText(e.getURL().toString());
                                if (actual!=null){
                                    url_back.push(actual);
                                }
                                actual=pagina;
                            } catch (Exception ex) {
                                System.out.println("Error hyperlink");
                            }
                        }
                    }
                }
        );
                
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    URL pagina = new URL(addressBar.getText());
                }
                catch(MalformedURLException ex){
                    addressBar.setText("http://" + addressBar.getText());
                }
                try {
                    if (actual!=null){
                        url_back.push(actual);
                    }
                    actual=new URL(addressBar.getText());
                    pageGo(new URL(addressBar.getText()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
        }   
        });
        
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            /*    try {// USAR URL ACTUAL
                    pageRefresh(new URL(addressBar.getText()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            */
        }
        });
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if(!url_back.empty()){
                pageGo(url_back.pop());
            }else{
                mostrador.setText("<html> Start Site <html>");
            }

        }
        });
        
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            /*    try { //USAR URL_list+1
                    pageBack(new URL(addressBar.getText()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            */
        }
        });
        //add(addressBar,BorderLayout.NORTH);
    }
    public void pageBack(URL previousURL){
        String host = previousURL.getHost();
        String path = previousURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
    }
    public void pageForward(URL nextURL){
        String host = nextURL.getHost();
        String path = nextURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
    }
    public void pageRefresh(URL currentURL){
        String host = currentURL.getHost();
        String path = currentURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
    }
    public void pageGo(URL gotoURL){
        String host = gotoURL.getHost();
        String path = gotoURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
        URL_list.add(gotoURL);
    }
}
