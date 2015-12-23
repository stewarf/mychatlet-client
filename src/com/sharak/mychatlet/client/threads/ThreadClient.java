package com.sharak.mychatlet.client.threads;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadClient extends Thread {
    
    private BufferedReader entrada;
    
    public ThreadClient(BufferedReader entrada) throws IOException { 
        this.entrada=entrada;
    }
    
    public void run() {
        String txt;
        try {
            while((txt=entrada.readLine()) != null) {
                System.out.println(txt);
                if(txt.equals("/E")){ break; }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            
            if(entrada != null) {
                try {
                    entrada.close();
                } catch(IOException io){}
            }
            
            System.exit(-1);
        }
        
    }
    
}
