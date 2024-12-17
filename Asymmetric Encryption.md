# **Asymmetric Encryption (RSA)**

Asymmetric encryption uses a pair of keys:  
- **Public Key:** Used for encryption.  
- **Private Key:** Used for decryption.  

---

## **Steps:**
1. Generate a public-private key pair.  
2. Share the public key with the sender.  
3. The sender encrypts data using the public key.  
4. The receiver decrypts the encrypted data using the private key.  

---

## **Example in Java**
```java
import java.security.*;
import javax.crypto.Cipher;

public class AsymmetricEncryption {
    public static void main(String[] args) throws Exception {
        // Generate key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Key size: 2048 bits
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Data to encrypt
        String data = "Hello, World!";

        // Encrypt data
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        System.out.println("Encrypted Data: " + new String(encryptedData));

        // Decrypt data
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        System.out.println("Decrypted Data: " + new String(decryptedData));
    }
}
