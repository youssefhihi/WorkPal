package com.workPal.view.ManagerUI;

import com.workPal.controller.SubscriptionController;
import com.workPal.model.Manager;
import com.workPal.model.Service;
import com.workPal.model.Subscription;
import com.workPal.utility.ViewUtility;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class SubscriptionUI {
    private static Manager managerAuth;
    private static final Scanner scanner = new Scanner(System.in);
    private static final SubscriptionController subscriptionController = new SubscriptionController();

    public SubscriptionUI(Manager manager) {
        SubscriptionUI.managerAuth = manager;
    }

    public void subscriptionMain() {
        int choice = 0;
        do {

            System.out.println("📅 Subscription Management Menu");
            System.out.println("➔ [1] 📋 View Subscriptions Not Accepted Yet");
            System.out.println("➔ [2] ✅ View Accepted Subscriptions");
            System.out.println("➔ [3] ➕ Add Subscription");
            System.out.println("➔ [4] ✏️ Update Subscription");
            System.out.println("➔ [5] ❌ Delete Subscription");
            System.out.println("➔ [6] 🏢 View Subscriptions for a Space");
            System.out.println("➔ [7] 🧑‍🤝‍🧑 View Subscriptions for a Member");
            System.out.println("➔ [8] 🚪 Exit");
            choice = ViewUtility.enterChoice(choice);
            switch (choice) {
                case 1:
                    handleSubscriptionNotAccepted();
                    break;
                case 2:
                    handleSubscriptionAccepted();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
            }

        }while (choice != 8) ;
    }

    private void handleSubscriptionNotAccepted() {
        // Fetch subscriptions that have not been accepted
        Map<UUID, Subscription> subscriptionMap = subscriptionController.getSubscriptionNotAccepted(managerAuth);

        // Check if there are any subscriptions to display
        if (subscriptionMap.isEmpty()) {
            System.out.println("🚫 No subscriptions have been accepted yet.");
            return;
        }

        // Display the subscriptions with indices
        System.out.println("📋 Subscriptions Not Accepted Yet:");
        int index = 1;
        for (Subscription subscription : subscriptionMap.values()) {
            System.out.println("\n🆔 Subscription ID:"  + index );
            System.out.println("⏳ Duration: " + subscription.getDuration() + " " + subscription.getDurationType());
            System.out.println("📅 Start Date: " + subscription.getStartDate());
            System.out.println("👤 Member: ");
            System.out.println("     Name: " + subscription.getMember().getName());
            System.out.println("     Email: " + subscription.getMember().getEmail());
            System.out.println("🏢 Space: " + subscription.getSpace().getName());
            System.out.println("🔧 Services: ");
            for (Service service : subscription.getServices().values()) {
                System.out.println(" - 🛠️ " + service.getName());
            }
            System.out.println("📍 Status: ❗ Not Accepted");
            index++;
        }

        // Prompt user to choose a subscription to accept
        System.out.println("\n💡 Enter the index of the Subscription you want to accept or type 'exit' to return:");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("exit")) {
            System.out.println("🚪 Returning to main menu...");
            return;
        }

        try {
            int selectedIndex = Integer.parseInt(input);

            if (selectedIndex > 0 && selectedIndex <= subscriptionMap.size()) {
                Subscription selectedSubscription = (Subscription) subscriptionMap.values().toArray()[selectedIndex - 1];
                subscriptionController.acceptSubscription(selectedSubscription);
            } else {
                System.out.println("❗ Invalid index. Please enter a number between 1 and " + subscriptionMap.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("❗ Invalid input format. Please enter a valid number.");
        }
    }



    private void handleSubscriptionAccepted(){

    }

}
