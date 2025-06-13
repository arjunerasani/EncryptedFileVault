package com.vault.app;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import com.vault.user.User;
import com.vault.user.UserManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VaultFXApp extends Application {
    private static void createChoiceScene(Stage primaryStage, AtomicReference<User> loggedInUser,
                                    AtomicReference<Integer> choice) {
        Label label2 = new Label("Please select an option below");
        VBox labelBox2 = new VBox(label2);
        labelBox2.setAlignment(Pos.TOP_CENTER);
        labelBox2.setPadding(new Insets(20));

        List<String> options = new ArrayList<>(Arrays.asList("Register", "Login"));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(options);

        VBox root2 = new VBox(labelBox2, comboBox);
        root2.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root2, 400, 200));
        primaryStage.show();

        comboBox.setOnAction(f -> {
            if (comboBox.getValue().equals("Register")) {
                choice.set(0);
                LoginController.register(primaryStage, loggedInUser, choice);
            } else if (comboBox.getValue().equals("Login")) {
                choice.set(1);
                LoginController.login(primaryStage, loggedInUser, choice);
            }
        });
    }

    public static void errorMessageScene(Stage primaryStage, AtomicReference<User> loggedInUser,
                                   AtomicReference<Integer> choice) {
        Label errorLabel = new Label();
        Button errorButton = new Button("Ok");

        if (choice.get() == 0) {
            errorLabel = new Label("There was an error, please try registering again.");
        } else if (choice.get() == 1) {
            errorLabel = new Label("It seems your credentials are invalid, please try again.");
        }

        VBox registerErrorBox = new VBox(errorLabel);
        registerErrorBox.setAlignment(Pos.CENTER);
        registerErrorBox.setPadding(new Insets(20));

        VBox registerErrorBox2 = new VBox(errorButton);
        errorButton.setAlignment(Pos.CENTER);
        registerErrorBox2.setPadding(new Insets(10));

        VBox root3 = new VBox(registerErrorBox, registerErrorBox2);
        root3.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root3, 400, 200));
        primaryStage.show();

        errorButton.setOnAction(e -> {
            createChoiceScene(primaryStage, loggedInUser, choice);
        });
    }

    public void start(Stage primaryStage) {
        UserManager.initializeDatabase();

        AtomicReference<User> loggedInUser = new AtomicReference<>();
        AtomicReference<Integer> choice = new AtomicReference<>();

        Label label = new Label("Welcome to the Encrypted File Vault!");
        VBox labelBox = new VBox(label);
        labelBox.setAlignment(Pos.TOP_CENTER);
        labelBox.setPadding(new Insets(20));

        Button continueButton = new Button("Continue");
        VBox buttonBox = new VBox(continueButton);
        buttonBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(10));

        VBox root = new VBox(labelBox, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("Encrypted File Vault");
        primaryStage.setScene(scene);
        primaryStage.show();

        continueButton.setOnAction(e -> {
            createChoiceScene(primaryStage, loggedInUser, choice);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
