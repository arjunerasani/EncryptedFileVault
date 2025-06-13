package com.vault.app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import com.vault.crypto.FileEncryptor;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EncryptionController {
    public static void encryptionMenu(Stage primaryStage) {
        AtomicReference<Integer> choice = new AtomicReference<>();

        Label optionLabel = new Label("Please select an option below");
        VBox labelBox = new VBox(optionLabel);
        labelBox.setAlignment(Pos.TOP_CENTER);
        labelBox.setPadding(new Insets(20));

        List<String> options = new ArrayList<>(Arrays.asList("Encrypt", "Decrypt", "Logout"));

        ComboBox<String> optionBox = new ComboBox<>();
        optionBox.getItems().addAll(options);

        VBox root = new VBox(labelBox, optionBox);
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

        optionBox.setOnAction(e -> {
            if (optionBox.getValue().equals("Encrypt")) {
                choice.set(0);
                encryptDecrypt(primaryStage, choice);
            } else if (optionBox.getValue().equals("Decrypt")) {
                choice.set(1);
                encryptDecrypt(primaryStage, choice);
            } else if (optionBox.getValue().equals("Logout")) {
                EncryptionController logoutController = new EncryptionController();
                logoutController.logoutMessage(primaryStage);
            }
        });
    }

    private static void encryptDecrypt(Stage primaryStage,  AtomicReference<Integer> choice) {
        Label fullPathLabel = new Label();
        Label encryptedPathLabel = new Label();
        Label passwordLabel = new Label();
        Button enterButton = new Button();

        if (choice.get() == 0) {
            fullPathLabel = new Label("Enter the full path to the file to be encrypted" +
                    " e.g. C:\\Users\\JohnDoe\\Documents\\secret.txt:");
            passwordLabel = new Label("Enter the password for the file to be encrypted: ");
            encryptedPathLabel = new Label("Enter the full path to where you would like to store the encrypted" +
                    " file e.g. C:\\Users\\JohnDoe\\Documents\\secret.txt:");
            enterButton = new Button("Encrypt");
        } else if (choice.get() == 1) {
            fullPathLabel = new Label("Enter the full path to the file to be decrypted" +
                    " e.g. C:\\Users\\JohnDoe\\Documents\\secret.txt:");
            passwordLabel = new Label("Enter the password for the file to be decrypted" +
                    " e.g. C:\\Users\\JohnDoe\\Documents\\secret.txt:");
            encryptedPathLabel = new Label("Enter the full path to where you would like to store the decrypted" +
                    " file e.g. C:\\Users\\JohnDoe\\Documents\\secret.txt:");
            enterButton = new Button("Decrypt");
        }

        TextField fullPathTextField = new TextField();
        PasswordField passwordTextField = new PasswordField();
        TextField encryptedPathTextField = new TextField();

        VBox fullPathBox = new VBox(fullPathLabel, fullPathTextField);
        VBox passwordBox = new VBox(passwordLabel, passwordTextField);
        VBox encryptedPathBox = new VBox(encryptedPathLabel, encryptedPathTextField);
        VBox buttonBox = new VBox(enterButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, fullPathBox, passwordBox, encryptedPathBox, buttonBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 700, 350));
        primaryStage.show();

        enterButton.setOnAction(e -> {
            String fullPath = fullPathTextField.getText();
            String password = passwordTextField.getText();
            String encryptedPath = encryptedPathTextField.getText();

            if (fullPath.isEmpty() || password.isEmpty() || encryptedPath.isEmpty()) {
                showError(primaryStage, "All fields must be filled.");
                return;
            }

            File inputFile = new File(fullPath);

            if (!inputFile.exists() || !inputFile.isFile()) {
                showError(primaryStage, "The input file does not exist.");
                return;
            }

            File outputFile = new File(encryptedPath);
            File parentDir = outputFile.getParentFile();

            if (parentDir == null || !parentDir.exists() || !parentDir.canWrite()) {
                showError(primaryStage, "Invalid output path or directory not writable.");
                return;
            }

            try {
                if (choice.get() == 0) {
                    FileEncryptor.encryptFile(fullPath, password, encryptedPath);
                } else if (choice.get() == 1) {
                    FileEncryptor.decryptFile(fullPath, password, encryptedPath);
                }

                successMessage(primaryStage, choice, encryptedPath);
            } catch (IOException ex) {
                showError(primaryStage, "An error occurred: " + ex.getMessage());
            }

        });
    }

    private static void showError(Stage primaryStage, String errorMessage) {
        Label errorLabel = new Label(errorMessage);
        Button okButton = new Button("Ok");

        VBox box = new VBox(15, errorLabel, okButton);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(box, 450, 150));
        primaryStage.show();

        okButton.setOnAction(e -> {
            encryptionMenu(primaryStage);
        });
    }

    public void logoutMessage(Stage primaryStage) {
        Label thanksLabel = new Label("Thank You For Using The Encrypted File Vault!");
        Button logoutButton = new Button("Logout");

        VBox labelBox = new VBox(thanksLabel);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(20));

        VBox buttonBox = new VBox(logoutButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox root = new VBox(labelBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        primaryStage.setScene(new Scene(root, 400, 200));

        logoutButton.setOnAction(e -> {
            VaultFXApp redo = new VaultFXApp();
            redo.start(primaryStage);
        });
    }

    public static void successMessage(Stage primaryStage, AtomicReference<Integer> choice, String encryptedFilePath) {
        Label successLabel = new Label();
        Button okButton = new Button("Ok");

        if (choice.get() == 0) {
            successLabel = new Label("Successfully encrypted file and saved to " + encryptedFilePath);
        } else if (choice.get() == 1) {
            successLabel = new Label("Successfully decrypted file and saved to " + encryptedFilePath);
        }

        VBox successLabelBox = new VBox(successLabel);
        successLabelBox.setAlignment(Pos.CENTER);
        successLabelBox.setPadding(new Insets(20));

        VBox buttonBox = new VBox(okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox root = new VBox(successLabelBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

        okButton.setOnAction(e -> {
           encryptionMenu(primaryStage);
        });
    }
}
