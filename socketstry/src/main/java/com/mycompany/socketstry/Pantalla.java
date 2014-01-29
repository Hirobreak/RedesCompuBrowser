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
public class Pantalla extends JFrame{
    
    //HTMLEditorKit kit;
    JEditorPane mostrador;
    JTextField addressBar;
    Pantalla(String str){
        mostrador=new JEditorPane();
        addressBar = new JTextField("Type URL Here");
        addressBar.setVisible(true);
        mostrador.setContentType("text/html");
        mostrador.setText(str);
        JScrollPane scrollPane = new JScrollPane(mostrador);
        mostrador.setEditable(false);
        JFrame ventana = new JFrame("Explorador v0.3");
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(addressBar,BorderLayout.NORTH);
        ventana.add(scrollPane,BorderLayout.CENTER);
        addressBar.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        try {
                            mostrador.setPage(addressBar.getText());
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
        mostrador.addHyperlinkListener(
                new HyperlinkListener(){
                    public void hyperlinkUpdate(HyperlinkEvent e){
                        if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
                            try {
                                mostrador.setPage(e.getURL().toString());
                                addressBar.setText(e.getURL().toString());
                            } catch (IOException ex) {
                                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
        );
        //add(addressBar,BorderLayout.NORTH);
    }
    
}
