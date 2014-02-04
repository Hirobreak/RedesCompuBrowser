
package com.mycompany.socketstry;

import java.net.URL;
import java.util.*;

/*
Clase que guarda los URL de las paginas visitadas para utilizarlas con los botones
forward y back
*/
public interface PageHistory {
    Stack<URL> url_back=new Stack<URL>();
    Stack<URL> url_forward = new Stack<URL>();
    
}
