package com.workPal.view.MemberUI;

import com.workPal.controller.FeedbackController;
import com.workPal.controller.SpaceController;
import com.workPal.controller.SubscriptionController;
import com.workPal.model.*;
import com.workPal.utility.Validation.SubscriptionValidation;
import com.workPal.utility.ViewUtility;

import java.util.*;

public class SpaceDetailsUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SpaceController spaceController = new SpaceController();
    private static  final SubscriptionController subscriptionController = new SubscriptionController();
    private  static  final FeedbackController feedbackController = new FeedbackController();
    private final Member memberAuth;
    private  final Space spaceDetails;

    public SpaceDetailsUI(Member member, Space space){
        this.memberAuth = member;
        this.spaceDetails = space;
    }
    public void SpaceDetailsMain(){
        int choice = 0 ;
        do{

            System.out.println("\n===== Welcome "+ memberAuth.getName()+"! to 🏢 Spaces Discovering 🏢 =====");
            System.out.println("➔ [1] 🏢 Reserve this Space ");
            System.out.println("➔ [2] 🔍 show feedbacks ");
            System.out.println("➔ [3] 🚪 write a feedback");
            System.out.println("➔ [4] 🚪 Exit");
            choice = ViewUtility.enterChoice(choice);
            switch(choice){
                case 1:
                    handleReserveSpace();
                    break;
                case 2:

                    break;
                case 3:
                    break;
                default:
                    System.out.println("❗ Invalid choice. Please select a valid option from the menu.");
            }
        }while(choice != 4);
    }

    private void handleWriteFeedback() {
        System.out.println("\n===== 🚪 Write Your Feedback =====");

        // Get the feedback comments from the user
        System.out.print("➔ Enter your feedback comment: ");
        String comment = scanner.nextLine();

        // Validate the comment (you can add more validation if needed)
        if (comment.trim().isEmpty()) {
            System.out.println("❗ Feedback comment cannot be empty.");
            return;
        }

        // Collect feedback information
        Feedback feedback = new Feedback();
        feedback.setComment(comment);
        feedback.setMember(memberAuth);
        feedback.setSpace(spaceDetails);

        // Use the feedbackController to submit the feedback
        boolean success = feedbackController.addFeedback(feedback);

        // Inform the user of the result
        if (success) {
            System.out.println("✅ Your feedback has been submitted successfully.");
        } else {
            System.out.println("❗ There was an error submitting your feedback. Please try again later.");
        }
    }


    private void handleReserveSpace() {
        System.out.println("\n===== 🏢 Reserving Space: " + spaceDetails.getName() + " =====");
        System.out.println("Services map: " + spaceDetails.getServices());

        System.out.print("➔ Enter the duration type (e.g., day, month, year): ");
        String durationType = scanner.nextLine().toLowerCase();
        while (!SubscriptionValidation.isDurationTypeValid(durationType)) {
            System.out.println("❗ Invalid duration type. Please re-enter a valid type (e.g., day, month, year): ");
            durationType = scanner.nextLine().toLowerCase();
        }

        // Validate duration
        System.out.print("➔ How many " + durationType + "(s) do you want to reserve? ");
        int duration = 0;
        while (true) {
            try {
                duration = scanner.nextInt();
                if (duration <= 0) {
                    throw new IllegalArgumentException("Duration must be greater than 0.");
                }
                break;
            } catch (Exception e) {
                System.out.println("❗ Invalid input. Please enter a valid positive number: ");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        System.out.print("➔ Enter the start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        while (!SubscriptionValidation.isDateValid(startDate)) {
            System.out.println("❗ Invalid date format. Please enter the date in the correct format (YYYY-MM-DD): ");
            startDate = scanner.nextLine();
        }



        System.out.println("Available Services:");
        List<Service> servicesList = new ArrayList<>(spaceDetails.getServices().values());
        for (int i = 0; i < servicesList.size(); i++) {
            System.out.println("[" + i + "] " + servicesList.get(i).getName());
        }

        Map<UUID, Service> selectedServices = new HashMap<>();
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Select the indices of the services to associate with the space (comma-separated):");
            String[] indices = scanner.nextLine().split(",");
            for (String indexStr : indices) {
                try {
                    int serviceIndex = Integer.parseInt(indexStr.trim());
                    if (serviceIndex >= 0 && serviceIndex < servicesList.size()) {
                        Service selectedService = servicesList.get(serviceIndex);
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


        Subscription subscription = new Subscription();
        subscription.setDuration(duration);
        subscription.setDurationType(durationType);
        subscription.setStartDate(startDate);
        subscription.setMember(memberAuth);
        subscription.setSpace(spaceDetails);
        subscription.setServices(selectedServices);

        ViewUtility.showLoading("⏳ Reserving the space");
        subscriptionController.createSubscription(subscription);
    }



}
