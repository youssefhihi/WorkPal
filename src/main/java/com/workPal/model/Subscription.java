package com.workPal.model;

import java.util.Map;
import java.util.UUID;

public class Subscription {
    UUID id;
    private Member member;
    private Space space;
    private String durationType;
    private Integer duration;
    private String startDate ;
    private Map<UUID, Service> services;

    public Subscription(Member member, Space space, String durationType, Integer duration,String startDate, Map<UUID, Service> services) {
        this.member = member;
        this.space = space;
        this.durationType = durationType;
        this.duration = duration;
        this.startDate =startDate;
        this.services = services;
    }
    public Subscription(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public void setStartDate(String startDate){
        this.startDate =startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Map<UUID, Service> getServices() {
        return services;
    }

    public void setServices(Map<UUID, Service> services) {
        this.services = services;
    }
}
