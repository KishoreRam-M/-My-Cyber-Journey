# **Introduction to Cryptography**

Cryptography is the practice and study of techniques for securing communication and data from unauthorized access. It transforms readable data (plaintext) into an unreadable format (ciphertext) to protect sensitive information.

---

## **Key Concepts in Cryptography**

1. **Encryption**  
   - Converts plaintext into ciphertext using an algorithm and a key.  
   - Ensures data confidentiality.  

2. **Decryption**  
   - Converts ciphertext back into plaintext using an algorithm and the same or a related key.  

3. **Keys**  
   - **Symmetric Key:** Same key for encryption and decryption.  
   - **Asymmetric Key:** Public key for encryption and private key for decryption.  

4. **Hashing**  
   - Converts data into a fixed-length hash value.  
   - Cannot be reversed. Used for data integrity.

---

## **Applications of Cryptography**

1. **Data Encryption:** Protect sensitive information during transmission.  
2. **Authentication:** Verify the identity of users (e.g., digital certificates).  
3. **Digital Signatures:** Ensure the authenticity and integrity of messages.  
4. **Secure Password Storage:** Store hashed passwords instead of plaintext.  
5. **Blockchain:** Maintain secure and immutable records of transactions.  

---

## **Cryptographic Techniques with Java**

### **1. Symmetric Encryption (AES)**  
- Uses the same key for both encryption and decryption.  
- **Steps:**  
  1. Generate or share a secret key.  
  2. Encrypt data using the secret key.  
  3. Decrypt data with the same key.  

**Example in Java:**  
```java
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SymmetricEncryption {
    public static void main(String[] args) throws Exception {
        // Generate secret key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size
        SecretKey secretKey = keyGen.generateKey();

        // Encrypt data
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String data = "Hello, World!";
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        System.out.println("Encrypted Data: " + new String(encryptedData));

        // Decrypt data
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        System.out.println("Decrypted Data: " + new String(decryptedData));
    }
}
