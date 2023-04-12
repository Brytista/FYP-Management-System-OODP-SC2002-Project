package package1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static int updateAll() {

        int updateStudentsResult = updateStudents();
        int updateSupervisorsResult = updateSupervisors();
        int updateFYPCoordinatorsResult = updateFYPCoords();
        int updateProjectsResult = updateProjects();

        if (updateStudentsResult == 1 && updateSupervisorsResult == 1 && updateFYPCoordinatorsResult == 1
                && updateProjectsResult == 1) {
            return 1;
        } else {
            System.out.println("Error: Unable to update all files");
            return 0;
        }
    }

    // Update studentList.csv file
    public static int updateStudents() {

        String filePath = Paths.get("files/studentList.csv").toString();

        List<Student> students = Student.getStudents();

        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : students) {

                // index 0: userID
                String userID = student.getUserID();
                // index 1: password
                String password = student.getPassword();
                // index 2: name
                String name = student.getUserName();
                // index 3: email
                String email = student.getEmail();
                // index 4: projectID
                String projectID = String.valueOf(student.getProjectID());
                // index 5: requestProject
                String requestProject = String.valueOf(student.returnRequestProject());
                // index 6: isDeregistered
                String isDeregistered = String.valueOf(student.getIsDeregistered());

                csvWriter.write(
                        String.join(",", userID, password, name, email, projectID, requestProject, isDeregistered));
                csvWriter.newLine();
            }

            System.out.println("Successfully saved data to studentList.csv file");

        } catch (Exception e) {
            System.err.println("Error occurred while updating studentList.csv file: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // Update supervisorList.csv file
    public static int updateSupervisors() {

        String filePath = Paths.get("files/supervisorList.csv").toString();

        List<Supervisor> supervisors = Supervisor.getSupervisors();

        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Supervisor supervisor : supervisors) {

                // index 0: userID
                String userID = supervisor.getUserID();
                // index 1: password
                String password = supervisor.getPassword();
                // index 2: name
                String name = supervisor.getUserName();
                // index 3: email
                String email = supervisor.getEmail();
                // index 4: numOfProjects
                String numOfProjects = String.valueOf(supervisor.numberOfProjectManaged);
                // index 5: studentsManaged
                List<String> studentManaged = new ArrayList<>();

                if (supervisor.studentManaged.isEmpty()) {
                    studentManaged.add("None;");

                } else {
                    for (Student student : supervisor.studentManaged) {
                        String studentID;

                        if (student != null) {
                            studentID = student.getUserID();
                        } else {
                            studentID = "None;";
                        }
                        
                        studentManaged.add(studentID);
                    }
                }

                String studentManagedJoined = String.join(";", studentManaged);

                csvWriter.write(String.join(",", userID, password, name, email, numOfProjects, studentManagedJoined));
                csvWriter.newLine();
            }

            System.out.println("Successfully saved data to supervisorList.csv file");

        } catch (Exception e) {
            System.err.println("Error occurred while updating supervisorList.csv file: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // Update fypCoordinatorList.csv file
    public static int updateFYPCoords() {

        String filePath = Paths.get("files/fypCoordinatorList.csv").toString();

        List<FYPCoordinator> fypCoords = FYPCoordinator.getFYPCoordinators();

        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (FYPCoordinator fypCoord : fypCoords) {

                // index 0: userID
                String userID = fypCoord.getUserID();
                // index 1: password
                String password = fypCoord.getPassword();
                // index 2: name
                String name = fypCoord.getUserName();
                // index 3: email
                String email = fypCoord.getEmail();

                csvWriter.write(String.join(",", userID, password, name, email));
                csvWriter.newLine();
            }

            System.out.println("Successfully saved data to fypCoordinatorList.csv file");

        } catch (Exception e) {
            System.err.println("Error occurred while updating fypCoordinatorList.csv file: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // Update projectList.csv file
    public static int updateProjects() {

        String filePath = Paths.get("files/projectList.csv").toString();

        List<Project> projects = Project.projectList;

        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Project project : projects) {

                // index 0: projectID
                String userID = String.valueOf(project.projectID);
                // index 1: supervisorID
                String supervisorID = project.supervisor.getUserID();
                // index 2: projectTitle
                String name = project.projectTitle;
                // index 3: projectStatus
                String projectStatus = String.valueOf(project.projectStatus);
                // index 4: studentID
                String studentID;
                try {
                    studentID = project.student.getUserID();
                } catch (Exception e) {
                    studentID = "None";
                }

                csvWriter.write(String.join(",", userID, supervisorID, name, projectStatus, studentID));
                csvWriter.newLine();
            }

            System.out.println("Successfully saved data to projectList.csv file");

        } catch (Exception e) {
            System.err.println("Error occurred while updating projectList.csv file: " + e.getMessage());
            return 0;
        }

        return 1;
    }

}
