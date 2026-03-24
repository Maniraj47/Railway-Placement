package com.railway.model;

public class Ticket {
    private static int idCounter = 1001;
    private final int pnr;
    private final Passenger passenger;
    private final CoachType coachType;
    private int seatNumber; // -1 if waiting
    private SeatStatus status;

    public Ticket(Passenger passenger, CoachType coachType, int seatNumber, SeatStatus status) {
        this.pnr = idCounter++;
        this.passenger = passenger;
        this.coachType = coachType;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public int getPnr() {
        return pnr;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String seatInfo = (seatNumber == -1) ? "Waiting List" : "Seat " + seatNumber;
        return String.format("PNR: %d | %s | %s | %s | %s", pnr, passenger, coachType.getDisplayName(), seatInfo, status);
    }
}
