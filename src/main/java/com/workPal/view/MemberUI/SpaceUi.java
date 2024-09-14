package com.workPal.view.MemberUI;

import com.workPal.controller.SpaceController;
import com.workPal.model.Manager;
import com.workPal.model.Member;
import com.workPal.model.Service;
import com.workPal.model.Space;
import com.workPal.utility.ViewUtility;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class SpaceUi {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SpaceController spaceController = new SpaceController();

    private final Member memberAuth;
    public SpaceUi(Member member){
        this.memberAuth = member;
    }


    public void spaceUIMain(){
        int choice = 0 ;
        do{
            System.out.println("\n===== Welcome "+ memberAuth.getName()+"! to 🏢 Spaces Discovering 🏢 =====");
            System.out.println("➔ [1] 🏢 View All Spaces");
            System.out.println("➔ [2] 🔍 Search Space");
            System.out.println("➔ [4] 🚪 Exit");
            choice = ViewUtility.enterChoice(choice);
            switch(choice){
                case 1:
                    handleViewSpaces();
                    break;
                case 2:
                    handleSearchSpace();
                    break;
                case 3:
                    handleDetailsSpace();
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
            }
        }while(choice != 4);
    }


    private void handleViewSpaces() {
        System.out.println("\n=== 🏢 View All Spaces ===");
        Map<UUID, Space> spaces = spaceController.getAllSpaces();

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
    private void handleDetailsSpace(){
        System.out.print("🏢 Enter the name of the space to update: ");
        String name = scanner.nextLine();

        // Search for the space by name
        Optional<Space> spaceOptional = spaceController.getSpaceByName(name);
        if (spaceOptional.isPresent()) {
            Space space = spaceOptional.get();
            new SpaceDetailsUI(memberAuth, space).SpaceDetailsMain();
        } else {
            System.out.println("❗ No space found with the provided name: " + name);
        }
    }
}