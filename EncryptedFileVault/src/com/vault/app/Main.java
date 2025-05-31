package com.vault.app;

import java.util.*;
import com.vault.user.User;
import com.vault.user.UserManager;

public class Main {
    public static void main(String[] args) {
        UserManager.initializeDatabase();

        Scanner scan = new Scanner(System.in);
        User loggedInUser = null;

        while (true) {
            System.out.println("Welcome! Please select one of the following options:\n1. Register\n2. Login\n3. Exit");
            int choice;

            if (scan.hasNextInt()) {
                choice = scan.nextInt();
            } else {
                scan.next();
                choice = -1;
            }

            scan.nextLine();

            switch (choice) {
                case 1:
                    loggedInUser = UserManager.register(scan);
                    break;
                case 2:
                    loggedInUser = UserManager.login(scan);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (loggedInUser != null) {
                VaultApp.vaultMenu(scan, loggedInUser);
            }
        }
    }
}
