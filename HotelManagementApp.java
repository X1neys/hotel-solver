import java.util.Scanner;

public class HotelManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Available Rooms:");
            for (HotelRoom room : HotelRoom.rooms) {
                System.out.println(room);
            }

            System.out.print("Enter room number to book: ");
            String roomNumber = scanner.next();
            System.out.print("Enter number of nights: ");
            int nights = scanner.nextInt();
            System.out.print("Are you a member? (T/F): ");
            char isMemberInput = scanner.next().toUpperCase().charAt(0);
            boolean isMember = (isMemberInput == 'T');

            boolean roomFound = false;
            for (HotelRoom room : HotelRoom.rooms) {
                if (room.roomNumber.equals(roomNumber)) {
                    roomFound = true;
                    if (room.checkRoomAvailability(nights)) {
                        double price = room.calculatePrice(nights, isMember);
                        System.out.println("Total price: " + price);
                        System.out.println("Room " + roomNumber + " successfully booked.");
                    } else {
                        System.out.println("Room is unavailable. Booking failed.");
                    }
                    break;
                }
            }

            if (!roomFound) {
                System.out.println("Room number not found. Please try again.");
            }
        }
    }
}