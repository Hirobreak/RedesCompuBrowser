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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author user
 */
public class Pantalla extends JFrame implements PageHistory{
    
 
    JEditorPane mostrador;
    static JTextField addressBar;
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
        addressBar = new JTextField();
        addressBar.setPreferredSize(new Dimension(500, 27));
        addressBar.setVisible(true);
        mostrador.setContentType("text/html");
        mostrador.setText(str);
        final JScrollPane scrollPane = new JScrollPane(mostrador);
        mostrador.setEditable(false);
        
        JFrame ventana = new JFrame("Explorador v0.3");
        final JFrame historial = new JFrame("Page history");
        historial.setSize(825,600);
        panelHist.setLayout(new GridLayout(100,0));
        JScrollPane scrollhist = new JScrollPane(panelHist);
        historial.add(scrollhist);
        historial.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        homeButton = new JButton();
        ImageIcon homeIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/home.jpg"))); 
        homeButton.setIcon(homeIcon);
        homeButton.setToolTipText("Go to homepage");
        
        okButton = new JButton();
        ImageIcon goIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/go.jpg"))); 
        okButton.setIcon(goIcon);
        okButton.setToolTipText("Go to specified address");
        
        backButton = new JButton();
        ImageIcon backIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/back.jpg"))); 
        backButton.setIcon(backIcon);
        backButton.setToolTipText("Go to previous page");
        
        forwardButton = new JButton();
        ImageIcon forwardIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/forward.jpg"))); 
        forwardButton.setIcon(forwardIcon);
        forwardButton.setToolTipText("Go to next page");
        
        refreshButton = new JButton();
        ImageIcon refreshIcon = new ImageIcon(ImageIO.read(new File("../socketstry/src/main/java/resources/refresh.jpg"))); 
        refreshButton.setIcon(refreshIcon);
        refreshButton.setToolTipText("Reload current page");
        
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
                cambiarHomepage(homepage);
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
                            if(!pagina.toString().equals(actual.toString()) && actual!=null){
                                url_back.push(actual);
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
                                if(!pagina.toString().equals(actual.toString()) && actual!=null){
                                    url_back.push(actual);
                                    guardarHist(pagina);
                                }
                                pageGo(pagina);
                                System.out.println(e.getDescription());
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
                    if(!pagina.toString().equals(actual.toString()) && actual!=null){
                        url_back.push(actual);
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
                    if(!homepage.toString().equals(actual.toString()) && actual!=null){
                        guardarHist(homepage);
                        url_back.push(actual);
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
                url_forward.push(actual);
                pageGo(back);
            }else{
                mostrador.setText("<html> Start Site <html>");
            }
        }
        });
        
        
        
        
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if(!url_forward.empty()){
                URL forward = url_forward.pop();
                url_back.push(actual);
                pageGo(forward);
            }else{
                mostrador.setText("<html> End Site <html>");
            }
        }
        });
    }
    
    
    public void cambiarAddress(String locat){
        addressBar.setText(locat);
        }

    public void pageGo(URL gotoURL){
        String host = gotoURL.getHost();
        String path = gotoURL.getPath();
        Request r = new Request(host,path);
        mostrador.setText(r.initClient());
        addressBar.setText(gotoURL.toString());
        actual = gotoURL;
        r.getCookie(actual);
        r.setCookie(actual);
        refreshButtons();
    }
    public void refreshButtons(){
        if(url_back.empty())
            backButton.setEnabled(false);
        else
            backButton.setEnabled(true);
        if(url_forward.empty())
            forwardButton.setEnabled(false);
        else
            forwardButton.setEnabled(true);
    }

    public void guardarHist(URL url){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton j = new JButton(url.toString());
        JLabel dateLabel = new JLabel(dateFormat.format(date));
        addListenerHistButton(j);
        panel.add(j);
        panel.add(dateLabel);
        panelHist.add(panel);
    }
    
    public void addListenerHistButton(final JButton histButton){
        histButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    URL urlButton = new URL(histButton.getText());
                    System.out.println("histButton: "+histButton.getText()+" actual: "+actual.toString());
                    if(!urlButton.toString().equals(actual.toString()) && actual!=null){
                        guardarHist(urlButton);
                        url_back.push(actual);
                    }
                    pageGo(urlButton);
                    refreshButtons();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {

                } catch (Exception ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        });
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
