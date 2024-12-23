

### 1. **What is Encryption?**
Encryption is a technique that scrambles plain text (readable data) into ciphertext (unreadable data). Only authorized parties with the decryption key can revert it back to its original form.

**Purpose:** Protect sensitive information such as passwords, messages, and financial data from being accessed or tampered with.

---

### 2. **How Encryption Works**
Encryption involves three main components:
- **Plaintext**: The original readable message.
- **Key**: A unique string used to encrypt and decrypt the message.
- **Ciphertext**: The encrypted message.

The encryption process follows this formula:
\[
\text{Ciphertext} = \text{Encrypt}(\text{Plaintext}, \text{Key})
\]

**Decryption Formula**:
\[
\text{Plaintext} = \text{Decrypt}(\text{Ciphertext}, \text{Key})
\]

---

### 3. **Types of Encryption**
There are two primary types of encryption:
#### a. **Symmetric Encryption**
- **Key Usage**: The same key is used for both encryption and decryption.
- **Examples**: AES, DES.
- **Use Case**: Faster and suitable for securing data at rest (e.g., files).

**Visualization**:
1. Alice encrypts a message using a shared secret key.
2. Bob decrypts it using the same key.

#### b. **Asymmetric Encryption**
- **Key Usage**: A pair of keys is used — a public key for encryption and a private key for decryption.
- **Examples**: RSA, ECC.
- **Use Case**: Ideal for secure communication, such as email encryption.

**Visualization**:
1. Alice encrypts a message using Bob's public key.
2. Bob decrypts it using his private key.

---

### 4. **Step-by-Step Encryption Example**

#### Scenario: Symmetric Encryption with AES
- **Plaintext**: "HELLO"
- **Key**: "SECRET123"
- **Cipher Algorithm**: AES

**Steps**:
1. Convert plaintext ("HELLO") into binary or ASCII representation.
   ```
   H -> 01001000, E -> 01000101, ...
   ```
2. Apply the AES algorithm using the key.
   ```
   Encrypt("HELLO", "SECRET123") -> Ciphertext (e.g., "23AC9B")
   ```
3. Transmit ciphertext securely.

**Decryption Steps**:
1. Bob receives the ciphertext "23AC9B".
2. Bob decrypts it using the same key "SECRET123".
3. The result is the original message "HELLO".

---

### 5. **Real-World Applications of Encryption**
- **Web Browsing**: HTTPS encrypts data between your browser and websites.
- **Messaging**: Apps like WhatsApp and Signal use end-to-end encryption.
- **Data Storage**: Disk encryption tools (e.g., BitLocker) secure sensitive files.
- **Email**: Tools like PGP ensure secure email communication.

---

### 6. **Common Encryption Algorithms**
| Algorithm   | Type         | Key Size        | Use Case                      |
|-------------|--------------|-----------------|-------------------------------|
| AES         | Symmetric    | 128, 192, 256 bits | Securing data at rest         |
| RSA         | Asymmetric   | 1024, 2048, 4096 bits | Secure key exchange          |
| ECC         | Asymmetric   | 256 bits (equiv. to RSA 3072) | Compact, efficient encryption |
| DES/3DES    | Symmetric    | 56/168 bits     | Legacy systems (less secure)  |

---

### 7. **Visualization**

Here’s a step-by-step visualization of encryption:

1. **Encryption Process**:
   - Input: "HELLO"
   - Key: "SECRET123"
   - Ciphertext Output: "X1Y2Z3"

   ```
   Plaintext --> Encryption (Key) --> Ciphertext
   ```

2. **Decryption Process**:
   - Input: "X1Y2Z3"
   - Key: "SECRET123"
   - Plaintext Output: "HELLO"

   ```
   Ciphertext --> Decryption (Key) --> Plaintext
   ```

3. **Asymmetric Encryption**:
   - Public Key encrypts: "HELLO" --> "A1B2C3"
   - Private Key decrypts: "A1B2C3" --> "HELLO"

---




                             

```
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEncryptionExample {
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String encrypt(String plaintext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            SecretKey secretKey = generateKey();
            String plaintext = "Hello, AES Encryption!";
            String encryptedText = encrypt(plaintext, secretKey);
            System.out.println("Encrypted Text: " + encryptedText);
            String decryptedText = decrypt(encryptedText, secretKey);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
