package com.workPal.services.serviceImpl;

import com.workPal.model.Feedback;
import com.workPal.repositories.interfaces.FeedbackRepository;
import com.workPal.repositories.repositoryImpl.FeedbackImpl;
import com.workPal.services.interfaces.FeedbackService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository = new FeedbackImpl();

    @Override
    public Map<UUID, Feedback> getFeedbackForSpace(UUID spaceId) {
        return feedbackRepository.getFeedbackForSpace(spaceId);
    }

    @Override
    public Map<UUID, Feedback> getFeedbackForMember(UUID memberId) {
        return feedbackRepository.getFeedbackForMember(memberId);
    }

    @Override
    public boolean addFeedback(Feedback feedback) {
        return feedbackRepository.addFeedback(feedback);
    }

    @Override
    public boolean removeFeedback(UUID feedbackId) {
        return feedbackRepository.removeFeedback(feedbackId);
    }
}
