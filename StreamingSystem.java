import java.util.*;

public class StreamingSystem {
    private Scanner sc = new Scanner(System.in);
    private List<User> users = new ArrayList<>();

    public void run() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1) Add user");
            System.out.println("2) Remove user by ID");
            System.out.println("3) Remove user by username");
            System.out.println("4) List all users");
            System.out.println("5) User operations");
            System.out.println("6) Exit");
            System.out.print("Choose> ");
            switch (sc.nextLine()) {
                case "1": addUser(); break;
                case "2": removeUserByID(); break;
                case "3": removeUserByName(); break;
                case "4": listUsers(); break;
                case "5": userMenu(); break;
                case "6": return;
                default: System.out.println("Invalid choice."); 
            }
        }
    }

    private void addUser() {
        System.out.print("Enter username: ");
        String name = sc.nextLine().trim();
        users.add(new User(name));
        System.out.println("Added user \"" + name + "\" (ID=" + users.get(users.size()-1).getUserID() + ")");
    }

    private void removeUserByID() {
        System.out.print("Enter user ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            users.removeIf(u -> u.getUserID() == id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void removeUserByName() {
        System.out.print("Enter username: ");
        String name = sc.nextLine().trim();
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(name));
    }

    private void listUsers() {
        System.out.println("--- Users ---");
        for (User u : users) {
            System.out.printf("%d: %s%n", u.getUserID(), u.getUsername());
        }
    }

    private User findUser() {
        System.out.print("Enter user ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            for (User u : users) if (u.getUserID() == id) return u;
        } catch (NumberFormatException ignored) {}
        System.out.println("User not found.");
        return null;
    }

    private void userMenu() {
        User u = findUser();
        if (u == null) return;
        while (true) {
            System.out.println("\n--- User: " + u.getUsername() + " ---");
            System.out.println("1) Add recording");
            System.out.println("2) Add playlist from file");
            System.out.println("3) Add playlist from another user");
            System.out.println("4) Remove recording by index");
            System.out.println("5) Remove recording by name");
            System.out.println("6) Play recording by index");
            System.out.println("7) Play recording by name");
            System.out.println("8) Play entire playlist");
            System.out.println("9) Shuffle playlist");
            System.out.println("10) Save playlist to file");
            System.out.println("11) Display playlist stats");
            System.out.println("12) Back to main menu");
            System.out.print("Choose> ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": addRecording(u); break;
                case "2": addFromFile(u); break;
                case "3": addFromUser(u); break;
                case "4": removeByIndex(u); break;
                case "5": removeByName(u); break;
                case "6": playByIndex(u); break;
                case "7": playByName(u); break;
                case "8": u.playAll(); break;
                case "9": u.shuffle(); break;
                case "10": u.savePlaylist(); break;
                case "11": u.displayStats(); break;
                case "12": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void addRecording(User u) {
        try {
            System.out.print("Artist: "); String a = sc.nextLine();
            System.out.print("Name:   "); String n = sc.nextLine();
            System.out.print("Duration (s): "); int d = Integer.parseInt(sc.nextLine());
            u.addRecording(new AudioRecording(a, n, d));
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration.");
        }
    }

    private void addFromFile(User u) {
        System.out.print("Enter filename: ");
        u.addFromFile(sc.nextLine().trim());
    }

    private void addFromUser(User u) {
        System.out.print("Enter other user ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            User other = users.stream()
                .filter(x -> x.getUserID() == id && x != u)
                .findFirst().orElse(null);
            if (other != null) u.addFromUser(other);
            else System.out.println("Invalid user.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void removeByIndex(User u) {
        try {
            System.out.print("Index: ");
            u.removeByIndex(Integer.parseInt(sc.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid index.");
        }
    }

    private void removeByName(User u) {
        System.out.print("Name: ");
        u.removeByName(sc.nextLine().trim());
    }

    private void playByIndex(User u) {
        try {
            System.out.print("Index: ");
            u.playByIndex(Integer.parseInt(sc.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid index.");
        }
    }

    private void playByName(User u) {
        System.out.print("Name: ");
        u.playByName(sc.nextLine().trim());
    }
}
