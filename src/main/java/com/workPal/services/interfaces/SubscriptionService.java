package com.workPal.services.interfaces;

import com.workPal.model.Manager;
import com.workPal.model.Member;
import com.workPal.model.Space;
import com.workPal.model.Subscription;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionService {

    void CreateSubscription(Subscription subscription);
    void RemoveSubscription(Subscription subscription);
    Map<UUID, Subscription> getMemberSubscription(Member member);
    Map<UUID, Space> getSpaceSubscription(Space space);
    Map<UUID, Subscription> getAll();
    Optional<Subscription> getMemberSpace(Subscription subscription);
    Map<UUID, Subscription> getSubscriptionNotAccepted(Manager manager);
    void acceptSubscription(Subscription subscription);
}
