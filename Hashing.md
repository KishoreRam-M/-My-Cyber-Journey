# **Hashing (SHA-256)**

Hashing ensures data integrity by generating a unique fixed-length hash value.  

---

## **Key Properties**
1. **Hashing Algorithms:** Examples include SHA-256 and MD5.  
2. **One-Way Operation:** Hashing is irreversible and cannot be decrypted.  

---

## **Example in Java**
```java
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class HashingExample {
    public static void main(String[] args) throws Exception {
        String data = "Hello, World!";
        
        // Hash the data
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        
        // Convert hash to hexadecimal format
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hash) {
            hexHash.append(String.format("%02x", b));
        }
        
        System.out.println("Hashed Data (SHA-256): " + hexHash.toString());
    }
}
