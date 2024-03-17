/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author Zy
 */
import Utility.*;
import Entity.Student;
import java.sql.*;
import adt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import javax.servlet.ServletContext;

public class StudentDao {

    // Create an ArrayList to store Student objects
    private static ListInterface<Student> sList = new ArrayList<>();

    // add new student
    public static void addStudent(Student s) {
        sList.add(s);
        
    }

    // get all student details
    public static ListInterface<Student> getAllStudents() {
        return sList;
    }

    // delete student
    // cannot directly student but move it into withdraw List
    public static boolean deleteStudent(String id) {
        // Check if the student ID is available
        int index = getIndex(id);
        if (index != -1) {
            // Remove the student from the list
            sList.remove(index);
            return true; // Deletion successful
        } else {
            return false; // Student not found
        }
    }

    // update student
    public static boolean updateStudent(String id, Student updatedStudent) {
        // Find the index of the student with the given ID
        int index = getIndex(id);

        // If the student is found, update its information
        if (index != -1) {
            // Replace the student at the found index with the updated student
            sList.replace(index, updatedStudent);
            return true; // Return true to indicate a successful update
        }

        // If the student is not found, return false
        return false;
    }

    // check the availability of the id 
    public static boolean availableId(String id) {
        // return value if the index number is available
        return getIndex(id) >= 0;
    }

    // check the index number based on id
    public static int getIndex(String id) {
        // Trim the provided ID to remove leading and trailing whitespace
        String trimmedId = id.trim();

        for (int i = 1; i <= sList.getNumberOfEntries(); i++) {
            Student s = sList.getEntry(i);
            if (s != null) {
                if (s.getId().equals(trimmedId)) {
                    // if equals, return the index number
                    return i;
                }
            }
        }
        return -1;
    }

    // get student info by id
    public static Student getStudentById(String id) {
        // Iterate over the list of students to find the one with the matching ID
        for (int i = 1; i <= sList.getNumberOfEntries(); i++) {
            Student s = sList.getEntry(i);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        // Return null if no student with the given ID is found
        return null;
    }

    // search students name
    public static ListInterface<Student> searchStudent(String name) {
        ListInterface<Student> searchResults = new ArrayList<>();
        // Loop through all students to find matches
        for (int i = 1; i <= sList.getNumberOfEntries(); i++) {
            Student s = sList.getEntry(i);
            // Check if the student's name contains the search term (case-insensitive)
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(s);
            }
        }
        return searchResults;
    }

    
// -------------------------------------------------------------------------------------------------------------------
    // File
    // save record to file  
    public static void saveToFile(ListInterface<Student> sList, ServletContext context) {
        // Get the real path for the file within the servlet context
        String fileName = context.getRealPath("/File/studentsRecords.txt");
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(sList);
            ooStream.close();
            System.out.println("\nRecords saved to file successfully.");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file: " + ex.getMessage());
        }
    }

    // read student records from file
    public static ListInterface<Student> retrieveFromFile() {
        String fileName = "studentsRecord"; // Define fileName within the method
        File file = new File(fileName);
        ListInterface<Student> studentList = new ArrayList<>();
        try {
            if (!file.exists()) {
                System.out.println("File does not exist.");
                return studentList;
            }

            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            studentList = (ListInterface<Student>) oiStream.readObject();
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found: " + ex.getMessage());
        }
        return studentList; // Return the retrieved list
    }

// -----------------------------------------------------------------------------------------------------------------
// Database
    public static int addStudentDatabase(Student s) {
        int status = 0;
        String sql = "INSERT INTO `students` (`id`, `name`,`ic`,`gender`,`email`,paymentStatus,`programmeId`) VALUES (?, ?,?,?,?,?,?);";
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ps.setString(1, s.getId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getIc());
            ps.setString(4, s.getGender());
            ps.setString(5, s.getEmail());
            // TODO
            ps.setInt(6, 0);
            ps.setString(7, "P0001");

            status = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int updateStudent(Student s) {
        int status = 0;
        String sql = "UPDATE `students` SET `name` = ?,`ic` = ?,`gender`=?,`email`=?,paymentStatus=?,`programmeId`=?) VALUES (?, ?,?,?,?,?,?);";
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ps.setString(1, s.getName());
            ps.setString(2, s.getIc());
            ps.setString(3, s.getGender());
            ps.setString(4, s.getEmail());
            // TODO
            ps.setInt(5, 0);
            ps.setString(6, "P0001");

            status = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int deleteStudentDatabase(int id) {
        int status = 0;
        String sql = "DELETE FROM `students` WHERE id = " + id + ";";
        try {
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ps.setInt(1, id);

            status = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

//    public static Student getStudentByIdDatabase(int id) {
//        Student s = new Student();
//
//        String sql = "SELECT * FROM `students` WHERE id = " + id + ";";
//        try {
//            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                s.setId(rs.getString(1));
//                s.setName(rs.getString(2));
//                s.setIc(rs.getString(3));
//                s.setGender(rs.getString(4));
//                s.setEmail(rs.getString(5));
//                s.setPaymentStatus(rs.getInt(6));
//                s.setProgrammeId(rs.getString(7));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return s;
//    }
//
//    public static ArrayList<Student> getAllStudentsDatabase() {
//        ArrayList<Student> sList = new ArrayList<>();
//        String sql = "SELECT * FROM `Artist`;";
//        try {
//            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
//            ResultSet rs = ps.executeQuery(sql);
//            while (rs.next()) {
//                Student s = new Student();
//                s.setId(rs.getString(1));
//                s.setName(rs.getString(2));
//                s.setIc(rs.getString(3));
//                s.setGender(rs.getString(4));
//                s.setEmail(rs.getString(5));
//                s.setPaymentStatus(rs.getInt(6));
//                s.setProgrammeId(rs.getString(7));
//
//                sList.add(s);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return sList;
//    }
//
}
