package com.sharak.mychatlet.client;

import com.sharak.mychatlet.client.models.Server;
import com.sharak.mychatlet.client.threads.ThreadClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyChatLetClient {
    
    private Server server;
    private Socket clientSocket;
    
    public static void main(String[] args){
        new MyChatLetClient(args);
    }
    
    public MyChatLetClient(String args[]){
        if(args.length < 2){
            server = new Server(Server.DEFAULT_HOSTNAME,Server.DEFAULT_PUERTO);
        } else {
            server = new Server(args[0],Integer.parseInt(args[1]));
        }
        
        System.out.println("Conectando client al server");
        
        iniciarCliente(args);
        procesarMensajes();
    }
    
    private void iniciarCliente(String[]args)
    {
        try {
            
            clientSocket = new Socket(server.getHostname(),server.getPuerto());
            
        } catch(IOException ioe) {
            System.out.println("No se conecto al server. "+ioe); 
        }
    }
    
    private void procesarMensajes()
    {
        BufferedReader entrada=null;
        PrintWriter salida=null;			
        try {
            entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            salida = new PrintWriter(clientSocket.getOutputStream(),true);			
            BufferedReader entradaConsola=new BufferedReader(new InputStreamReader(System.in));
            
            new ThreadClient(entrada).start();
            
            while (true) {
                salida.println(entradaConsola.readLine());
            }
        } catch(IOException io) {
            io.printStackTrace();
            
            if(entrada != null) {
                try { 
                    entrada.close(); 
                } catch(Exception e) {
                    entrada=null;
                }
            }
            
            if(salida != null) {
                try {
                    salida.close();
                } catch(Exception ei) {
                    salida=null;
                }
            }
            
            if(clientSocket != null) {
                try {
                    clientSocket.close();
                } catch(Exception ee) {
                    clientSocket=null;
                }
            }
            
        }
        
    }
    
}
