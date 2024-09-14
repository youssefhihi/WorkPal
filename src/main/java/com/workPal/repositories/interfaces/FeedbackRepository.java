package com.workPal.repositories.interfaces;

import com.workPal.model.Feedback;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository {
    List<Feedback> getFeedbackForSpace(UUID spaceId);
    List<Feedback> getFeedbackForMember(UUID memberId);
    boolean addFeedback(Feedback feedback);
    boolean removeFeedback(UUID feedbackId);
}
