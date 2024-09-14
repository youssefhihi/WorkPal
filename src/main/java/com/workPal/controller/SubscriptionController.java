package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Member;
import com.workPal.model.Space;
import com.workPal.model.Subscription;
import com.workPal.services.interfaces.SubscriptionService;
import com.workPal.services.serviceImpl.SubscriptionServiceImpl;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SubscriptionController {
    private static final Connection connection = DatabaseConnection.connect();

    private final SubscriptionService subscriptionService = new SubscriptionServiceImpl(connection);

    public void createSubscription(Subscription subscription) {
        subscriptionService.CreateSubscription(subscription);
    }

    public void removeSubscription(Subscription subscription) {
        subscriptionService.RemoveSubscription(subscription);
    }

    public Map<UUID, Subscription> getMemberSubscriptions(Member member) {
        return subscriptionService.getMemberSubscription(member);
    }

    public Map<UUID, Space> getSpaceSubscriptions(Space space) {
        return subscriptionService.getSpaceSubscription(space);
    }

    public Map<UUID, Subscription> getAllSubscriptions() {
        return subscriptionService.getAll();
    }

    public Optional<Subscription> getMemberSpaceSubscription(Subscription subscription) {
        return subscriptionService.getMemberSpace(subscription);
    }
    public Map<UUID, Subscription> getSubscriptionNotAccepted(){
        return subscriptionService.getSubscriptionNotAccepted();
    }
    public void  acceptSubscription(Subscription subscription){
        subscriptionService.acceptSubscription(subscription);
    }
}
