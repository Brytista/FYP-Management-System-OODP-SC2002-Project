package package1;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

public class InitializeApplication {

    private static List<Student> studentList = new ArrayList<>();
    private static List<Supervisor> supervisorList = new ArrayList<>();
    private static List<FYPCoordinator> fypCoordinatorList = new ArrayList<>();
    private static List<Project> projectList = new ArrayList<>();

    public InitializeApplication() {}

    // Load all the lists from the CSV files and assign them to the respective classes
    public static int assignAllLists() {
        int loaded = InitializeApplication.loadAllLists();

        if (loaded == 1) {
            Student.assignStudentsList(studentList);
            Supervisor.assignSupervisorsList(supervisorList);
            FYPCoordinator.assignFYPCoordinatorsList(fypCoordinatorList);
            Project.assignProjectList(projectList);

            System.out.println("Lists loaded successfully");
            return 1;

        } else {
            System.out.println("Error: Unable to load lists");
            return 0;
        }
    }

    // Load the Student list from the CSV file
    public static int loadStudentList() {

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/studentList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                Student student = new Student(userID, password, name, email);

                // If projectID is greater than 0, assign the student's project
                int projectID = Integer.parseInt(fields[4]);
                if (projectID > 0) {
                    Project project = Project.getProjectByID(projectID);
                    student.changeProject(project);
                }

                // Assign the student's requestProject boolean
                boolean requestProject = Boolean.parseBoolean(fields[5]);
                student.setRequestProject(requestProject);

                // Assign the student's isDeregistered boolean
                boolean isDeregistered = Boolean.parseBoolean(fields[6]);
                student.setIsDeregistered(isDeregistered);

                studentList.add(student);

                count++;
            }

            // Print a message if no student data is found in the file
            if (count == 0) {
                System.out.println("No student data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // Load the supervisor list from the CSV file
    public static int loadSupervisorList() {

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/supervisorList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                Supervisor supervisor = new Supervisor(userID, password, name, email);

                // Assign the supervisor's numberOfProjectManaged
                int numberOfProjectManaged = Integer.parseInt(fields[4]);
                if (numberOfProjectManaged > 0) {
                    supervisor.numberOfProjectManaged = numberOfProjectManaged;
                }

                // Assign the supervisor's managed students
                String[] retrievedStudentsManaged = fields[5].split(";");
                if (retrievedStudentsManaged[0] != "None") {
                    List<Student> studentsManaged = new ArrayList<Student>();

                    for (String studentID : retrievedStudentsManaged) {
                        Student student = Student.getStudentByID(studentID);
                        studentsManaged.add(student);
                    }

                    supervisor.assignStudentManaged(studentsManaged);
                }

                supervisorList.add(supervisor);

                count++;
            }

            // Print a message if no supervisor data is found in the file
            if (count == 0) {
                System.out.println("No supervisor data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;

    }

    // Load the FYP Coordinator list from the CSV file
    public static int loadFYPCoordinatorList() {

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/fypCoordinatorList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                FYPCoordinator fypCoordinator = new FYPCoordinator(userID, password, name, email);
                fypCoordinatorList.add(fypCoordinator);

                count++;
            }

            // Print a message if no FYP Coordinator data is found in the file
            if (count == 0) {
                System.out.println("No FYP Coordinator data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;

    }

    // Load the project list from the CSV file
    public static int loadProjectList() {

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/projectList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                Supervisor supervisor = Supervisor.getSupervisorByID(fields[1]);
                String projectTitle = fields[2];

                // Create a new project object
                Project project = new Project(supervisor, projectTitle);

                // Fetch the project status and student
                ProjectStatus projectStatus = ProjectStatus.valueOf(fields[3]);
                Student student = Student.getStudentByID(fields[4]);

                // Assign the project's projectID, projectStatus and student
                project.projectID = Integer.parseInt(fields[0]);
                project.projectStatus = projectStatus;
                project.student = student;
                
                projectList.add(project);

                count++;
            }

            // Print a message if no project data is found in the file
            if (count == 0) {
                System.out.println("No project data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;

    }

    public static int loadAllLists() {

        int loadedStudents = InitializeApplication.loadStudentList();
        int loadedSupervisors = InitializeApplication.loadSupervisorList();
        int loadedFYPCoordinators = InitializeApplication.loadFYPCoordinatorList();
        int loadedProjects = InitializeApplication.loadProjectList();

        int result;
        if (loadedStudents == 1 && loadedSupervisors == 1 && loadedFYPCoordinators == 1 && loadedProjects == 1) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

}
