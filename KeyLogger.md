

### **What is a Keylogger?**
A **keylogger** is a program that records every key pressed on a keyboard. While keyloggers can be used for legitimate purposes, such as monitoring employee activity or recovering lost data, they are often associated with malicious activities like stealing sensitive information (e.g., passwords, credit card details).

---

### **How Does the Code Work?**
The code is a basic implementation of a keylogger that records keystrokes into a text file named `log.txt`.

#### Key Concepts in the Code:

1. **InputStreamReader**:
   - Used to read input from the keyboard.
   - It listens for each key pressed and captures the character representation of the key.

2. **FileWriter**:
   - Used to write the captured keystrokes into a file.
   - The file `log.txt` is opened in append mode (`true`), ensuring that new keystrokes are added to the existing file content.

3. **Infinite Loop**:
   - The `while (true)` loop continuously listens for keyboard input and logs every keystroke until the program is stopped.

4. **Key Reading**:
   - `reader.read()` captures the ASCII value of the key pressed.
   - The ASCII value is converted to a character using `(char) key` and written to the file.

5. **Immediate File Writing**:
   - `writer.flush()` ensures that each keystroke is saved to the file immediately after it's captured.

---

### **Code Breakdown**:

```java
try (FileWriter writer = new FileWriter("log.txt", true);
     InputStreamReader reader = new InputStreamReader(System.in)) {
```
- Opens the `log.txt` file for appending data.
- Sets up an `InputStreamReader` to read keyboard input.

```java
System.out.println("Keylogger running...");
```
- Prints a message indicating that the keylogger is active.

```java
while (true) {
    int key = reader.read();  // Reads a single key
    writer.write((char) key + "\n");  // Logs keystroke
    writer.flush();  // Flush data to file immediately
}
```
- Continuously reads each key pressed (`reader.read()`).
- Logs the keystroke to `log.txt`, converting it to a character.
- Flushes the data to ensure it is saved to the file instantly.

```java
} catch (IOException e) {
    e.printStackTrace();
}
```
- Handles any file writing or reading errors.

---

### **Legality and Ethics of Keyloggers**:
- **Legitimate Uses**:
  - Parental control: Monitoring children’s activity.
  - Employee monitoring: Tracking workplace behavior (with consent).
  - Personal use: Recovering lost work.

- **Malicious Uses**:
  - Stealing passwords, credit card numbers, or other sensitive data.
  - Unauthorized monitoring of a user's activity.

> **Important**: Unauthorized use of keyloggers is illegal in most countries and violates privacy laws. This example should only be used for educational purposes in understanding how input handling works.

---
