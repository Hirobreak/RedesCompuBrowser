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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    JPanel panelHist = new JPanel();
    
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
        final JFrame historial = new JFrame("Page history");
        historial.setSize(800,600);
        panelHist.setLayout(new GridLayout(100,100));
        historial.add(panelHist,BorderLayout.NORTH);
        historial.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setMenuBar(menu);
        ventana.add(bar, BorderLayout.NORTH);
        ventana.add(scrollPane,BorderLayout.CENTER);
        
        cambiarHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarHomepage(homepage); // hacer en jframe aparte
            }
        });
        
        mostrarHist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historial.setVisible(true);
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
                            if(pagina!=actual){
                                url_history.add(pagina);
                                guardarHist(pagina);
                            }
                            pageGo(pagina);
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
                                if(pagina!=actual){
                                    url_history.add(pagina);
                                    guardarHist(pagina);
                                }
                                pageGo(pagina);
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
                    URL pagina = new URL(addressBar.getText());
                    if (actual!=null){
                        url_back.push(actual);
                    }
                    if(pagina!=actual){
                        url_history.add(pagina);
                        guardarHist(pagina);
                    }
                    pageGo(pagina);
                    refreshButtons();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
        }   
        });
        
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pageGo(actual);
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
                    if(homepage!=actual){
                        url_history.add(homepage);
                        guardarHist(homepage);
                    }
                    pageGo(homepage);
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
                pageGo(back);
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

    public void pageGo(URL gotoURL){
        String host = gotoURL.getHost();
        String path = gotoURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
        addressBar.setText(gotoURL.toString());
        actual = gotoURL;
        refreshButtons();
    }
    public void refreshButtons(){
        if(url_back.empty())
            backButton.setEnabled(false);
        else
            backButton.setEnabled(true);
    }

    public void guardarHist(URL url){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        JLabel j = new JLabel(url.toString()+" - "+dateFormat.format(date));
        panelHist.add(j);
    }
    
    public void cambiarHomepage(URL home){
        final JFrame cambiarHomepage = new JFrame("Cambiar homepage");
        cambiarHomepage.setSize(600, 100);
        cambiarHomepage.setVisible(true);
        Panel panelPrin=new Panel(new GridLayout(3, 1));
        Panel panelTextField=new Panel(new FlowLayout());
        Panel panelLabel=new Panel(new FlowLayout());
        Panel panelboton=new Panel(new GridLayout(1, 2));
        Label homep=new Label("Homepage:");
        final TextField editHome=new TextField(home.toString(), 75);
        Button guardar=new Button("Guardar");
        Button cancelar=new Button("Cancelar");
        panelLabel.add(homep);
        panelTextField.add(editHome);
        panelboton.add(guardar);
        panelboton.add(cancelar);
        panelPrin.add(panelLabel);
        panelPrin.add(panelTextField);
        panelPrin.add(panelboton);
        cambiarHomepage.add(panelPrin);   
        guardar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    homepage=new URL(editHome.getText());
                    cambiarHomepage.dispose();
                } catch (MalformedURLException ex) {
                    editHome.setText("http://" + editHome.getText());
                }
            }
        });
        cancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cambiarHomepage.dispose();
            }
        });
    }
}
