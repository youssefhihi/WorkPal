package com.workPal.view.ManagerUI;

import com.workPal.controller.AdditionalServiceController;
import com.workPal.controller.SpaceController;
import com.workPal.controller.TypeController;
import com.workPal.model.Service;
import com.workPal.model.Manager;
import com.workPal.model.Space;
import com.workPal.model.Type;
import com.workPal.utility.Validation.SpaceValidation;
import com.workPal.utility.ViewUtility;

import java.util.*;

public class SpaceUi {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SpaceController spaceController = new SpaceController();
    private  static final TypeController typeController = new TypeController();
    private static final AdditionalServiceController serviceController = new AdditionalServiceController();

    private Manager managerAuth;
    public SpaceUi(Manager manager){
        this.managerAuth = manager;
    }

    public void spaceUiRun(){
        int choice = 0 ;
        do{
            System.out.println("\n===== Welcome to "+ managerAuth.getName()+"! to 🏢 Spaces Management 🏢 =====");
            System.out.println("➔ [1] 🏢 View All Spaces");
            System.out.println("➔ [2] 🔍 Search Space");
            System.out.println("➔ [3] ➕ Add New Space");
            System.out.println("➔ [4] ✏️ Update Space Information");
            System.out.println("➔ [5] ❌ Delete Space");
            System.out.println("➔ [6] 🚪 Exit");
            choice = ViewUtility.enterChoice(choice);
            switch(choice){
                case 1:
                    handleViewSpaces();
                    break;
                case 2:
                    handleSearchSpace();
                    break;
                case 3:
                    handleAddSpace();
                    break;
                case 4:
                    handleUpdateSpace();
                    break;
                case 5:
                    handleDeleteSpace();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");

            }
        }while(choice != 7);
    }



    private void handleViewSpaces() {
        System.out.println("\n=== 🏢 View All Spaces ===");
        Map<UUID, Space> spaces = spaceController.getManagerSpaces(managerAuth);

        if (spaces.isEmpty()) {
            System.out.println("⚠️ No spaces found.");
        } else {
            for (Space space : spaces.values()) {
                System.out.println("╔═══════════════════════════════════════════════════╗");
                System.out.println("  Name : " + space.getName());
                System.out.println("  Description : " + space.getDescription());
                System.out.println("  Location :" + space.getLocation());
                System.out.println("  Capacity :" + space.getCapacity());
                System.out.println("  Type :" + space.getType().getName());
                System.out.println("  Additional Services: ");
                                for (Service service : space.getServices().values()) {
                System.out.print("    -" + service.getName() );
                                }
                System.out.println("╚═══════════════════════════════════════════════════╝");
                System.out.println(" ");
            }
        }
    }



    private void handleSearchSpace() {
        System.out.println("\n=== 🔍 Search Space ===");
        System.out.print("🏢  Enter  to search: ");
        String query = scanner.nextLine();

        Map<UUID, Space> spaces = spaceController.searchSpaces(query);

        if (!spaces.isEmpty()) {
            System.out.println("\n🔍 Search Results:");
            for (Map.Entry<UUID, Space> entry : spaces.entrySet()) {
                Space space = entry.getValue();
                System.out.println("╔═══════════════════════════════════════════════════╗");
                System.out.println("  Name : " + space.getName());
                System.out.println("  Description : " + space.getDescription());
                System.out.println("  Location :" + space.getLocation());
                System.out.println("  Capacity :" + space.getCapacity());
                System.out.println("  Type :" + space.getType().getName());
                System.out.println("  Additional Services: ");
                for (Service service : space.getServices().values()) {
                    System.out.print("    -" + service.getName() );
                }
                System.out.println("╚═══════════════════════════════════════════════════╝");
                System.out.println(" ");
            }
        } else {
            System.out.println("❗ No space found with the provided input: " + query);
        }
    }

