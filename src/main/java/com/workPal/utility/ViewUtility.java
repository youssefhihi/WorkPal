package com.workPal.utility;

import java.util.Scanner;

public class ViewUtility {

    private static final Scanner scanner = new Scanner(System.in);

    public static Integer enterChoice(int choice){
        try {
            System.out.print("👉 Select your option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch (Exception e){
            scanner.nextLine();
        }
        return  choice;
    }

}
