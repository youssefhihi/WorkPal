package com.workPal.view.Auth;

import com.workPal.controller.MemberController;
import com.workPal.controller.UserController;
import com.workPal.enums.Role;
import com.workPal.model.Admin;
import com.workPal.model.Manager;
import com.workPal.model.Member;
import com.workPal.model.User;
import com.workPal.utility.Validation.UserValidation;
import com.workPal.utility.ViewUtility;
import com.workPal.view.Admin.AdminMain;
import com.workPal.view.ManagerUI.ManagerMain;
import com.workPal.view.MemberUI.MemberMain;

import java.util.Optional;
import java.util.Scanner;

public class Auth {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MemberController memberController = new MemberController();
    private static final UserController userController = new UserController();

    public static void runAuth(){
        int choice = 0;

   do{
        System.out.println("\n===== 🌐 WorkPal Auth Menu 🌐 =====");
        System.out.println("➔ [1] Create New Account");
        System.out.println("➔ [2] Login");
        System.out.println("➔ [3] Forget Password");
        System.out.println("➔ [4] Exit");
        choice = ViewUtility.enterChoice(choice);
        switch (choice){
            case 1:
                handleRegistration();
                break;
            case 2:
                handleLogin();
                break;
            case 3:
                handleForgetPassword();
                break;
            default:
                System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                break;
        }

    }while(choice != 4);

}



    private static void handleRegistration() {
        System.out.println("\n=== Register ===");

        // Validate name input
        String name;
        while (true) {
            System.out.print("📝 Enter your name: ");
            name = scanner.nextLine();
            if (UserValidation.isValidName(name)) {
                break;
            }
            System.out.println("Invalid name. Please enter a valid name.");
        }

        // Validate phone number input
        String phoneNumber;
        while (true) {
            System.out.print("📞 Enter your phone number: +212 ");
            phoneNumber = scanner.nextLine();
            if (UserValidation.isValidPhoneNumber(phoneNumber)) {
                break;
            }
            System.out.println("Invalid phone number. Please enter a valid phone number.");
        }

        // Validate email input
        String email;
        while (true) {
            System.out.print("📧 Enter your email: ");
            email = scanner.nextLine();
            if (UserValidation.isValidEmail(email) && UserValidation.emailExist(email)) {
                break;
            }
            System.out.println("Invalid email Or already used. Please enter a valid email address.");
        }

        // Validate password input
        String password;
        while (true) {
            System.out.print("🔒 Enter your password: ");
            password = scanner.nextLine();
            if (UserValidation.isValidPassword(password)) {
                break;
            }
            System.out.println("Invalid password. Your password must contain at least 8 characters, including a letter and a number.");
        }

        // Create a new member object and add it
        Member newMember = new Member(name, email, password, phoneNumber);
       memberController.addMember(newMember);
    }

    private static void handleLogin() {
        System.out.println("\n=== 🔑 Login ===");
        System.out.print("📧 Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("🔒 Enter your password: ");
        String password = scanner.nextLine();

        Optional<User> userOptional = userController.login(email, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("✅ Login successful! Welcome Back  " + user.getName() + ".");
            switch (user.getRole()) {
                case admin:
                    System.out.println("👑 Role: Admin - You have administrative access.");
                        Admin admin = new Admin(user.getName(), user.getEmail(), user.getPassword());
                        AdminMain adminMain =  new AdminMain(admin);
                        adminMain.runAdminUI();
                    break;
                case manager:
                    System.out.println("📝 Role: Manager - You can manage resources.");
                    Manager manager = new Manager(user.getName(), user.getEmail(), user.getPassword());
                    ManagerMain managerMain =  new ManagerMain(manager);
                    managerMain.runMain();
                    break;
                case member:
                    System.out.println("👤 Role: Member - You have standard member access.");
                       Optional<Member> member = memberController.getMemberById(user.getId());
                       MemberMain memberMain =  new MemberMain(member.get());
                       memberMain.runMain();
                    break;
                default:
                    System.out.println("⚠️ Unknown role. Access level is limited.");
                    break;
            }
        } else {
            System.out.println("❌ Login failed.");
        }
    }



    private static void handleForgetPassword() {
        System.out.println("\n=== 🔒 Forgot Password ===");
        System.out.print("📧 Enter your email: ");
        String email = scanner.nextLine();
        userController.forgetPassword(email);
    }





}
