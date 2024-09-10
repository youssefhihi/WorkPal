package com.workPal.view.MemberUI.Profile;

import com.workPal.controller.MemberController;
import com.workPal.controller.UserController;
import com.workPal.model.Member;
import com.workPal.utility.Validation.UserValidation;
import com.workPal.utility.ViewUtility;
import com.workPal.view.MemberUI.MemberMain;

import java.util.Scanner;

public class ProfileUI {
    private static Member memberAuth;
    private static final Scanner scanner = new Scanner(System.in);
    private static final MemberController memberController = new MemberController();
    private static  final UserController userController = new UserController();
    public ProfileUI(Member member){
        ProfileUI.memberAuth = member;
    }

    public void runProfileUI(){
        int choice = 0;
        do{
            System.out.println("\n===== 🛠️ Manage Your Profile, " + memberAuth.getName() + "! 🛠️ =====");
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

        // Display current profile information
        System.out.println("📄 Current Profile Information:");
        System.out.println("👤 Name: " + memberAuth.getName());
        System.out.println("📧 Email: " + memberAuth.getEmail());
        System.out.println("📞 Phone Number: " + memberAuth.getPhoneNumber());

        // Prompt user to enter new details
        System.out.print("\n📝 Enter new name (press Enter to keep current): ");
        String newName = scanner.nextLine();
        newName = newName.isEmpty() ? memberAuth.getName() : newName;

        while (!UserValidation.isValidName(newName)) {
            System.out.println("❗ Invalid name format. Please enter a valid name.");
            System.out.print("📝 Re-enter your name: ");
            newName = scanner.nextLine();
            newName = newName.isEmpty() ? memberAuth.getName() : newName;
        }


        System.out.print("📧 Enter new email (press Enter to keep current): ");
        String newEmail = scanner.nextLine();
        newEmail = newEmail.isEmpty() ? memberAuth.getEmail() : newEmail;

        while (!UserValidation.isValidEmail(newEmail)) {
            System.out.println("❗ Invalid email format. Please enter a valid email.");
            System.out.print("📧 Re-enter your email: ");
            newEmail = scanner.nextLine();
            newEmail = newEmail.isEmpty() ? memberAuth.getEmail() : newEmail;
        }

        System.out.print("📞 Enter new phone number (press Enter to keep current): ");
        String newPhoneNumber = scanner.nextLine();
        newPhoneNumber = newPhoneNumber.isEmpty() ? memberAuth.getPhoneNumber() : newPhoneNumber;

        while (!UserValidation.isValidPhoneNumber(newPhoneNumber)) {
            System.out.println("❗ Invalid phone number format. Please enter a valid phone number.");
            System.out.print("📞 Re-enter your phone number: ");
            newPhoneNumber = scanner.nextLine();
            newPhoneNumber = newPhoneNumber.isEmpty() ? memberAuth.getPhoneNumber() : newPhoneNumber;

        }

        // Update member details
        memberAuth.setName(newName);
        memberAuth.setEmail(newEmail);
        memberAuth.setPhoneNumber(newPhoneNumber);

        // Save changes using the update method
        memberController.updateMember(memberAuth);
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

        // Step 3: Confirm the new password
        System.out.print("🔄 Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        // Check if the new password matches the confirmation
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Passwords do not match. Please try again.");
            return;
        }
        String passwordAuth = memberAuth.getPassword();
        // Update the password in the database
        memberAuth.setPassword(currentPassword);
        Boolean isUpdated = userController.updatePassword(memberAuth,newPassword);

        if (isUpdated) {
            memberAuth.setPassword(newPassword);
        }else{
            memberAuth.setPassword(passwordAuth);
        }
    }

}
