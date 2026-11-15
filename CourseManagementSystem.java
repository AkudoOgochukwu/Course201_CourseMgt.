import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// 1. The Course Class (The Blueprint)
class Course {
    private String code;
    private String title;
    private int units;

    public Course(String code, String title, int units) {
        this.code = code.trim().toUpperCase();
        this.title = title.trim();
        this.units = units;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getUnits() { return units; }

    public String toFileFormat() {
        return code + "|" + title + "|" + units;
    }
}

// The CourseManager Class... Here the logic is handled 
class CourseManager {
    private ArrayList<Course> courses = new ArrayList<>();
    private final String FILE_NAME = "courses.txt";

    public void addCourse(String code, String title, int units) {
        courses.add(new Course(code, title, units));
        System.out.println("Success: " + code + " added successfully!");
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses added yet.");
            return;
        }
        System.out.println("\n--- Your Courses ---");
        System.out.printf("%-15s | %-30s | %-5s%n", "CODE", "TITLE", "UNITS");
        System.out.println("---------------------------------------------------------");
        for (Course c : courses) {
            System.out.printf("%-15s | %-30s | %-5d%n", c.getCode(), c.getTitle(), c.getUnits());
        }
    }

    // Recursive Search Function... Here the Function Calls It's self
    public int recursiveSearch(String code, int index) {
        if (index >= courses.size()) {
            return -1;
        }
        if (courses.get(index).getCode().equalsIgnoreCase(code.trim())) {
            return index;
        }
        return recursiveSearch(code, index + 1);
    }

    public void searchCourse(String code) {
        int index = recursiveSearch(code, 0);
        if (index != -1) {
            Course c = courses.get(index);
            System.out.println("Found: " + c.getCode() + " - " + c.getTitle() + " (" + c.getUnits() + " units)");
        } else {
            System.out.println("Course not found.");
        }
    }

    public void computeTotalUnits() {
        int total = 0;
        for (Course c : courses) {
            total += c.getUnits();
        }
        System.out.println("Total Credit Units: " + total);
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
            System.out.println("Success: Courses saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Error: No saved file found.");
            return;
        }

        courses.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    courses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
            System.out.println("Success: Courses loaded from file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}

// 3. The Main Class (The Interface)
public class CourseManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManager manager = new CourseManager();
        boolean running = true;

        while (running) {
            System.out.println("\nHello Miva Star!! Welcome to School");
            System.out.println(" STUDENT COURSE MANAGEMENT SYSTEM");
            System.out.println("Reach Higher Star");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by Code");
            System.out.println("4. Compute Total Units");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit Program");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Course Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Credit Units: ");
                    try {
                        int units = Integer.parseInt(scanner.nextLine().trim());
                        if (units > 0) {
                            manager.addCourse(code, title, units);
                        } else {
                            System.out.println("Error: Units must be greater than 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input. Units must be a whole number.");
                    }
                    break;
                case "2":
                    manager.viewCourses();
                    break;
                case "3":
                    System.out.print("Enter Course Code to search: ");
                    manager.searchCourse(scanner.nextLine());
                    break;
                case "4":
                    manager.computeTotalUnits();
                    break;
                case "5":
                    manager.saveToFile();
                    break;
                case "6":
                    manager.loadFromFile();
                    break;
                case "7":
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please select 1-7.");
            }
        }
        scanner.close();
    }
}
