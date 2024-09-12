package com.workPal.view.Admin;

import com.workPal.controller.ManagerController;
import com.workPal.enums.Role;
import com.workPal.model.Admin;
import com.workPal.model.Manager;
import com.workPal.utility.Validation.UserValidation;
import com.workPal.utility.ViewUtility;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class ManagerMngUI {
    private final Scanner scanner = new Scanner(System.in);
    private final ManagerController managerController = new ManagerController();
    private final Admin adminAuth;
    public ManagerMngUI(Admin admin){
        this.adminAuth = admin;
    }

    public void runManagerMngUI() {
        int choice = 0;
        do {
            System.out.println("\n===== 🧑‍💼 Managers Management 🧑‍💼 =====");
            System.out.println("➔ [1] 🧑‍💼 View All Managers");
            System.out.println("➔ [2] 🔍 Search Manager");
            System.out.println("➔ [3] 👥 Add New Manager");
            System.out.println("➔ [4] ✏️ Update Manager Information");
            System.out.println("➔ [5] ❌ Delete Manager");
            System.out.println("➔ [6] 🚪 Exit");
            choice = ViewUtility.enterChoice(choice);
            switch (choice){
                case 1:
                    handleViewManagers();
                    break;
                case 2:
                    handleSearchManager();
                    break;
                case 3:
                    handleAddManager();
                    break;
                case 4:
                    handleUpdateManager();
                    break;
                case 5:
                    handleDeleteManager();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        }while(choice != 6);

    }

    private void handleViewManagers() {
        System.out.println("\n=== 🧑‍💼 View All Managers ===");
        Map<UUID, Manager> managers = managerController.getAllManagers();

        if (managers.isEmpty()) {
            System.out.println("⚠️ No managers found.");
        } else {
            for (Manager manager : managers.values()) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("  Name: " + manager.getName());
                System.out.println("  Email: " + manager.getEmail());
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.println(" ");
            }
        }
    }

    private void handleSearchManager() {
        System.out.println("\n=== 🔍 Search Manager ===");
        System.out.print("📧 Enter the email of the manager to search: ");
        String query = scanner.nextLine();

        // Search for the manager by email
        Map<UUID, Manager> managers = managerController.searchManagers(query);

        if (!managers.isEmpty()) {
            System.out.println("\n🔍 Search Results:");
            for (Map.Entry<UUID, Manager> entry : managers.entrySet()) {
                Manager manager = entry.getValue();
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("  Name: " + manager.getName());
                System.out.println("  Email: " + manager.getEmail());
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.println(" ");
            }
        } else {
            System.out.println("❗ No manager found with the provided email: " + query);
        }
    }


    private void handleAddManager() {
        System.out.println("\n=== 🧑‍💼  Add New Manager ===");

        // Prompt for Manager's name and validate it
        System.out.print("📝 Enter the manager's name: ");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (UserValidation.isValidName(name)) {
                break;
            }
            System.out.println("❗ Invalid name. Please enter a valid name.");
            System.out.print("📝 Re-enter the manager's name: ");
        }

        // Prompt for Manager's email and validate it
        System.out.print("📧 Enter the manager's email: ");
        String email;
        while (true) {
            email = scanner.nextLine();
            if (!UserValidation.isValidEmail(email)) {
                System.out.println("❗ Invalid email format. Please enter a valid email.");
                System.out.print("📧 Re-enter the manager's email: ");
                break;
            }else if(UserValidation.emailExist(email)){
                System.out.println("❗Email already exist. Please enter a valid email.");
                System.out.print("📧 Re-enter the manager's email: ");
            }else{
                break;
            }

        }


        // Prompt for Manager's password and validate it
        System.out.print("🔒 Enter the manager's password: ");
        String password;
        while (true) {
            password = scanner.nextLine();
            if (UserValidation.isValidPassword(password)) {
                break;
            }
            System.out.println("❗ Password must contain at least one letter and one number.");
            System.out.print("🔒 Re-enter the manager's password: ");
        }

        // Create a new Manager object with the provided details
        Manager newManager = new Manager(name, email, password);

       managerController.addManager(newManager);

    }


    private void handleUpdateManager() {
        System.out.println("\n=== ✏️ Update Manager Information ===");

        // Prompt the user to enter the email of the manager to update
        System.out.print("📧 Enter the email of the manager to update: ");
        String email = scanner.nextLine();

        // Search for the manager by email
        Optional<Manager> managerOptional = managerController.getManagerByEmail(email);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            System.out.println("\nCurrent Name: " + manager.getName());
            System.out.print("📝 Enter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            newName = newName.isEmpty() ? manager.getName() : newName;
            // Validate the new name
            while (!UserValidation.isValidName(newName)) {
                System.out.println("❗ Invalid name format. Please enter a valid name.");
                System.out.print("📝 Re-enter new name: ");
                newName = scanner.nextLine();
            }

            System.out.println("\nCurrent Name: " + manager.getEmail());
            System.out.print("📝 Enter new email (press Enter to keep current): ");
            String newEmail = scanner.nextLine();
            newEmail = newEmail.isEmpty() ? manager.getEmail() : newEmail;

            // Validate the new name
            while (!UserValidation.isValidEmail(newEmail)) {
                if (!newEmail.equals(manager.getEmail()) && UserValidation.emailExist(newEmail)) {
                    System.out.println("❗ Email already exists. Please enter a different email.");
                } else {
                    System.out.println("❗ Invalid email format. Please enter a valid email.");
                    System.out.print("📧 Re-enter new email: ");
                    newEmail = scanner.nextLine();
                }
            }

            // Prompt for a new password
            System.out.println("\nCurrent password: " +  manager.getPassword());
            System.out.print("🔒 Enter new password (press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if(!newPassword.isEmpty()){
                while (!UserValidation.isValidPassword(newPassword)) {
                    System.out.println("❗ Invalid password format. Please enter a valid password.");
                    System.out.print("📝 Re-enter new password: ");
                    newPassword = scanner.nextLine();
                }
                manager.setPassword(newPassword);
            }else{
                manager.setPassword("");
            }
            // Update manager information
            manager.setName(newName);
            manager.setEmail(newEmail);
             managerController.updateManager(manager);
        } else {
            System.out.println("❗ No manager found with the provided email: " + email);
        }
    }



    private void handleDeleteManager() {
        System.out.println("\n=== ❌ Delete Manager ===");

        System.out.print("📧 Enter the email of the manager to delete: ");
        String email = scanner.nextLine();

        Optional<Manager> managerOptional = managerController.getManagerByEmail(email);

        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();

            System.out.println("\nAre you sure you want to delete the following manager?");
            System.out.println("🆔 ID: " + manager.getId());
            System.out.println("👤 Name: " + manager.getName());
            System.out.println("📧 Email: " + manager.getEmail());
            System.out.print("⚠️ Type 'yes' to confirm deletion: ");
            String confirmation = scanner.nextLine();

            // Confirm deletion
            if (confirmation.equalsIgnoreCase("yes")) {
                // Delete the manager via the controller
                managerController.deleteManager(manager.getId());

            } else {
                System.out.println("❌ Deletion canceled. The manager was not deleted.");
            }
        } else {
            System.out.println("❗ No manager found with the provided email: " + email);
        }
    }








}
