package com.example.javaprograms;

import java.util.Random;
import java.util.Scanner;

public class MyClass {


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        String passenger_names[];
        int passenger_age[];
        boolean passenger_senior[];
        int control_number[];
        double passenger_fare[];
        int passenger_number;
        char travel_type, travel_destination;
        Random rnd = new Random();
        boolean child_no_adult = true;

        travel_type = AirlineType();

        //get number of passenger
        passenger_number = Passenger();
        if (travel_type == 'C') {
            while (passenger_number > 48) {
                System.out.println("Sorry! You have exceeded the number of\n" +
                        "passengers required\n");
                passenger_number = Passenger();
            }
        } else {
            if (travel_type == 'A') {
                while (passenger_number > 14) {
                    System.out.println("Sorry! You have exceeded the number of\n" +
                            "passengers required\n");
                    passenger_number = Passenger();
                }
            } else {
                while (passenger_number > 23) {
                    System.out.println("Sorry! You have exceeded the number of\n" +
                            "passengers required\n");
                    passenger_number = Passenger();
                }
            }
        }

        passenger_names = new String[passenger_number];
        passenger_age = new int[passenger_number];
        passenger_senior = new boolean[passenger_number];
        passenger_fare = new double[passenger_number];
        control_number = new int[passenger_number];

        //get Passenger names and generate control number
        for (int i = 0; i < passenger_number; i++) {
            control_number[i] = rnd.nextInt(999999);
            System.out.print("Passenger " + (i + 1) + " name: ");
            passenger_names[i] = kb.nextLine();
        }
        //get Passenger age
        for (int i = 0; i < passenger_number; i++) {
            System.out.print(passenger_names[i] + " age: ");
            passenger_age[i] = kb.nextInt();
            //if passenger is above 19 (meaning if there is a passenger below 19 he/she has a guardian)
            if (passenger_age[i] >= 19) {
                child_no_adult = false;
            }
            //if passenger is senior
            if (passenger_age[i] > 59) {
                passenger_senior[i] = true;
            } else {
                passenger_senior[i] = false;
            }
        }

