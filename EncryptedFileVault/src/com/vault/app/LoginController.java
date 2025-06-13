package com.vault.app;

import com.vault.user.User;
import com.vault.user.UserManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicReference;

public class LoginController {
    public static void register(Stage primaryStage, AtomicReference<User> loggedInUser,
                                AtomicReference<Integer> choice) {
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameText = new TextField();
        PasswordField passwordText = new PasswordField();

        Button registerButton = new Button("Register");

        sceneFields(primaryStage, usernameLabel, passwordLabel, usernameText, passwordText, registerButton);

        registerButton.setOnAction(e -> {
            String username = usernameText.getText();
            String password = passwordText.getText();

            User user = UserManager.register(username, password);
            loggedInUser.set(user);

            if (user == null) {
                VaultFXApp.errorMessageScene(primaryStage, loggedInUser, choice);
            } else {
                EncryptionController.encryptionMenu(primaryStage);
            }
        });
    }

    public static void login(Stage primaryStage, AtomicReference<User> loggedInUser, AtomicReference<Integer> choice) {
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameText = new TextField();
        PasswordField passwordText = new PasswordField();

        Button loginButton = new Button("Login");

        sceneFields(primaryStage, usernameLabel, passwordLabel, usernameText, passwordText, loginButton);

        loginButton.setOnAction(e -> {
            String username = usernameText.getText();
            String password = passwordText.getText();

            User user = UserManager.login(username, password);
            loggedInUser.set(user);

            if (user == null) {
                VaultFXApp.errorMessageScene(primaryStage, loggedInUser, choice);
            } else {
                EncryptionController.encryptionMenu(primaryStage);
            }
        });
    }

    private static void sceneFields(Stage primaryStage, Label usernameLabel, Label passwordLabel,
                                    TextField usernameText, PasswordField passwordText, Button loginButton) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameText, 1, 0);

        grid.add(passwordLabel, 0, 1);
        grid.add(passwordText, 1, 1);

        grid.add(loginButton, 1, 2);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
