/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.socketstry;

import java.net.URL;
import java.util.*;

/**
 *
 * @author Kevin
 */
public interface PageHistory {
    ArrayList<URL> url_history = new ArrayList<URL>();
    Stack<URL> url_back=new Stack<URL>();
    Stack<URL> url_forward = new Stack<URL>();
}
