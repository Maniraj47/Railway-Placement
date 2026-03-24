package com.railway.service;

import com.railway.model.*;
import java.util.*;

public class RailwayService {
    private final Map<CoachType, List<Seat>> coachSeats = new EnumMap<>(CoachType.class);
    private final Map<CoachType, Queue<Ticket>> waitingLists = new EnumMap<>(CoachType.class);
    private final List<Ticket> allTickets = new ArrayList<>();

    public RailwayService() {
        for (CoachType type : CoachType.values()) {
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= type.getCapacity(); i++) {
                seats.add(new Seat(i, type));
            }
            coachSeats.put(type, seats);
            waitingLists.put(type, new LinkedList<>());
        }
    }

    public synchronized Ticket bookTicket(Passenger passenger, CoachType type) {
        // Check for available seat
        Seat availableSeat = findAvailableSeat(type);
        if (availableSeat != null) {
            availableSeat.book(passenger);
            Ticket ticket = new Ticket(passenger, type, availableSeat.getSeatNumber(), SeatStatus.BOOKED);
            allTickets.add(ticket);
            return ticket;
        }

        // Check for waiting list
        Queue<Ticket> waitingList = waitingLists.get(type);
        if (waitingList.size() < type.getMaxWaitingList()) {
            Ticket ticket = new Ticket(passenger, type, -1, SeatStatus.WAITING);
            waitingList.add(ticket);
            allTickets.add(ticket);
            return ticket;
        }

        return null; // No availability
    }

    private Seat findAvailableSeat(CoachType type) {
        return coachSeats.get(type).stream()
                .filter(Seat::isVacant)
                .findFirst()
                .orElse(null);
    }

    public synchronized boolean cancelTicket(int pnr) {
        Ticket ticketToCancel = allTickets.stream()
                .filter(t -> t.getPnr() == pnr)
                .findFirst()
                .orElse(null);

        if (ticketToCancel == null) return false;

        CoachType type = ticketToCancel.getCoachType();
        if (ticketToCancel.getStatus() == SeatStatus.BOOKED) {
            // Free the seat
            int seatNumber = ticketToCancel.getSeatNumber();
            Seat seat = coachSeats.get(type).get(seatNumber - 1);
            seat.cancel();
            ticketToCancel.setStatus(SeatStatus.VACANT); // Technically it's cancelled
            allTickets.remove(ticketToCancel);

            // Promote from waiting list
            promoteFromWaitingList(type, seat);
            return true;
        } else if (ticketToCancel.getStatus() == SeatStatus.WAITING) {
            waitingLists.get(type).remove(ticketToCancel);
            allTickets.remove(ticketToCancel);
            return true;
        }

        return false;
    }

    private void promoteFromWaitingList(CoachType type, Seat seat) {
        Queue<Ticket> waitingList = waitingLists.get(type);
        if (!waitingList.isEmpty()) {
            Ticket nextInLine = waitingList.poll();
            seat.book(nextInLine.getPassenger());
            nextInLine.setSeatNumber(seat.getSeatNumber());
            nextInLine.setStatus(SeatStatus.BOOKED);
        }
    }

    public Map<CoachType, Integer> getAvailability() {
        Map<CoachType, Integer> availability = new EnumMap<>(CoachType.class);
        for (CoachType type : CoachType.values()) {
            long vacant = coachSeats.get(type).stream().filter(Seat::isVacant).count();
            availability.put(type, (int) vacant);
        }
        return availability;
    }

    public void prepareChart() {
        System.out.println("\n--- RESERVATION CHART ---");
        for (CoachType type : CoachType.values()) {
            System.out.println("\nCoach: " + type.getDisplayName());
            System.out.println("--------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-10s%n", "Seat No", "Passenger", "Status");
            System.out.println("--------------------------------------------------");
            for (Seat seat : coachSeats.get(type)) {
                if (!seat.isVacant()) {
                    System.out.printf("%-10d | %-20s | %-10s%n", 
                        seat.getSeatNumber(), seat.getPassenger().getName(), seat.getStatus());
                }
            }
            Queue<Ticket> wl = waitingLists.get(type);
            int count = 1;
            for (Ticket t : wl) {
                System.out.printf("WL-%-7d | %-20s | %-10s%n", 
                    count++, t.getPassenger().getName(), "WAITING");
            }
        }
    }
}
