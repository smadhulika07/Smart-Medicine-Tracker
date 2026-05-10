
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

interface ReminderSystem {
    void addMedicine(String name, String time);
    void showSchedule();
    void markAsTaken(String name);
}

class Medicine {
    String name;
    String time;
    boolean taken;

    Medicine(String name, String time) {
        this.name = name;
        this.time = time;
        this.taken = false;
    }
}

class SmartMedicineReminder implements ReminderSystem {

    private List<Medicine> medicines = new ArrayList<>();
    private Map<LocalDate, List<String>> history = new HashMap<>();

    @Override
    public void addMedicine(String name, String time) {
        medicines.add(new Medicine(name, time));
        System.out.println("Medicine Added Successfully!");
    }

    @Override
    public void showSchedule() {
        System.out.println("\nMedicine Schedule:");

        for (Medicine med : medicines) {
            System.out.println("Medicine: " + med.name +
                    " | Time: " + med.time +
                    " | Taken: " + med.taken);
        }
    }

    @Override
    public void markAsTaken(String name) {
        for (Medicine med : medicines) {
            if (med.name.equalsIgnoreCase(name)) {
                med.taken = true;

                history.putIfAbsent(LocalDate.now(), new ArrayList<>());
                history.get(LocalDate.now()).add(name);

                System.out.println(name + " marked as taken.");
                return;
            }
        }

        System.out.println("Medicine not found.");
    }

    public void showHistory() {
        System.out.println("\nMedicine Intake History:");

        for (LocalDate date : history.keySet()) {
            System.out.println(date + " -> " + history.get(date));
        }
    }

    public void reminderCheck() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String currentTime = LocalTime.now()
                        .withSecond(0)
                        .withNano(0)
                        .toString();

                for (Medicine med : medicines) {
                    if (med.time.equals(currentTime) && !med.taken) {
                        System.out.println("\nReminder: Time to take " + med.name);
                    }
                }
            }
        }, 0, 60000);
    }
}

public class medicine_tracker {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        SmartMedicineReminder system = new SmartMedicineReminder();

        system.reminderCheck();

        while (true) {
            System.out.println("\n===== Smart Medicine Tracker =====");
            System.out.println("1. Add Medicine");
            System.out.println("2. Show Schedule");
            System.out.println("3. Mark Medicine as Taken");
            System.out.println("4. Show History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter medicine name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter time (HH:MM): ");
                    String time = sc.nextLine();

                    system.addMedicine(name, time);
                    break;

                case 2:
                    system.showSchedule();
                    break;

                case 3:
                    System.out.print("Enter medicine name: ");
                    String medName = sc.nextLine();
                    system.markAsTaken(medName);
                    break;

                case 4:
                    system.showHistory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}