package com.vault.app;

import java.io.IOException;
import java.util.*;
import com.vault.user.User;
import com.vault.crypto.FileEncryptor;


public class VaultApp {
    public static void vaultMenu(Scanner scan, User loggedInUser) {
        int choice;

        do {
            System.out.println("Vault Menu:");
            System.out.println("1. Encrypt a file\n2. Decrypt a file\n3. Logout\nPlease enter your choice: ");

            if (scan.hasNextInt()) {
                choice = scan.nextInt();
            } else {
                scan.next();
                choice = -1;
            }

            scan.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Please enter the full path of the file to encrypt" +
                            " e.g. C:\\\\Users\\\\JohnDoe\\\\Documents\\\\secret.txt: ");
                    String filePath = scan.nextLine();

                    System.out.println("Please enter the password for the file: ");
                    String password = scan.nextLine();

                    System.out.println("Please enter the full path to where you would like to store the encrypted" +
                            " file e.g. C:\\\\Users\\\\JohnDoe\\\\Documents\\\\secret.txt: ");
                    String encryptedFilePath = scan.nextLine();

                    try {
                        FileEncryptor.encryptFile(filePath, password, encryptedFilePath);
                    } catch (IOException e) {
                        System.out.println("Problem while encrypting file: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Please enter the full path to your encrypted file" +
                            " e.g. C:\\\\Users\\\\JohnDoe\\\\Documents\\\\secret.txt: ");
                    String fileEncryptedPath = scan.nextLine();

                    System.out.println("Please enter the password for the file: ");
                    String encryptedFilePassword = scan.nextLine();

                    System.out.println("Please enter the full path to where you would like to store the decrypted" +
                            " file e.g. C:\\\\Users\\\\JohnDoe\\\\Documents\\\\secret.txt: ");
                    String decryptedFilePath = scan.nextLine();

                    FileEncryptor.decryptFile(fileEncryptedPath, encryptedFilePassword, decryptedFilePath);
                    break;
                case 3:
                    System.out.println("Thank you for using the Vault App!");
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
}
