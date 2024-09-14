package com.workPal.view.ManagerUI;

import com.workPal.controller.AdditionalServiceController;
import com.workPal.model.Service;
import com.workPal.utility.ViewUtility;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ServiceUI {
    private final AdditionalServiceController additionalServiceController  = new AdditionalServiceController();
    private final Scanner scanner = new Scanner(System.in);


    public void runAdditionalServiceUI() {
        int choice;
        do {
            System.out.println("\n=== 🛠️ Additional Services Management 🛠️ ===");
            System.out.println("➔ [1] 📋 View All Services");
            System.out.println("➔ [2] 🔍 Search Service by Name");
            System.out.println("➔ [3] ➕ Add New Service");
            System.out.println("➔ [4] ✏️ Update Existing Service");
            System.out.println("➔ [5] ❌ Delete Service");
            System.out.println("➔ [6] 🚪 Exit");

            choice = ViewUtility.enterChoice(0);

            switch (choice) {
                case 1:
                    viewAllServices();
                    break;
                case 2:
                    searchServiceByName();
                    break;
                case 3:
                    addNewService();
                    break;
                case 4:
                    updateService();
                    break;
                case 5:
                    deleteService();
                    break;
                case 6:
                    System.out.println("🚪 Exiting Additional Services Management.");
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        } while (choice != 6);
    }

    private void viewAllServices() {
        List<Service> services = additionalServiceController.getAllServices();
        if (services.isEmpty()) {
            System.out.println("📭 No additional services found.");
        } else {
            System.out.println("\n=== 📋 All Additional Services ===");
            for (Service service : services) {
                System.out.println("╔══════════════════════════════════════════╗");
                System.out.println("    Name: " + service.getName());
                System.out.println("    Price: " + service.getPrice());
                System.out.println("╚══════════════════════════════════════════╝");
                System.out.println(" ");
            }
        }
    }

    private void searchServiceByName() {
        System.out.print("🔍 Enter the name of the service to search: ");
        String name = scanner.nextLine();
        Optional<Service> serviceOptional = additionalServiceController.getServiceByName(name);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            System.out.println("\n=== Service Details ===");
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("    Name: " + service.getName());
            System.out.println("    Price: " + service.getPrice());
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println(" ");
        } else {
            System.out.println("❗ No service found with the provided name.");
        }
    }

    private void addNewService() {
        System.out.print("📝 Enter service name : ");
        String name = scanner.nextLine();
        while (name.length() < 3) {
            System.out.println("❗ Service name cannot be empty and must be at least 3 characters long.");
            System.out.print("📝 Re-enter service name: ");
            name = scanner.nextLine();
        }

        System.out.print("💲 Enter service price: ");
        Integer price;
        while (true) {
            try {
                price = Integer.parseInt(scanner.nextLine());
                if (price > 0) {
                    break;
                } else {
                    System.out.println("❗ Price must be a positive integer.");
                    System.out.print("💲 Re-enter service price: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Invalid input. Please enter a valid number.");
                System.out.print("💲 Re-enter service price: ");
            }
        }

        // Ensure the name is unique
        Optional<Service> existingService = additionalServiceController.getServiceByName(name);
        if (existingService.isPresent()) {
            System.out.println("❗ A service with this name already exists.");
        } else {
            Service service = new Service(name, price);
            additionalServiceController.addService(service);
        }
    }

    private void updateService() {
        System.out.print("🔍 Enter the name of the service to update: ");
        String name = scanner.nextLine();
        Optional<Service> serviceOptional = additionalServiceController.getServiceByName(name);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            System.out.println("\nCurrent Details:");
            System.out.println("Name: " + service.getName());
            System.out.println("Price: " + service.getPrice());

            System.out.print("📝 Enter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = service.getName();
            } else if (newName.length() < 3) {
                System.out.println("❗ The name must be at least 3 characters long.");
                System.out.print("📝 Re-enter new name: ");
                newName = scanner.nextLine();
            }

            System.out.print("💲 Enter new price (press Enter to keep current): ");
            String priceStr = scanner.nextLine();
            Integer newPrice;
            if (priceStr.isEmpty()) {
                newPrice = service.getPrice();
            } else {
                try {
                    newPrice = Integer.parseInt(priceStr);
                    if (newPrice <= 0) {
                        System.out.println("❗ Price must be a positive integer.");
                        System.out.print("💲 Re-enter new price: ");
                        priceStr = scanner.nextLine();
                        newPrice = Integer.parseInt(priceStr);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❗ Invalid input. Please enter a valid number.");
                    System.out.print("💲 Re-enter new price: ");
                    priceStr = scanner.nextLine();
                    newPrice = Integer.parseInt(priceStr);
                }
            }

            service.setName(newName);
            service.setPrice(newPrice);

            additionalServiceController.updateService(service);
        } else {
            System.out.println("❗ No service found with the provided name.");
        }
    }

    private void deleteService() {
        System.out.print("❌ Enter the name of the service to delete: ");
        String name = scanner.nextLine();
        Optional<Service> serviceOptional = additionalServiceController.getServiceByName(name);
        if (serviceOptional.isPresent()) {
            Service service = serviceOptional.get();
            System.out.println("\n=== Service Details ===");
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("    Name: " + service.getName());
            System.out.println("    Price: " + service.getPrice());
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println(" ");
            System.out.print("⚠️ Type 'yes' to confirm deletion: ");
            String confirmation = scanner.nextLine();

            // Confirm deletion
            if (confirmation.equalsIgnoreCase("yes")) {
                // Delete the service via the controller
                additionalServiceController.deleteService(service.getId());

            } else {
                System.out.println("❌ Deletion canceled. The service was not deleted.");
            }
        } else {
            System.out.println("❗ No service found with the provided name.");
        }
    }
}
