package com.workPal.view.Admin;

import com.workPal.controller.ManagerController;
import com.workPal.controller.MemberController;
import com.workPal.model.Admin;
import com.workPal.utility.ViewUtility;

import java.util.Scanner;

public class AdminMain {

    private final Scanner scanner = new Scanner(System.in);
    private final ManagerController managerController = new ManagerController();
    private final MemberController memberController = new MemberController();
    private  final Admin adminAuth;
    public AdminMain(Admin admin){
        this.adminAuth = admin;
    }
    public  void runAdminUI() {
        int choice = 0;
        do {
            System.out.println("\n===== 🛠️ Admin Dashboard, Welcome " + this.adminAuth.getName() + " 🛠️ =====");
            System.out.println("➔ [1] 🧑‍💼 Manage Managers");
            System.out.println("➔ [2] 👥 Manage Members");
            System.out.println("➔ [3] ⚙️ Configure Global Settings");
            System.out.println("➔ [4] 🚪 Logout");
            choice = ViewUtility.enterChoice(choice);
            switch (choice){
                case 1:
                    ManagerMngUI managerMngUI = new ManagerMngUI(this.adminAuth);
                    managerMngUI.runManagerMngUI();
                    break;
                case 2:
                    MemberMngUI memberMngUI = new MemberMngUI(this.adminAuth);
                    memberMngUI.runMemberMngUI();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
                    break;
            }
        }while(choice != 4);

    }
}
