package com.workPal.view.Admin;

import com.workPal.controller.MemberController;
import com.workPal.model.Admin;
import com.workPal.model.Member;
import com.workPal.utility.Validation.UserValidation;
import com.workPal.utility.ViewUtility;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class MemberMngUI {
    private final Scanner scanner = new Scanner(System.in);
    private final MemberController memberController = new MemberController();
    private final Admin adminAuth;

    public MemberMngUI(Admin admin){
        this.adminAuth = admin;
    }
    public void runMemberMngUI() {
        int choice = 0;
        do {
            System.out.println("\n===== 👥 Members Management 👥 =====");
            System.out.println("➔ [1] 👥 View All Members");
            System.out.println("➔ [2] 🔍 Search Member");
            System.out.println("➔ [3] ➕ Add New Member");
            System.out.println("➔ [4] ✏️ Update Member Information");
            System.out.println("➔ [5] ❌ Delete Member");
            System.out.println("➔ [6] 🚪 Logout");
            choice = ViewUtility.enterChoice(choice);
            switch (choice) {
                case 1:
                    handleViewMembers();
                    break;
                case 2:
                    handleSearchMember();
                    break;
                case 3:
                    handleAddMember();
                    break;
                case 4:
                    handleUpdateMember();
                    break;
                case 5:
                    handleDeleteMember();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        } while (choice != 6);
    }

    private void handleViewMembers() {
        System.out.println("\n=== 👥 View All Members ===");
        Map<UUID, Member> members = memberController.getAllMembers();

        if (members.isEmpty()) {
            System.out.println("⚠️ No members found.");
        } else {
            for (Member member : members.values()) {
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("  Name: " + member.getName());
                System.out.println("  Email: " + member.getEmail());
                System.out.println("  Phone Number: " + member.getPhoneNumber());
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.println(" ");
            }
        }
    }

    private void handleSearchMember() {
        System.out.println("\n=== 🔍 Search Member ===");
        System.out.print("📧 Enter the email of the member to search: ");
        String query = scanner.nextLine();

        Map<UUID, Member> members = memberController.searchMembers(query);

        if (!members.isEmpty()) {
            System.out.println("\n🔍 Search Results:");
            for (Map.Entry<UUID, Member> entry : members.entrySet()) {
                Member member = entry.getValue();
                System.out.println("╔═══════════════════════════════════════╗");
                System.out.println("  Name: " + member.getName());
                System.out.println("  Email: " + member.getEmail());
                System.out.println("  Phone Number: " + member.getPhoneNumber());
                System.out.println("╚═══════════════════════════════════════╝");
                System.out.println(" ");
            }
        } else {
            System.out.println("❗ No member found with the provided input: " + query);
        }
    }

    private void handleAddMember() {
        System.out.println("\n=== ➕ Add New Member ===");

        // Prompt for Member's name and validate it
        System.out.print("📝 Enter the member's name: ");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (UserValidation.isValidName(name)) {
                break;
            }
            System.out.println("❗ Invalid name. Please enter a valid name.");
            System.out.print("📝 Re-enter the member's name: ");
        }

        // Prompt for Member's email and validate it
        System.out.print("📧 Enter the member's email: ");
        String email;
        while (true) {
            email = scanner.nextLine();
            if (UserValidation.isValidEmail(email) && !UserValidation.emailExist(email)) {
                break;
            }
            System.out.println("❗ Invalid email format or email already exist. Please enter a valid email.");
            System.out.print("📧 Re-enter the member's email: ");
        }

        // Prompt for Member's phone number and validate it
        System.out.print("📱 Enter the member's phone number: ");
        String phoneNumber;
        while (true) {
            phoneNumber = scanner.nextLine();
            if (UserValidation.isValidPhoneNumber(phoneNumber)) {
                break;
            }
            System.out.println("❗ Invalid phone number format. Please enter a valid phone number.");
            System.out.print("📱 Re-enter the member's phone number: ");
        }

        // Prompt for Member's password and validate it
        System.out.print("🔒 Enter the member's password: ");
        String password;
        while (true) {
            password = scanner.nextLine();
            if (UserValidation.isValidPassword(password)) {
                break;
            }
            System.out.println("❗ Password must contain at least one letter and one number.");
            System.out.print("🔒 Re-enter the member's password: ");
        }

        // Create a new Member object with the provided details
        Member newMember = new Member(name, email,password , phoneNumber);

        memberController.addMember(newMember);
    }

    private void handleUpdateMember() {
        System.out.println("\n=== ✏️ Update Member Information ===");

        // Prompt the user to enter the email of the member to update
        System.out.print("📧 Enter the email of the member to update: ");
        String email = scanner.nextLine();

        // Search for the member by email
        Optional<Member> memberOptional = memberController.getMemberByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // Display current information and prompt for new details
            System.out.println("\nCurrent Name: " + member.getName());
            System.out.print("📝 Enter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            newName = newName.isEmpty() ? member.getName() : newName;

            // Validate the new name
            while (!UserValidation.isValidName(newName)) {
                System.out.println("❗ Invalid name format. Please enter a valid name.");
                System.out.print("📝 Re-enter new name: ");
                newName = scanner.nextLine();
            }

            System.out.println("\nCurrent Email: " + member.getEmail());
            System.out.print("📧 Enter new email (press Enter to keep current): ");
            String newEmail = scanner.nextLine();
            newEmail = newEmail.isEmpty() ? member.getEmail() : newEmail;

            // Validate the new email
            while (!UserValidation.isValidEmail(newEmail)) {
                    if (!newEmail.equals(member.getEmail()) && UserValidation.emailExist(newEmail)) {
                        System.out.println("❗ Email already exists. Please enter a different email.");
                    } else {
                        System.out.println("❗ Invalid email format. Please enter a valid email.");
                        System.out.print("📧 Re-enter new email: ");
                        newEmail = scanner.nextLine();
                    }
            }

            System.out.println("\nCurrent Phone Number: " + member.getPhoneNumber());
            System.out.print("📱 Enter new phone number (press Enter to keep current): ");
            String newPhoneNumber = scanner.nextLine();
            newPhoneNumber = newPhoneNumber.isEmpty() ? member.getPhoneNumber() : newPhoneNumber;

            // Validate the new phone number
            while (!UserValidation.isValidPhoneNumber(newPhoneNumber)) {
                System.out.println("❗ Invalid phone number format. Please enter a valid phone number.");
                System.out.print("📱 Re-enter new phone number: ");
                newPhoneNumber = scanner.nextLine();
            }

            // Prompt for a new password
            System.out.println("\nCurrent password: " +  member.getPassword());
            System.out.print("🔒 Enter new password (press Enter to keep current): ");
            String newPassword = scanner.nextLine();
            if(!newPassword.isEmpty()){
                while (!UserValidation.isValidPassword(newPassword)) {
                    System.out.println("❗ Invalid password format. Please enter a valid password.");
                    System.out.print("📝 Re-enter new password: ");
                    newPassword = scanner.nextLine();
                }
                member.setPassword(newPassword);
            }else{
                member.setPassword("");
            }
            // Update member information
            member.setName(newName);
            member.setEmail(newEmail);
            member.setPhoneNumber(newPhoneNumber);

            // Send updated member to the backend
            memberController.updateMember(member);
        } else {
            System.out.println("❗ No member found with the provided email: " + email);
        }
    }

    private void handleDeleteMember() {
        System.out.println("\n=== ❌ Delete Member ===");

        System.out.print("📧 Enter the email of the member to delete: ");
        String email = scanner.nextLine();

        Optional<Member> memberOptional = memberController.getMemberByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            System.out.println("\nAre you sure you want to delete the following member?");
            System.out.println("👤 Name: " + member.getName());
            System.out.println("📧 Email: " + member.getEmail());
            System.out.println("📱 Phone Number : " + member.getPhoneNumber());
            System.out.print("⚠️ Type 'yes' to confirm deletion: ");
            String confirmation = scanner.nextLine();

            // Confirm deletion
            if (confirmation.equalsIgnoreCase("yes")) {
                // Delete the manager via the controller
                memberController.deleteMember(member.getId());

            } else {
                System.out.println("❌ Deletion canceled. The manager was not deleted.");
            }
        } else {
            System.out.println("❗ No manager found with the provided email: " + email);
        }
    }
}


