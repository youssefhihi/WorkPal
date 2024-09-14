package com.workPal.view.ManagerUI;

import com.workPal.controller.ManagerController;
import com.workPal.controller.UserController;
import com.workPal.model.Manager;
import com.workPal.utility.Validation.UserValidation;
import com.workPal.utility.ViewUtility;

import java.util.Scanner;

public class ProfileUI {
    private static Manager managerAuth;
    private static final Scanner scanner = new Scanner(System.in);
    private static final ManagerController managerController = new ManagerController();
    private static  final UserController userController = new UserController();
    public ProfileUI(Manager manager){
        ProfileUI.managerAuth = manager;
    }

    public void runProfileUI(){
        int choice = 0;
        do{
            System.out.println("\n===== 🛠️ Manage Your Profile, " + managerAuth.getName() + "! 🛠️ =====");
            System.out.println("➔ [1] ✏️ Edit Profile Information");
            System.out.println("➔ [2] 🔒 Change Password");
            System.out.println("➔ [3] ❌ Exit to Main Menu");
            choice = ViewUtility.enterChoice(choice);
            switch (choice){
                case 1:
                    handleEditProfile();
                    break;
                case 2:
                    handleUpdatePassword();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        }while(choice != 3);

    }


    private void handleEditProfile() {
        System.out.println("\n=== ✏️ Edit Profile Information ===");

        System.out.println("📄 Current Profile Information:");
        System.out.println("👤 Name: " + managerAuth.getName());
        System.out.println("📧 Email: " + managerAuth.getEmail());

        System.out.print("\n📝 Enter new name (press Enter to keep current): ");
        String newName = scanner.nextLine();
        newName = newName.isEmpty() ? managerAuth.getName() : newName;

        while (!UserValidation.isValidName(newName)) {
            System.out.println("❗ Invalid name format. Please enter a valid name.");
            System.out.print("📝 Re-enter your name: ");
            newName = scanner.nextLine();
            newName = newName.isEmpty() ? managerAuth.getName() : newName;
        }


        System.out.print("📧 Enter new email (press Enter to keep current): ");
        String newEmail = scanner.nextLine();
        newEmail = newEmail.isEmpty() ? managerAuth.getEmail() : newEmail;

        while (!UserValidation.isValidEmail(newEmail) && UserValidation.emailExist(newEmail)) {
            System.out.println("❗ Invalid email format or email already exist. Please enter a valid email.");
            System.out.print("📧 Re-enter your email: ");
            newEmail = scanner.nextLine();
            newEmail = newEmail.isEmpty() ? managerAuth.getEmail() : newEmail;
        }


        managerAuth.setName(newName);
        managerAuth.setEmail(newEmail);
        managerAuth.setPassword("");

        managerController.updateManager(managerAuth);
    }

    private static void handleUpdatePassword() {
        System.out.println("\n=== 🔒 Change Password ===");

        System.out.print("📝 Enter your current password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("🆕 Enter new password: ");
        String newPassword = scanner.nextLine();

        // Validate the new password
        while (!UserValidation.isValidPassword(newPassword)) {
            System.out.println("❗ Password must contain at least one letter and one number.");
            System.out.print("🆕 Re-enter new password: ");
            newPassword = scanner.nextLine();
        }

        System.out.print("🔄 Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Passwords do not match. Please try again.");
            return;
        }
        String passwordAuth = managerAuth.getPassword();
        managerAuth.setPassword(currentPassword);
        Boolean isUpdated = userController.updatePassword(managerAuth,newPassword);

        if (isUpdated) {
            managerAuth.setPassword(newPassword);
        }else{
            managerAuth.setPassword(passwordAuth);
        }
    }

}
