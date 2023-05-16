import java.util.Vector;

// Create exception for throw, if room is already occupied
class StudyRoomUnavailableException extends Exception {
    public StudyRoomUnavailableException() {
        super("Study room is already occupied!");
    }
}

class StudyRoom {
    private int roomNumber;
    private int capacity;
    private boolean availability;

    public StudyRoom(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.availability = true;

        // Add newly created study room to study room list
        StudyRoomReservationSystem.studyRoomList.add(this);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

public class StudyRoomReservationSystem {
    static Vector<StudyRoom> studyRoomList = new Vector<>();

    // Reserves the study room with the specified room number, if it is available
    synchronized void reserveStudyRoom(int roomNumber) throws StudyRoomUnavailableException {
        boolean flag = true;
        for(StudyRoom room : studyRoomList) {
            if (room.getRoomNumber() == roomNumber) {
                flag = false;
                if (room.isAvailable()) {
                    room.setAvailability(false);
                } else {
                    // Throw exception if room is already occupied
                    throw new StudyRoomUnavailableException();
                }
                break;
            }
        }
        // If the given room number is invalid, do this
        if (flag) {
            System.out.println("Invalid Room Number!");
        }
    }

    // Releases the study room with the specified room number
    synchronized void releaseStudyRoom(int roomNumber) {
        boolean flag = true;
        for(StudyRoom room : studyRoomList) {
            if (room.getRoomNumber() == roomNumber) {
                flag = false;
                if (room.isAvailable()) {
                    System.out.println("Study room " + room.getRoomNumber() + " is already free");
                } else {
                    room.setAvailability(true);
                }
            }
        }
        // If the given room number is invalid, do this
        if (flag) {
            System.out.println("Invalid Room Number!");
        }
    }

    // Display the status of all study rooms
    void displayStudyRoomStatus() {
        System.out.println("Study Room Status:");
        for (StudyRoom room : studyRoomList) {
            if (room.isAvailable()) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Capacity: " + room.getCapacity() + ", Availability: Available");
            } else {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Capacity: " + room.getCapacity() + ", Availability: Not Available");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StudyRoomReservationSystem system = new StudyRoomReservationSystem();

        StudyRoom room1 = new StudyRoom(1, 4);
        StudyRoom room2 = new StudyRoom(2, 6);
        StudyRoom room3 = new StudyRoom(3, 8);
        StudyRoom room4 = new StudyRoom(4, 2);
        StudyRoom room5 = new StudyRoom(5, 3);

        system.displayStudyRoomStatus();

        // Thread 1 for reserve room number 1
        Runnable obj1 = new Runnable() {
            public void run() {
                try {
                    system.reserveStudyRoom(1);
                } catch (StudyRoomUnavailableException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        Thread t1 = new Thread(obj1);

        // Thread 2 for reserve room number 2
        Runnable obj2 = new Runnable() {
            public void run() {
                try {
                    system.reserveStudyRoom(2);
                } catch (StudyRoomUnavailableException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        Thread t2 = new Thread(obj2);

        // Thread 3 for release room number 1
        Runnable obj3 = new Runnable() {
            public void run() {
                system.releaseStudyRoom(1);
            }
        };
        Thread t3 = new Thread(obj3);

        // Thread 4 for reserve room number 4
        Runnable obj4 = new Runnable() {
            public void run() {
                try {
                    system.reserveStudyRoom(4);
                } catch (StudyRoomUnavailableException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        Thread t4 = new Thread(obj4);

        // Thread 5 for reserve room number 3
        Runnable obj5 = new Runnable() {
            public void run() {
                try {
                    system.reserveStudyRoom(3);
                } catch (StudyRoomUnavailableException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        Thread t5 = new Thread(obj5);

        // Thread 6 for reserve room number 1
        Runnable obj6 = new Runnable() {
            public void run() {
                try {
                    system.reserveStudyRoom(1);
                } catch (StudyRoomUnavailableException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        Thread t6 = new Thread(obj6);

        // Thread 7 for release room number 3
        Runnable obj7 = new Runnable() {
            public void run() {
                system.releaseStudyRoom(3);
            }
        };
        Thread t7 = new Thread(obj7);

        // Starting Threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();

        System.out.println();
        system.displayStudyRoomStatus();

    }

}
