import java.util.Scanner;

public class trial {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Rooms: ");
        displayRooms();
        int roomIndex = -1;
        double totalPrice = 0;
        while (true) {
            System.out.println("--- Menu ---");
            System.out.println(" 1. Book Room");
            System.out.println(" 2. Cancel Room");
            System.out.println(" 3. Payment");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("\nEnter Room Number to book: ");
                        String roomToBook = scanner.nextLine();
                        roomIndex = -1;
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
                        if (HotelRoom.rooms[roomIndex].isAvailable) {
                            break;
                        } else {
                            System.out.println(
                                    "Sorry, Room" + roomToBook + "is not available. Please choose another room.");
                        }
                    }
                    System.out.println("\nEnter number of nights: ");
                    int nights = scanner.nextInt();
                    boolean isMember = false;
                    while (true) {
                        System.out.print("Are you a member? (T/F): ");
                        String memberInput = scanner.next().toUpperCase();
                        if (memberInput.equals("T")) {
                            isMember = true;
                            break;
                        } else if (memberInput.equals("F")) {
                            isMember = false;
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter either 'T'(for ture) or 'F'(for false).");
                        }
                    }
                    double price = HotelRoom.rooms[roomIndex].calculatePrice(nights, isMember);
                    System.out.println("Total Price for " + nights + "night(s): " + price);
                    totalPrice += price;
                    HotelRoom.rooms[roomIndex].bookRoom(roomIndex);
                    System.out.println("\nUpdated Rooom Availabilty: ");
                    displayRooms();
                    System.out.print("\nDo you want to book an additional room (Y/N): ");
                    scanner.nextLine();
                    String response = scanner.nextLine().toUpperCase();
                    if (!response.equals("Y")) {
                        break;
                    }
                    System.out.println("\nTotal amount for all bookings:" + totalPrice);
                    scanner.close();

                case 2:
                    System.out.println("Option 2 selected");
                    break;
                case 3:
                    System.out.println("Option 3 selected");
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
                System.out.println(HotelRoom.rooms[i] + "| Status: Not Available");
            }
        }
    }
}