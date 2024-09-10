package com.workPal.utility.Mails;

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
}
