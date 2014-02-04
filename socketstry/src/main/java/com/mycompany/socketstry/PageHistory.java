
package com.mycompany.socketstry;

import java.net.URL;
import java.util.*;


public interface PageHistory {
    Stack<URL> url_back=new Stack<URL>();
    Stack<URL> url_forward = new Stack<URL>();
    
}
