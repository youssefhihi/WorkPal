package com.workPal.repositories.interfaces;

import com.workPal.model.Feedback;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FeedbackRepository {
    Map<UUID, Feedback> getFeedbackForSpace(UUID spaceId);
    Map<UUID, Feedback> getFeedbackForMember(UUID memberId);
    boolean addFeedback(Feedback feedback);
    boolean removeFeedback(UUID feedbackId);
}
