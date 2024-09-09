package com.workPal.utility.Mails;

public class MailMsg {

    public static String resetPassword(String token, String userName){
        return "<html>" +
                "<body>" +
                "<h2>Password Reset Request</h2>" +
                "<p>Dear " + userName + ",</p>" +
                "<p>We received a request to reset your password. Please use the following code to reset your password:</p>" +
                "<h3 style='color: blue;'>" + token + "</h3>" +
                "<p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>" +
                "<p>Thank you,<br>WorkPal Support Team</p>" +
                "</body>" +
                "</html>";
    }
}
