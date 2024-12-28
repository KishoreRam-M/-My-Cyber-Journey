package Upd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Client {
    private static DatagramSocket socket;
    private static InetAddress ip;
    private static JTextArea textArea;
    private static JTextField textField;
    private static final int SERVER_PORT = 9999;
    private static final String SERVER_ADDRESS = "localhost";
    private static SecretKey aesKey;
    private static PublicKey serverPublicKey;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(SERVER_ADDRESS);
            JFrame frame = new JFrame("UDP Chat Client");
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textField = new JTextField(50);

            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(new SendButtonListener());

            frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(textField, BorderLayout.CENTER);
            panel.add(sendButton, BorderLayout.EAST);
            frame.getContentPane().add(panel, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

            initiateConnection();

            new Thread(Client::receiveMessages).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error initializing the client: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void initiateConnection() throws Exception {
        KeyPair clientKeyPair = generateRSAKeyPair();
        RSAPublicKey clientPublicKey = (RSAPublicKey) clientKeyPair.getPublic();

        String publicKeyString = Base64.getEncoder().encodeToString(clientPublicKey.getEncoded());
        sendMessage(publicKeyString);

        DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
        socket.receive(receivePacket);
        String serverPublicKeyString = new String(receivePacket.getData(), 0, receivePacket.getLength());
        serverPublicKey = loadRSAPublicKey(serverPublicKeyString);

        aesKey = generateAESKey();
        String encryptedAESKey = encryptWithRSA(aesKey.getEncoded(), serverPublicKey);
        sendMessage(encryptedAESKey);
    }

    private static void sendMessage(String msg) {
        try {
            byte[] encryptedMessage = encryptMessage(msg);
            DatagramPacket sendPacket = new DatagramPacket(encryptedMessage, encryptedMessage.length, ip, SERVER_PORT);
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessages() {
        try {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                byte[] encryptedMessage = receivePacket.getData();
                String message = decryptMessage(encryptedMessage);
                SwingUtilities.invokeLater(() -> textArea.append("Server: " + message + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = textField.getText();
            if (!msg.isEmpty()) {
                sendMessage(msg);
                textField.setText("");
            }
        }
    }

    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
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

    private static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static RSAPublicKey loadRSAPublicKey(String keyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static String encryptWithRSA(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}