    public void handleAddSpace() {
        System.out.println("\n===== 🏢 Add New Space 🏢 =====");

        // Get and validate space name
        System.out.print("Enter the name of the space: ");
        String name = scanner.nextLine();
        while (!SpaceValidation.isValidName(name)) {
            System.out.print("❗ Invalid name. Please enter a valid space name: ");
            name = scanner.nextLine();
        }

        // Get and validate space description
        System.out.print("Enter the description of the space: ");
        String desc = scanner.nextLine();
        while (!SpaceValidation.isValidDesc(desc)) {
            System.out.print("❗ Invalid description. Please enter a valid space description: ");
            desc = scanner.nextLine();
        }

        // Retrieve and select space type
        List<Type> types = typeController.getAllTypes();
        if (types.isEmpty()) {
            System.out.println("❗ No available types to assign. Please add types before adding a space.");
            return;
        }
        System.out.println("Available Types:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println("[" + i + "] " + types.get(i).getName());
        }
        System.out.print("Select the type index for the space: ");
        int index = -1;
        Type selectedType = null;
        while (selectedType == null) {
            try {
                index = Integer.parseInt(scanner.nextLine().trim());
                selectedType = SpaceValidation.isValidType(types, index);
            } catch (NumberFormatException e) {
                System.out.print("❗ Invalid input. Please enter a number corresponding to the type index: ");
            }
        }

        // Get and validate space location
        System.out.print("Enter the location of the space: ");
        String location = scanner.nextLine();
        while (!SpaceValidation.isValidLocation(location)) {
            System.out.print("❗ Invalid location. Please enter a valid location: ");
            location = scanner.nextLine();
        }

        // Get and validate space capacity
        int capacity = 0;
        System.out.print("Enter the capacity of the space: ");
        while (true) {
            try {
                capacity = Integer.parseInt(scanner.nextLine());
                if (capacity >= 0) {
                    break;
                } else {
                    System.out.print("❗ Invalid capacity. Please enter a valid capacity: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("❗ Invalid input. Please enter a number for the capacity: ");
            }
        }

        // Retrieve available services
        List<Service> services = serviceController.getAllServices();
        if (services.isEmpty()) {
            System.out.println("❗ No available services to assign. Please add services before adding a space.");
            return;
        }
        System.out.println("Available Services:");
        for (int i = 0; i < services.size(); i++) {
            System.out.println("[" + i + "] " + services.get(i).getName());
        }

        Map<UUID, Service> selectedServices = new HashMap<>();
        boolean validInput = false;
        while(!validInput) {
            System.out.println("Select the indices of the services to associate with the space (comma-separated):");
            String[] indices = scanner.nextLine().split(",");
            for (String indexStr : indices) {
                try {
                    int serviceIndex = Integer.parseInt(indexStr.trim());
                    if (serviceIndex >= 0 && serviceIndex < services.size()) {
                        Service selectedService = services.get(serviceIndex);
                        selectedServices.put(selectedService.getId(), selectedService);
                        validInput = true;
                    } else {
                        System.out.println("❗ Invalid service index: " + serviceIndex);
                        validInput = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❗ Invalid input for service index: " + indexStr);
                    validInput = false;
                }
            }
        }

        Space newSpace = new Space(name, desc, selectedType, location, capacity, managerAuth, selectedServices);

        // Add the space through the controller
        spaceController.addSpace(newSpace);
    }


    private void handleUpdateSpace() {
        System.out.println("\n=== ✏️ Update Space Information ===");

        System.out.print("🏢 Enter the name of the space to update: ");
        String name = scanner.nextLine();

        // Search for the space by name
        Optional<Space> spaceOptional = spaceController.getSpaceByName(name);
        if (spaceOptional.isPresent()) {
            Space space = spaceOptional.get();

            System.out.println("\nCurrent Name: " + space.getName());
            System.out.print("📝 Enter new name (press Enter to keep current): ");
            String newName = scanner.nextLine();
            newName = newName.isEmpty() ? space.getName() : newName;

            // Validate the new name
            while (!SpaceValidation.isValidName(newName)) {
                System.out.println("❗ Invalid name format. Please enter a valid name.");
                System.out.print("📝 Re-enter new name: ");
                newName = scanner.nextLine();
            }

            System.out.println("\nCurrent Description: " + space.getDescription());
            System.out.print("📝 Enter new description (press Enter to keep current): ");
            String newDescription = scanner.nextLine();
            newDescription = newDescription.isEmpty() ? space.getDescription() : newDescription;

            System.out.println("\nCurrent Location: " + space.getLocation());
            System.out.print("📝 Enter new location (press Enter to keep current): ");
            String newLocation = scanner.nextLine();
            newLocation = newLocation.isEmpty() ? space.getLocation() : newLocation;

            System.out.println("\nCurrent Capacity: " + space.getCapacity());
            System.out.print("🔢 Enter new capacity (press Enter to keep current): ");
            String capacityInput = scanner.nextLine();
            Integer newCapacity = capacityInput.isEmpty() ? space.getCapacity() : Integer.parseInt(capacityInput);

            // Validate the new capacity
            while (newCapacity <= 0) {
                System.out.println("❗ Invalid capacity. Please enter a positive integer.");
                System.out.print("🔢 Re-enter new capacity: ");
                capacityInput = scanner.nextLine();
                newCapacity = capacityInput.isEmpty() ? space.getCapacity() : Integer.parseInt(capacityInput);
            }

            List<Type> types = typeController.getAllTypes();
            if (types.isEmpty()) {
                System.out.println("❗ No available types to assign. Please add types before adding a space.");
                return;
            }
            System.out.println("\nCurrent Type: " + space.getType().getName());
            System.out.print("🔢 Enter new type : ");
            for (int i = 0; i < types.size(); i++) {
                System.out.println("[" + i + "] " + types.get(i).getName());
            }

            System.out.print("Select the type index for the space: ");
            int index = -1;
            Type selectedType = null;
            while (selectedType == null) {
                try {
                    index = Integer.parseInt(scanner.nextLine().trim());
                    selectedType = SpaceValidation.isValidType(types, index);
                } catch (NumberFormatException e) {
                    System.out.print("❗ Invalid input. Please enter a number corresponding to the type index: ");
                }
            }
            // Retrieve available services
            List<Service> services = serviceController.getAllServices();
            if (services.isEmpty()) {
                System.out.println("❗ No available services to assign. Please add services before adding a space.");
                return;
            }
            System.out.println("Available Services:");
            for (int i = 0; i < services.size(); i++) {
                System.out.println("[" + i + "] " + services.get(i).getName());
            }
            Map<UUID, Service> selectedServices = new HashMap<>();
            boolean validInput = false;
            while(!validInput) {
                System.out.println("Select the indices of the services to associate with the space (comma-separated):");
                String[] indices = scanner.nextLine().split(",");
                for (String indexStr : indices) {
                    try {
                        int serviceIndex = Integer.parseInt(indexStr.trim());
                        if (serviceIndex >= 0 && serviceIndex < services.size()) {
                            Service selectedService = services.get(serviceIndex);
                            selectedServices.put(selectedService.getId(), selectedService);
                            validInput = true;
                        } else {
                            System.out.println("❗ Invalid service index: " + serviceIndex);
                            validInput = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❗ Invalid input for service index: " + indexStr);
                        validInput = false;
                    }
                }
            }
            // Update space information
            space.setName(newName);
            space.setDescription(newDescription);
            space.setLocation(newLocation);
            space.setCapacity(newCapacity);
            space.setType(selectedType);
            space.setServices(selectedServices);

            spaceController.updateSpace(space,managerAuth);
        } else {
            System.out.println("❗ No space found with the provided name: " + name);
        }
    }


    private void handleDeleteSpace() {
        System.out.println("\n=== ❌ Delete Space ===");

        System.out.print("📧 Enter the name of the space to delete: ");
        String spaceName = scanner.nextLine();

        Optional<Space> spaceOptional = spaceController.getSpaceByName(spaceName);

        if (spaceOptional.isPresent()) {
            Space space = spaceOptional.get();

            System.out.println("\nAre you sure you want to delete the following space?");
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.println("  Name : " + space.getName());
            System.out.println("  Description : " + space.getDescription());
            System.out.println("  Location :" + space.getLocation());
            System.out.println("  Capacity :" + space.getCapacity());
            System.out.println("  Type :" + space.getType().getName());
            System.out.println("╚═══════════════════════════════════════════════════╝");
            System.out.print("⚠️ Type 'yes' to confirm deletion: ");
            String confirmation = scanner.nextLine();

            // Confirm deletion
            if (confirmation.equalsIgnoreCase("yes")) {
                // Delete the space via the controller
                spaceController.deleteSpace(space, managerAuth);

            } else {
                System.out.println("❌ Deletion canceled. The manager was not deleted.");
            }
        } else {
            System.out.println("❗ No space found with the provided email: " + spaceName);
        }
    }
}
