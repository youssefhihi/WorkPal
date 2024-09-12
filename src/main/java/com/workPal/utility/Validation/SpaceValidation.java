package com.workPal.utility.Validation;
import com.workPal.controller.SpaceController;
import com.workPal.model.Space;
import com.workPal.model.Type;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SpaceValidation {
    private static final SpaceController spaceController = new SpaceController();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s]{3,50}$");  // Allows letters, numbers, and spaces; length between 3-50 characters

    /**
     * Checks if a space with the same name already exists in the system.
     *
     * @param name The name of the space to check.
     * @return True if the space exists, otherwise false.
     */
    public static boolean spaceNameExists(String name) {
        Optional<Space> isExit = spaceController.getSpaceByName(name);
         return isExit.isPresent();
    }

    /**
     * Validates the space's name.
     *
     * @param name The name to validate.
     * @return True if the name is valid, otherwise false.
     */
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }
    public static boolean isValidDesc(String desc) {
        return desc != null && desc.length() > 5;
    }

    /**
     * Validates the space's location.
     *
     * @param location The location to validate.
     * @return True if the location is valid, otherwise false.
     */
    public static boolean isValidLocation(String location) {
        return location != null ;
    }

    /**
     * Validates the space's capacity.
     *
     * @param capacity The capacity to validate.
     * @return True if the capacity is positive, otherwise false.
     */
    public static int isValidCapacity(int capacity) {
        System.out.print("🔢 Enter the capacity of the space: ");
        while(capacity <= 0){
            try {
                capacity = scanner.nextInt();
               scanner.nextLine();
            }catch (Exception e){
                System.out.println("❗ Invalid capacity. Please enter a positive integer.");
                System.out.print("🔢 Re-enter new capacity: ");
                scanner.nextLine();
                capacity = 0;
            }
        }
        return  capacity;
    }

    /**
     * Validates the index for the given list of types and returns the corresponding Type object if the index is valid.
     *
     * @param types The list of Type objects.
     * @param index The index provided by the user.
     * @return The Type object if the index is valid; otherwise, returns null.
     */
    public static Type isValidType(List<Type> types, int index) {
        if (index >= 0 && index < types.size()) {
            return types.get(index);
        } else {
            System.out.println("❗ Invalid index. Please enter a valid index from 0 to " + (types.size() - 1) + ".");
            return null;
        }
    }
}



