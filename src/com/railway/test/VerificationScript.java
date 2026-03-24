package com.railway.test;

import com.railway.model.*;
import com.railway.service.RailwayService;
import java.util.*;

public class VerificationScript {
    public static void main(String[] args) {
        RailwayService service = new RailwayService();

        System.out.println("--- Testing Booking ---");
        // Book 60 seats for AC
        for (int i = 1; i <= 60; i++) {
            service.bookTicket(new Passenger("User" + i, 25, "M"), CoachType.AC);
        }
        
        System.out.println("Booking seat 61 (Should go to WL):");
        Ticket wl1 = service.bookTicket(new Passenger("WL-User1", 30, "F"), CoachType.AC);
        System.out.println(wl1);

        System.out.println("\n--- Testing Cancellation and Promotion ---");
        // Cancel PNR 1001 (Seat 1)
        if (service.cancelTicket(1001)) {
            System.out.println("Cancelled ticket 1001");
        }

        System.out.println("\n--- Reservation Chart After Promotion ---");
        service.prepareChart();
    }
}
