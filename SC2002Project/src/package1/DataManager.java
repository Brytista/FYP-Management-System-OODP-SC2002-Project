package package1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles data management for the application, including updating
 * the CSV files for students, supervisors, FYP coordinators, and projects.
 */
public class DataManager {

     /**
     * Updates all CSV files and returns the result.
     *
     * @return 1 if all files are successfully updated, 0 otherwise.
     */
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

    /**
     * Updates the studentList.csv file with the latest student data.
     *
     * @return 1 if the file is successfully updated, 0 otherwise.
     */
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

     /**
     * Updates the supervisorList.csv file with the latest supervisor data.
     *
     * @return 1 if the file is successfully updated, 0 otherwise.
     */
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

                if (supervisor.getStudentsManaged().isEmpty()) {
                    studentManaged.add("None;");

                } else {
                    for (Student student : supervisor.getStudentsManaged()) {
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

     /**
     * Updates the fypCoordinatorList.csv file with the latest FYP coordinator data.
     *
     * @return 1 if the file is successfully updated, 0 otherwise.
     */
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

     /**
     * Updates the projectList.csv file with the latest project data.
     *
     * @return 1 if the file is successfully updated, 0 otherwise.
     */
    public static int updateProjects() {

        String filePath = Paths.get("files/projectList.csv").toString();

        List<Project> projects = Project.getProjectList();

        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Project project : projects) {

                // index 0: projectID
                String userID = String.valueOf(project.projectID);
                // index 1: supervisorID
                String supervisorID = project.supervisor.getUserID();
                // index 2: projectTitle
                String name = project.getProjectTitle();
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


