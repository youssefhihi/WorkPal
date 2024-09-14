package com.workPal.repositories.interfaces;

import com.workPal.model.Member;
import com.workPal.model.Space;
import com.workPal.model.Subscription;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository {
    Boolean CreateSubscription(Subscription subscription);
    Boolean RemoveSubscription(Subscription subscription);
    Map<UUID, Subscription> getMemberSubscription(Member member);
    Map<UUID, Space> getSpaceSubscription(Space space);
    Optional<Subscription> getMemberSpace(Subscription subscription);
    Map<UUID, Subscription> getAll();
    Map<UUID, Subscription> getSubscriptionNotAccepted();
    Boolean acceptSubscription(Subscription subscription);


}
