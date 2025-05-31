# Encrypted File Vault

## Description

A Java-based encrypted file vault application that allows users to securely register, login, and encrypt/decrypt files using password-based AES encryption.

## Features

- User registration and login with secure password hashing and salting.
- File encryption and decryption with AES-256-CBC and PBKDF2 key derivation.
- Command-line interface (CLI) for easy interaction.
- SQLite database to store user info and vault paths.

## Getting Started

### Prerequisites

- Java 24+
- SQLite JDBC Driver (sqlite-jdbc-3.49.1.0.jar) included in lib/ folder (same level as src/)
- Any Java IDE

### Setup

1. Clone this repository:

```
git clone https://github.com/arjunerasani/EncryptedFileVault.git
cd EncryptedFileVault
```

2. Ensure the lib/sqlite-jdbc-3.49.1.0.jar file is present (or if a newer version of the JDBC driver is available, replace the existing driver in the same directory).
3. Add the SQLite JDBC jar as a library/dependency in your IDE or include it in your classpath.

## Running the Application

Run the main class (e.g., com.vault.app.Main) via your IDE or command line:

```
java -cp "lib/*:out/production/EncryptedFileVault" com.vault.app.Main
```

*(Adjust the classpath according to your setup)*

### Usage

- Register a new user with a username and password.
- Login using registered credentials.
- Use the vault menu to encrypt or decrypt files by provided file paths and passwords.

## Project Structure

```
EncryptedFileVault/
|--- src/              # Source code
|--- lib/              # External libraries (SQLite JDBC)
|--- vault/            # Folder for storing the database (create your own or download from the repository)
|--- README.MD/        # This file
```

## Important Notes

- The vault/ folder must exist before running the program otherwise a database error will occur.
- Passowrds are securly hashed and salted; the salt is stored in the database.
- The program uses AES encryption with a random salt and IV for security.

## Contributing

Feel free to open issues or submit pull requests.
