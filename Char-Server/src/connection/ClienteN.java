/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Scanner;

public class ClienteN extends Observable {
    
    private int numeroDoCliente;
    private String ultimaFrase;
    
    // Conexao
    private Socket conexao;
    
    // Ler e escrever
    private Scanner entrada;
    private PrintStream saida;
    
    // Threads
    private ReceberMsg rm;
    
    public ClienteN(int i, Socket c) {
        
        numeroDoCliente = i;
        ultimaFrase = "";
        conexao = c;
        
	try {
            
            entrada = new Scanner(conexao.getInputStream());
            saida = new PrintStream(conexao.getOutputStream());
            
        } catch (IndexOutOfBoundsException e) {
            // TODO
            System.out.println("=> IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO
            System.out.println("=> FileNotFoundException - If the given file object does not denote an existing, writable regular file and a new regular file of that name cannot be created, or if some other error occurs while opening or creating the file");
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO
            System.out.println("=> SecurityException - If a security manager is present and checkWrite(file.getPath()) denies write access to the file");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO
            System.out.println("=> IOException - if an I/O error occurs when creating the input stream, the socket is closed, the socket is not connected, or the socket input has been shutdown using shutdownInput()");
            System.out.println("=> IOException - if an I/O error occurs when creating the output stream or if the socket is not connected.");
            e.printStackTrace();
        }
        
        rm = new ReceberMsg(this);
        rm.start();
        
        System.out.println("=> Cliente " + numeroDoCliente + " criado");
    }
    
    public void esperarMensagem() {
        System.out.println("=> Aguardando mensagem");

	try {
            
            ultimaFrase = entrada.nextLine();
            setChanged();
            notifyObservers();
            
        } catch (NoSuchElementException e) {
            // TODO
            
            ultimaFrase = "###";
            
            System.out.println("=> NoSuchElementException - if no line was found");
            e.printStackTrace();
	} catch (IllegalStateException e) {
            // TODO
            
            ultimaFrase = "###";
            
            System.out.println("=> IllegalStateException - if this scanner is closed");
            e.printStackTrace();
	}

        System.out.println("=> Mensagem recebida");
    }
    
    public void enviarMensagem(String mensagem) {
        saida.println(mensagem);
        
        System.out.println("=> Mensagem enviada ao Cliente " + numeroDoCliente);
    }
    
    public void close() {
        try {
			
            conexao.close();
            entrada.close();
            saida.close();
			
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("=> IOException - If an I/O error occurs.");
            System.out.println("=> IOException - if an I/O error occurs when closing this socket.");
            e.printStackTrace();
	}
		
	System.out.println("=> Cliente finalizado");
    }

    ////////////////////////////////////
    
    public String getId() {
        return conexao.getInetAddress().toString();
    }
    
    public int getNumeroDoCliente() {
        return numeroDoCliente;
    }
    
    public String getUltimaFrase() {
        return ultimaFrase;
    }
    
}
