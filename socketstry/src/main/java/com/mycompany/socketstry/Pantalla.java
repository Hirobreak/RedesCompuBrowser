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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    JButton homeButton;
    Panel bar;
    URL actual;
    URL homepage;
    MenuBar menu = new MenuBar();
    Menu options = new Menu("Opciones");
    MenuItem cambiarHome = new MenuItem("Cambiar homepage");
    MenuItem mostrarHist = new MenuItem("Mostrar historial");
    MenuItem cerrarBrowser = new MenuItem("Cerrar");
    
    Pantalla(String str) throws IOException{ 
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
        
        homeButton = new JButton();
        ImageIcon homeIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/home.jpg"))); 
        homeButton.setIcon(homeIcon);
        
        okButton = new JButton();
        ImageIcon goIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/go.jpg"))); 
        okButton.setIcon(goIcon);
        
        backButton = new JButton();
        ImageIcon backIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/back.jpg"))); 
        backButton.setIcon(backIcon);
        
        forwardButton = new JButton();
        ImageIcon forwardIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/forward.jpg"))); 
        forwardButton.setIcon(forwardIcon);
        
        refreshButton = new JButton();
        ImageIcon refreshIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/refresh.jpg"))); 
        refreshButton.setIcon(refreshIcon);
        
        refreshButtons();
        options.add(cambiarHome);
        options.add(mostrarHist);
        options.add(cerrarBrowser);
        menu.add(options);
        bar.add(homeButton);
        bar.add(addressBar);
        bar.add(okButton);
        bar.add(backButton);
        bar.add(forwardButton);
        bar.add(refreshButton);
        
        ventana.setSize(1024, 600);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setMenuBar(menu);
        ventana.add(bar, BorderLayout.NORTH);
        ventana.add(scrollPane,BorderLayout.CENTER);
        
        cambiarHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("me da pereza hacer esto"); // hacer en jframe aparte
            }
        });
        
        mostrarHist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Historial de paginas:"); // hacer en jframe aparte
                for(String url : guardarHist())
                    System.out.println(url);
            }
        });
        
        cerrarBrowser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
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
                            if (actual!=null){
                                url_back.push(actual);
                            }
                            pageGo(pagina,false);
                            refreshButtons();
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
                                if (actual!=null){
                                    url_back.push(actual);
                                }
                                pageGo(pagina,false);
                                refreshButtons();
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
                    pageGo(new URL(addressBar.getText()),false);
                    refreshButtons();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
        }   
        });
        
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pageGo(actual,false);
                } catch (Exception ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
        });
        
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (actual!=null){
                        url_back.push(actual);
                    }
                    pageGo(homepage,false);
                    refreshButtons();
                } catch (Exception ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
        });
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if(!url_back.empty()){
                URL back = url_back.pop();
                pageGo(back,true);
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
    }

    public void pageGo(URL gotoURL, boolean esBack){
        String host = gotoURL.getHost();
        String path = gotoURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
        addressBar.setText(gotoURL.toString());
        actual = gotoURL;
        if(actual != gotoURL && esBack==false){
            url_history.add(gotoURL);
        }
        refreshButtons();
    }
    public void refreshButtons(){
        if(url_back.empty())
            backButton.setEnabled(false);
        else
            backButton.setEnabled(true);
    }
    
    public ArrayList<String> guardarHist(){
        ArrayList<String> hist = new ArrayList<String>();
        for(URL u : url_history){
            hist.add(u.toString());
        }
        return hist;
    }
}
