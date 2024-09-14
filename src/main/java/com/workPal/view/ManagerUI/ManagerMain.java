package com.workPal.view.ManagerUI;

import com.workPal.model.Manager;
import com.workPal.utility.ViewUtility;

import java.util.Scanner;

public class ManagerMain {
    private static Manager managerAuth;
    private static final Scanner scanner = new Scanner(System.in);

    public ManagerMain(Manager manager) {
        ManagerMain.managerAuth = manager;
    }

    public void runMain() {
        int choice = 0;
        do {
            System.out.println("\n===== 🎉 Hello, " + managerAuth.getName() + "! Welcome to WorkPal 🎉 =====");
            System.out.println("➔ [1] 🔧 Profile Management");
            System.out.println("➔ [2] 🏢 Space Management");
            System.out.println("➔ [3] 📅 Events Management");
            System.out.println("➔ [4] 🌍 Subscription Management");
            System.out.println("➔ [5] 🚪 Logout");
            choice = ViewUtility.enterChoice(choice);
            switch (choice) {
                case 1:
//                    profileManagement();
                    break;
                case 2:
                    spaceManagement();
                    break;
                case 3:
                    eventManagement();
                    break;
                case 4:
                    subscriptionManagement();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        } while (choice != 5);
    }

//    public void profileManagement() {
//        ProfileUI profileUI = new ProfileUI(managerAuth);
//        profileUI.runProfileUI();
//    }

    public void spaceManagement() {
        SpaceUi spaceUi = new SpaceUi(managerAuth);
        spaceUi.spaceUiRun();
    }

    public void eventManagement() {
        // Implement event management logic here
        System.out.println("Event Management feature is under construction.");
    }

    public void subscriptionManagement() {
       new SubscriptionUI(managerAuth).subscriptionMain();
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic if needed
    }
}
