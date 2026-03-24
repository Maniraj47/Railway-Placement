package com.railway.model;

public enum CoachType {
    AC("AC Coach", 60, 10),
    NON_AC("Non-AC Coach", 60, 10),
    SEATER("Seater", 60, 10);

    private final String displayName;
    private final int capacity;
    private final int maxWaitingList;

    CoachType(String displayName, int capacity, int maxWaitingList) {
        this.displayName = displayName;
        this.capacity = capacity;
        this.maxWaitingList = maxWaitingList;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxWaitingList() {
        return maxWaitingList;
    }
}
