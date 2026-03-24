package com.railway.model;

public class Seat {
    private final int seatNumber;
    private final CoachType coachType;
    private SeatStatus status;
    private Passenger passenger;

    public Seat(int seatNumber, CoachType coachType) {
        this.seatNumber = seatNumber;
        this.coachType = coachType;
        this.status = SeatStatus.VACANT;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public CoachType getCoachType() {
        return coachType;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public boolean isVacant() {
        return status == SeatStatus.VACANT;
    }

    public void book(Passenger passenger) {
        this.passenger = passenger;
        this.status = SeatStatus.BOOKED;
    }

    public void cancel() {
        this.passenger = null;
        this.status = SeatStatus.VACANT;
    }
}
