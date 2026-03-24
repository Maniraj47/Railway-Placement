package com.railway;

import com.railway.model.*;
import com.railway.service.RailwayService;
import java.util.*;

public class Main {
    private static final RailwayService railwayService = new RailwayService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   RAILWAY RESERVATION SYSTEM   ");
        System.out.println("========================================");

        while (true) {
            System.out.println("\n1. Book Ticket");
            System.out.println("2. Check Availability");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Prepare Chart");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> bookTicket();
                case 2 -> checkAvailability();
                case 3 -> cancelTicket();
                case 4 -> railwayService.prepareChart();
                case 5 -> {
                    System.out.println("Exiting... Happy Journey!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void bookTicket() {
        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.println("Select Coach Type:");
        for (int i = 0; i < CoachType.values().length; i++) {
            System.out.println((i + 1) + ". " + CoachType.values()[i].getDisplayName());
        }
        System.out.print("Choice: ");
        int coachChoice = Integer.parseInt(scanner.nextLine());
        CoachType type = CoachType.values()[coachChoice - 1];

        Passenger passenger = new Passenger(name, age, gender);
        Ticket ticket = railwayService.bookTicket(passenger, type);

        if (ticket != null) {
            System.out.println("Booking Success!");
            System.out.println(ticket);
        } else {
            System.out.println("Sorry, no seats or waiting list spots available.");
        }
    }

    private static void checkAvailability() {
        Map<CoachType, Integer> availability = railwayService.getAvailability();
        System.out.println("\n--- Availability ---");
        availability.forEach((type, count) -> 
            System.out.printf("%-15s: %d seats available%n", type.getDisplayName(), count));
    }

    private static void cancelTicket() {
        System.out.print("Enter PNR to cancel: ");
        int pnr = Integer.parseInt(scanner.nextLine());
        if (railwayService.cancelTicket(pnr)) {
            System.out.println("Ticket cancelled successfully.");
        } else {
            System.out.println("Ticket not found or already cancelled.");
        }
    }
}
