package Upd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 9999;
    private static DatagramSocket socket;
    private static JTextArea textArea;
    private static Map<InetAddress, Integer> users = new HashMap<>();
    private static PrivateKey privateKey;
    private static SecretKey aesKey;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(PORT);
            JFrame frame = new JFrame("UDP Chat Server");
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            JTextField replyField = new JTextField(50);
            JButton sendButton = new JButton("Send Reply");

            sendButton.addActionListener(new SendButtonListener(replyField));

            frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
            frame.getContentPane().add(replyField, BorderLayout.SOUTH);
            frame.getContentPane().add(sendButton, BorderLayout.EAST);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            initializeConnection();

            new Thread(Server::receiveMessages).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeConnection() throws Exception {
        KeyPair keyPair = generateRSAKeyPair();
        RSAPublicKey serverPublicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(serverPublicKey.getEncoded());
        sendMessage(publicKeyString);

        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        socket.receive(receivePacket);
        String encryptedAESKey = new String(receivePacket.getData(), 0, receivePacket.getLength());
        byte[] aesKeyBytes = decryptWithRSA(encryptedAESKey);
        aesKey = new SecretKeySpec(aesKeyBytes, "AES");
    }

    private static void receiveMessages() {
        try {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                byte[] encryptedMessage = receivePacket.getData();
                String message = decryptMessage(encryptedMessage);
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                users.put(clientAddress, clientPort);
                textArea.append("Received: " + message + "\n");

                if (message.equalsIgnoreCase("exit")) {
                    textArea.append("Exiting server...\n");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SendButtonListener implements ActionListener {
        private JTextField replyField;

        public SendButtonListener(JTextField replyField) {
            this.replyField = replyField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String reply = replyField.getText();
            if (!reply.isEmpty()) {
                sendMessage(reply);
                replyField.setText("");
            }
        }
    }

    private static void sendMessage(String message) {
        try {
            byte[] encryptedMessage = encryptMessage(message);
            for (Map.Entry<InetAddress, Integer> user : users.entrySet()) {
                DatagramPacket sendPacket = new DatagramPacket(encryptedMessage, encryptedMessage.length, user.getKey(), user.getValue());
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] decryptWithRSA(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        return cipher.doFinal(encryptedBytes);
    }

    private static byte[] encryptMessage(String message) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryptMessage(byte[] encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
