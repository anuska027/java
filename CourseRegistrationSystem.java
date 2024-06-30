import java.util.ArrayList;
import java.util.Scanner;

// Class to represent a course
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean registerStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        } else {
            return false;
        }
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasSlot() {
        return enrolled < capacity;
    }

    @Override
    public String toString() {
        return code + ": " + title + "\n" +
                "Description: " + description + "\n" +
                "Schedule: " + schedule + "\n" +
                "Enrolled: " + enrolled + "/" + capacity + "\n";
    }
}

// Class to represent a student
class Student {
    private String id;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerForCourse(Course course) {
        if (course.hasSlot() && !registeredCourses.contains(course)) {
            if (course.registerStudent()) {
                registeredCourses.add(course);
                return true;
            }
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            if (course.dropStudent()) {
                registeredCourses.remove(course);
                return true;
            }
        }
        return false;
    }
}

// Main class for the Student Course Registration System
public class CourseRegistrationSystem {
    private static ArrayList<Course> courseDatabase = new ArrayList<>();
    private static ArrayList<Student> studentDatabase = new ArrayList<>();

    public static void main(String[] args) {
        // Sample courses
        courseDatabase.add(new Course("CS101", "Introduction to Computer Science", "Basics of computer science", 30, "MWF 10-11"));
        courseDatabase.add(new Course("MA101", "Calculus I", "Introduction to calculus", 25, "TTh 9-10:30"));
        courseDatabase.add(new Course("PH101", "Physics I", "Introduction to physics", 20, "MWF 11-12"));

        // Sample students
        studentDatabase.add(new Student("S1001", "Alice"));
        studentDatabase.add(new Student("S1002", "Bob"));

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nCourse Registration System Menu:");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Display Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayAvailableCourses();
                    break;
                case 2:
                    registerForCourse(scanner);
                    break;
                case 3:
                    dropCourse(scanner);
                    break;
                case 4:
                    displayRegisteredCourses(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the Course Registration System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void displayAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase) {
            System.out.println(course);
        }
    }

    private static void registerForCourse(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student ID not found.");
            return;
        }

        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course code not found.");
            return;
        }

        if (student.registerForCourse(course)) {
            System.out.println("Successfully registered for the course.");
        } else {
            System.out.println("Failed to register for the course. It may be full or you may already be registered.");
        }
    }

    private static void dropCourse(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student ID not found.");
            return;
        }

        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course code not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Successfully dropped the course.");
        } else {
            System.out.println("Failed to drop the course. You may not be registered in this course.");
        }
    }

    private static void displayRegisteredCourses(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student ID not found.");
            return;
        }

        System.out.println("\nRegistered Courses for " + student.getName() + ":");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
        }
    }

    private static Student findStudentById(String id) {
        for (Student student : studentDatabase) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseByCode(String code) {
        for (Course course : courseDatabase) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }
}
