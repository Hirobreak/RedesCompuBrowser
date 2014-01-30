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
    ArrayList<URL> URL_list = new ArrayList<URL>();
    Stack<URL> url_back=new Stack<URL>();
}
