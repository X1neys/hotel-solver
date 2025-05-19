import java.util.*;

public class HotelManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Rooms: ");
        displayRooms();

        List<Integer> bookedRoomThing = new ArrayList<>();
        List<Double> bookedRoomPrices = new ArrayList<>();
        double totalPrice = 0;

        while (true) {
            System.out.println("--- Menu ---");
            System.out.println(" 1. Book Room");
            System.out.println(" 2. Cancel Room");
            System.out.println(" 3. Payment");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("\nEnter Room Number to book: ");
                        String roomToBook = scanner.nextLine();
                        int roomIndex = -1;
                        for (int i = 0; i < HotelRoom.rooms.length; i++) {
                            if (HotelRoom.rooms[i].roomNumber.equals(roomToBook)) {
                                roomIndex = i;
                                break;
                            }
                        }
                        if (roomIndex == -1) {
                            System.out.println("Invalid room number. Please try again. ");
                            continue;
                        }
                        if (!HotelRoom.rooms[roomIndex].isAvailable) {
                            System.out.println(
                                    "Sorry, Room " + roomToBook + " is not available. Please choose another room.");
                            continue;
                        }
                        System.out.println("\nEnter number of nights: ");
                        int nights = scanner.nextInt();
                        scanner.nextLine();
                        boolean isMember = false;
                        while (true) {
                            System.out.print("Are you a member? (T/F): ");
                            String memberInput = scanner.nextLine().toUpperCase();
                            if (memberInput.equals("T")) {
                                isMember = true;
                                break;
                            } else if (memberInput.equals("F")) {
                                isMember = false;
                                break;
                            } else {
                                System.out
                                        .println("Invalid input. Please enter either 'T'(for true) or 'F'(for false).");
                            }
                        }
                        double price = HotelRoom.rooms[roomIndex].calculatePrice(nights, isMember);
                        System.out.println("Total Price for " + nights + " night(s): " + price);
                        totalPrice += price;
                        bookedRoomThing.add(roomIndex);
                        bookedRoomPrices.add(price);
                        HotelRoom.rooms[roomIndex].bookRoom(roomIndex);
                        System.out.println("\nUpdated Room Availability: ");
                        displayRooms();
                        System.out.print("\nDo you want to book an additional room (Y/N): ");
                        String response = scanner.nextLine().toUpperCase();
                        if (!response.equals("Y")) {
                            break;
                        }
                    }
                    System.out.println("\nTotal amount for all bookings: " + totalPrice);
                    break;

                case 2:
                    System.out.println("\nEnter Room Number to cancel: ");
                    String roomToCancel = scanner.nextLine();
                    int cancelIndex = -1;
                    for (int i = 0; i < HotelRoom.rooms.length; i++) { // Loop through all rooms
                        if (HotelRoom.rooms[i].roomNumber.equals(roomToCancel)) { // Check if current room's number

                            cancelIndex = i;
                            break;
                        }
                    }

                    if (cancelIndex == -1) { // If no matching room was found
                        System.out.println("Invalid room number. Please try again."); // Inform user of invalid input
                    } else if (HotelRoom.rooms[cancelIndex].isAvailable) { // If the room is already available (not
                        // booked)

                        System.out.println("Room " + roomToCancel + " is not clearly booked."); // Inform user room is
                        // not booked
                    } else {
                        HotelRoom.rooms[cancelIndex].cancelRoom(cancelIndex); // Mark the room as available (cancel
                        // booking)

                        int found = -1;

                        for (int i = 0; i < bookedRoomThing.size(); i++) {
                            if (bookedRoomThing.get(i) == cancelIndex) { // Find the index matching the cancelled room
                                found = i;
                                break;
                            }
                        }

                        if (found != -1) {
                            totalPrice -= bookedRoomPrices.get(found); // Subtract the room's price from total
                            bookedRoomThing.remove(found); // Remove the room index from booked list
                            bookedRoomPrices.remove(found); // Remove the room price from booked prices list
                        }

                        System.out.println("Room " + roomToCancel + " booking cancelled.");
                        System.out.println("\nUpdated Room Availability: ");
                        displayRooms();
                        System.out.println("Updated total amount: " + totalPrice);
                    }
                    break;

                case 3:
                    System.out.println("\n--- Payment Summary ---");
                    if (totalPrice <= 0) {
                        System.out.println("No rooms have been booked.");
                        break;
                    }
                    System.out.println("Total amount due: $" + totalPrice);
                    System.out.print("Enter payment amount: $");
                    double payment = scanner.nextDouble();
                    scanner.nextLine();
                    if (payment >= totalPrice) {
                        System.out.println("Payment successful! Change: $" + (payment - totalPrice));
                        totalPrice = 0;
                        bookedRoomThing.clear();
                        bookedRoomPrices.clear();
                        System.out.println("Thank you for your payment!");
                        for (HotelRoom room : HotelRoom.rooms) {
                            room.isAvailable = true; // Reset all rooms to available after payment
                        }
                        System.out.println("\nRoom Availability (after update):");
                        for (int i = 0; i < HotelRoom.rooms.length; i++) {
                            System.out.println(HotelRoom.rooms[i]);
                        }
                        continue;

                    } else {
                        System.out.println("Insufficient payment. Please pay the full amount.");
                    }
                    break;

                default:
                    System.out.println("Invalid option selected");

            }
        }
    }

    private static void displayRooms() {
        for (int i = 0; i < HotelRoom.rooms.length; i++) {
            if (HotelRoom.rooms[i].isAvailable) {
                System.out.println(HotelRoom.rooms[i]);
            } else {
                System.out.println(HotelRoom.rooms[i] + " | Status: Not Available");
            }
        }
    }
}
