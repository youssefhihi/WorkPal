package com.workPal.view.MemberUI;

import com.workPal.model.Member;
import com.workPal.utility.ViewUtility;
import com.workPal.view.MemberUI.Profile.ProfileUI;

import java.util.Scanner;

public class MemberMain {
    private static Member memberAuth;
    private static final Scanner scanner = new Scanner(System.in);

    public MemberMain(Member member){
        MemberMain.memberAuth = member;
    }


    public void  runMain(){
        int choice = 0;

        do{
            System.out.println("\n===== 🎉 Hello, " + memberAuth.getName() + "! Welcome to WorkPal 🎉 =====");
            System.out.println("➔ [1] 🔧 Profile Management");
            System.out.println("➔ [2] 🏢 Reservation Management");
            System.out.println("➔ [3] 💳 Subscriptions Management");
            System.out.println("➔ [4] 📅 Events Management");
            System.out.println("➔ [5] 🌍 Discover Co-Working Spaces");
            System.out.println("➔ [6] 🚪 Logout");
            choice = ViewUtility.enterChoice(choice);
            switch (choice){
                case 1:
                    profileManagement();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }

        }while(choice != 7);

    }

    public void profileManagement(){
        ProfileUI profileUI = new ProfileUI(memberAuth);
        profileUI.runProfileUI();
    }
}
