package com.workPal.controller;

import com.workPal.model.Feedback;
import com.workPal.services.interfaces.FeedbackService;
import com.workPal.services.serviceImpl.FeedbackServiceImpl;

import java.util.List;
import java.util.UUID;

public class FeedbackController {

    private final FeedbackService feedbackService = new FeedbackServiceImpl();

    public List<Feedback> getFeedbackForSpace(UUID spaceId) {
        return feedbackService.getFeedbackForSpace(spaceId);
    }

    public List<Feedback> getFeedbackForMember(UUID memberId) {
        return feedbackService.getFeedbackForMember(memberId);
    }

    public boolean addFeedback(Feedback feedback) {
        return feedbackService.addFeedback(feedback);
    }

    public boolean removeFeedback(UUID feedbackId) {
        return feedbackService.removeFeedback(feedbackId);
    }
}
