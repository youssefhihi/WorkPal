package com.workPal.services.serviceImpl;

import com.workPal.model.Member;
import com.workPal.model.Space;
import com.workPal.model.Subscription;
import com.workPal.repositories.interfaces.SubscriptionRepository;
import com.workPal.repositories.repositoryImpl.SubscriptionImpl;
import com.workPal.services.interfaces.SubscriptionService;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(Connection connection) {
        this.subscriptionRepository = new SubscriptionImpl(connection);
    }

    @Override
    public void CreateSubscription(Subscription subscription) {
        Optional<Subscription> isExist = subscriptionRepository.getMemberSpace(subscription);

        if (isExist.isEmpty()) {
            Boolean isAdded = subscriptionRepository.CreateSubscription(subscription);
            if (isAdded) {
                System.out.println("✅ Reservation added successfully!");
            } else {
                System.out.println("❗ Failed to reserve space. An unexpected error occurred.");
            }
        } else {
            System.out.println("❗ Failed to reserve this space. Space already reserved for this member.");
        }
    }

    @Override
    public void RemoveSubscription(Subscription subscription) {
        boolean isRemoved = subscriptionRepository.RemoveSubscription(subscription);
        if (isRemoved) {
            System.out.println("✅ Subscription removed successfully.");
        } else {
            System.out.println("❗ Failed to remove subscription. Please check the ID and try again.");
        }
    }
    @Override
    public void acceptSubscription(Subscription subscription){
        Boolean accepted = subscriptionRepository.acceptSubscription(subscription);
        if (accepted){
            System.out.println("✅ Subscription of " + subscription.getMember().getName() + " has been accepted.");
        } else {
            System.out.println("❌ Failed to accept Subscription of " + subscription.getMember().getName()  + ".");
        }
    }

    @Override
    public Map<UUID, Subscription> getMemberSubscription(Member member) {
        return subscriptionRepository.getMemberSubscription(member);
    }

    @Override
    public Map<UUID, Space> getSpaceSubscription(Space space) {
        return subscriptionRepository.getSpaceSubscription(space);
    }

    @Override
    public Map<UUID, Subscription> getAll() {
        return subscriptionRepository.getAll();
    }

    @Override
    public Optional<Subscription> getMemberSpace(Subscription subscription) {
        return subscriptionRepository.getMemberSpace(subscription);
    }
    @Override
    public  Map<UUID, Subscription> getSubscriptionNotAccepted(){
        return subscriptionRepository.getSubscriptionNotAccepted();
    }
}
