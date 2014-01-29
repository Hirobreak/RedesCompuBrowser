/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.socketstry;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author user
 */
public class Pantalla {
    
    //HTMLEditorKit kit;
    JEditorPane mostrador;
    
    
    Pantalla(String str){
        mostrador=new JEditorPane();

        mostrador.setContentType("text/html");
        mostrador.setText(str);
        JScrollPane scrollPane = new JScrollPane(mostrador);     
        JFrame ventana = new JFrame("Explorador v0.3");
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.getContentPane().add(scrollPane);
    }
    
}
