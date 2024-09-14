package com.workPal.utility.Mails;

import com.workPal.model.Subscription;

public class MailMsg {

    public static String resetPassword(String token, String userName){
        return
                "Password Reset Request\n\n" +
                "Dear " + userName + " \n\n"+
                "We received a request to reset your password. Please use the following code to reset your password:     " +
                  token + "\n\n\n" +
                "If you did not request a password reset, please ignore this email or contact support if you have questions."
                + " \n " +
                "Thank you,\n WorkPal Support Team";
    }

    public static String reservationConfirmation(Subscription subscription) {
        return
                "Reservation Confirmation\n\n" +
                        "Dear " + subscription.getMember().getName() + ",\n\n" +
                        "You have successfully reserved a co-working space: " + subscription.getSpace().getName() + ".\n" +
                        "Your reservation is for " + subscription.getDuration() + " " + subscription.getDurationType() + "(s), starting on " + subscription.getStartDate() + ".\n\n" +
                        "Please wait for confirmation from the manager. You will receive an update shortly.\n\n" +
                        "If you have any questions or concerns, please contact us.\n\n" +
                        "Thank you,\nWorkPal Support Team";
    }

    public static String reservationRefusal(Subscription subscription) {
        return
                "Reservation Request Refused\n\n" +
                        "Dear " + subscription.getMember().getName() + ",\n\n" +
                        "We regret to inform you that your reservation for the co-working space: " + subscription.getSpace().getName() + " has been refused.\n" +
                        "Reservation details:\n" +
                        "Duration: " + subscription.getDuration() + " " + subscription.getDurationType() + "(s)\n" +
                        "Start Date: " + subscription.getStartDate() + "\n\n" +
                        "If you have any questions or require further assistance, feel free to contact us.\n\n" +
                        "Thank you for choosing WorkPal,\nWorkPal Support Team";
    }

    public static String reservationAcceptedEmail(Subscription subscription) {

        return "Reservation Accepted\n\n" +
                "Dear " + subscription.getMember().getName() + ",\n\n" +
                "We are pleased to inform you that your reservation for the co-working space has been accepted!\n\n" +
                "Details of your reservation:\n" +
                "📍 Space: " + subscription.getSpace().getName() + "\n" +
                "📅 Start Date: " + subscription.getStartDate() + "\n" +
                "⏳ Duration: " + subscription.getDuration() + " " + subscription.getDuration() + "\n\n" +
                "Thank you for choosing our space. If you have any questions or need further assistance, please feel free to contact us.\n\n" +
                "Best regards,\n" +
                "The WorkPal Team";
    }


}
