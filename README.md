# Encrypted File Vault

## Description

The Encrypted File Vault is a JavaFX-based desktop application that allows users to **securely encrypt and decrypt files** using a password of their choice. It includes full user authentication and handles encryption/decryption with helpful feedback messages  all wrapped in a clean and simple user interface.

## Features

- **User Registration & Login** system with secure password hashing
- **File Encryption & Decryption** using password-protected AES
- **JavaFX GUI** with clear instructions and entry fields
- **Custom file path support** for input and output
- **Success and error dialogs** for all major operations
- Built-in SQLite database for user authentication

## Project Structure

```
EncryptedFileVault/
|--- src/              # Source code
|--- lib/              # External libraries (SQLite JDBC)
|--- vault/            # Folder for storing the database (create your own or download from the repository)
|--- README.md/        # This file
|--- LICENSE.md        # MIT license
```

## Technologies Used

- Java 17+
- JavaFX (for GUI)
- SQLite (via JDBC)
- AES Encryption (Java Cryptography Extension)
- MVC-style architecture

## Setup Instructions

### Requirements

- Java JDK 17+
- JavaFX SDK
- SQLite (JDBC bundled or included)
- IDE such as IntelliJ IDEA or Eclipse

### Steps to Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/YOUR_USERNAME/EncryptedFileVault.git
   cd EncryptedFileVault
   ```

2. **Open the project in your IDE** and configure JavaFX:

- Set the --module-path VM option to point to the JavaFX SDK's lib directory.
- Include --add-modules javafx.controls,javafx.fxml in the VM options.

3. **Run the application**:

- Run the VaultFXApp.java class.
- The GUI will open with a registration or login screen.

## How It Works

### Authentication

- Passwords are hashed using a secure algorithm before storing them in the SQLite database.
- Users can register with a unique username and password.

### File Encryption

- Files are encrypted using AES with user-provided password.
- You specify both the input and output destination via file paths.
- On successful encryption or decryption, a confirmation is shown.

### Error Handling

- Validates file existence and file paths.
- Prevents empty fields or invalid inputs.
- Graceful handling of I/O and cryptography errors.

## Author

Arjun Erasani

Email: arerasani9@gmail.com

GitHub: https://github.com/arjunerasani
