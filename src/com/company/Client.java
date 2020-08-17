package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    public String username;
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private int port;
    private Scanner inputScanner;
    private Thread t;

    public Client(int port) {
        this.port = port;
        this.inputScanner = new Scanner(System.in);
    }

    public void login(){
        username = readClientInput("Type in your username:");

        try {
            connectToServer(port);
            outStream.writeObject(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToServer(int port) throws IOException {
        socket = new Socket(InetAddress.getByName("localhost"), port);
        outStream = new ObjectOutputStream(socket.getOutputStream());
        inStream = new ObjectInputStream(socket.getInputStream());
    }

    public void writeMessage() throws IOException {
        String recipient = readClientInput("Select a contact to write to:");
        String message = readClientInput(String.format("Type in message for %s:", recipient));
        outStream.writeObject(new Message(username, recipient, message));
    }

    public void readMessage() throws IOException, ClassNotFoundException {
        Message receivedMessage = (Message) inStream.readObject();
        System.out.println(receivedMessage);
    }

    public String readClientInput(String textToDisplay){
        System.out.println(textToDisplay);
        return inputScanner.nextLine();
    }

    public void startChat(){
        t = new Thread(this,"writing_thread");
        t.start();

        while(true) {
            try {
                readMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                writeMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