        if (child_no_adult == true) {
            System.out.println("Passenger needs to have at least one adult to continue");
        } else {
            travel_destination = Destination();
            for (int i = 0; i < passenger_number; i++) {
                System.out.println("\n\nPassenger " + (i + 0));
                int insurance_fee = 0, baggage_fee = 0, tax = 0, additional_fee = 0;
                double total_fare = 0;
                passenger_fare[i] = Transaction(travel_destination, travel_type);

                System.out.print("Does " + passenger_names[i] + " would like to avail our travel insurance? Y/N: ");
                char insurance = kb.next().charAt(0);
                Character.toUpperCase(insurance);
                while (insurance != 'Y' && insurance != 'N') {
                    System.out.print("Does " + passenger_names[i] + " would like to avail our travel insurance? Y/N: ");
                    insurance = kb.next().charAt(0);
                    Character.toUpperCase(insurance);
                }

                if (passenger_age[i] > 59) {
                    total_fare = passenger_fare[i] - (passenger_fare[i] * .20);
                } else {
                    switch (travel_type) {
                        case 'A':
                            if (insurance == 'Y') {
                                insurance_fee = 4500;
                            }
                            baggage_fee = 1250;
                            tax = 4260;
                            additional_fee = 550;
                            break;
                        case 'B':
                            if (insurance == 'Y') {
                                insurance_fee = 6500;
                            }
                            baggage_fee = 2850;
                            tax = 5700;
                            additional_fee = 550;
                            break;
                        case 'C':
                            if (insurance == 'Y') {
                                insurance_fee = 900;
                            }
                            baggage_fee = 950;
                            tax = 2500;
                            additional_fee = 255;
                            break;
                    }
                    total_fare = passenger_fare[i] + insurance_fee + baggage_fee + tax + additional_fee;
                }

                System.out.println("\n_____________________________________________________\n");
                System.out.println("Passenger " + (i + 1) + " receipt");
                System.out.println("Control Number: " + control_number[i]);
                System.out.println("Name: " + passenger_names[i]);
                System.out.println("Age: " + passenger_age[i]);
                System.out.println("Travel Type: " + travel_type);
                System.out.println("Travel Destination: " + travel_destination);
                System.out.println("________________________________\n");
                System.out.println("Travel Destination Fee: " + passenger_fare[i]);
                System.out.println("Baggage Fee: " + baggage_fee);
                System.out.println("Insurance Fee: " + insurance_fee);
                System.out.println("Travel Tax Fee: " + tax);
                System.out.println("Additional Processing Fee: " + additional_fee);
                System.out.println("Total Fee: " + total_fare);

            }
        }
    }

    public static char AirlineType() {
        char type;
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter travel type: ");
        type = kb.next().charAt(0);
        Character.toUpperCase(type);

        while (type != 'A' && type != 'B' && type != 'C') {
            System.out.println("Invalid Entry");
            System.out.print("Enter travel type: ");
            type = kb.next().charAt(0);
            Character.toUpperCase(type);
        }

        return type;
    }

    public static int Passenger() {
        int passenger_number;
        Scanner kb = new Scanner(System.in);

        System.out.print("\nEnter the number of passenger: ");
        passenger_number = kb.nextInt();
        return passenger_number;
    }

    public static char Destination() {
        char destination;
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter destination: ");
        destination = kb.next().charAt(0);
        Character.toUpperCase(destination);

        while (destination != 'A' && destination != 'B' && destination != 'C' && destination != 'D'
                && destination != 'E' && destination != 'F' && destination != 'G' && destination != 'H'
                && destination != 'I' && destination != 'J') {
            System.out.println("Invalid Entry");
            System.out.print("Enter destination type: ");
            destination = kb.next().charAt(0);
            Character.toUpperCase(destination);
        }

        return destination;
    }

    public static double Transaction(char destination, char travel_type) {
        double fare_prices[][] = {{8000, 12500, 3500}, {9800, 12950, 3900}, {9100, 10500, 3200}, {9850, 10975, 3575},
                {27450, 37390, 12055}, {30890, 39650, 13100}, {40450, 45355, 27800}, {43855, 49780, 29400},
                {8505, 12345, 3200}, {14300, 16320, 4600}};

        double total = 0;
        switch (destination) {
            case 'A':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[0][0];
                        break;
                    case 'B':
                        total = fare_prices[0][1];
                        break;
                    case 'C':
                        total = fare_prices[0][2];
                        break;
                }
                break;
            case 'B':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[1][0];
                        break;
                    case 'B':
                        total = fare_prices[1][1];
                        break;
                    case 'C':
                        total = fare_prices[1][2];
                        break;
                }
                break;
            case 'C':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[2][0];
                        break;
                    case 'B':
                        total = fare_prices[2][1];
                        break;
                    case 'C':
                        total = fare_prices[2][2];
                        break;
                }
                break;
            case 'D':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[3][0];
                        break;
                    case 'B':
                        total = fare_prices[3][1];
                        break;
                    case 'C':
                        total = fare_prices[3][2];
                        break;
                }
                break;
            case 'E':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[4][0];
                        break;
                    case 'B':
                        total = fare_prices[4][1];
                        break;
                    case 'C':
                        total = fare_prices[4][2];
                        break;
                }
                break;
            case 'F':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[5][0];
                        break;
                    case 'B':
                        total = fare_prices[5][1];
                        break;
                    case 'C':
                        total = fare_prices[5][2];
                        break;
                }
                break;
            case 'G':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[6][0];
                        break;
                    case 'B':
                        total = fare_prices[6][1];
                        break;
                    case 'C':
                        total = fare_prices[6][2];
                        break;
                }
                break;
            case 'H':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[7][0];
                        break;
                    case 'B':
                        total = fare_prices[7][1];
                        break;
                    case 'C':
                        total = fare_prices[7][2];
                        break;
                }
                break;
            case 'I':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[8][0];
                        break;
                    case 'B':
                        total = fare_prices[8][1];
                        break;
                    case 'C':
                        total = fare_prices[8][2];
                        break;
                }
                break;
            case 'J':
                switch (travel_type) {
                    case 'A':
                        total = fare_prices[9][0];
                        break;
                    case 'B':
                        total = fare_prices[9][1];
                        break;
                    case 'C':
                        total = fare_prices[9][2];
                        break;
                }
                break;
        }
        return total;
    }
}