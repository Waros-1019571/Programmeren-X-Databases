package com.codecademy.model;

import com.codecademy.entity.*;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EnrollmentDAO implements DAO<Enrollment> {

    private final DBConnection dbConnection;

    public EnrollmentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Enrollment> getAll() {
        Statement statement = null;
        ResultSet result = null;
        List<Enrollment> enrollmentList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT e.StudentID, s.Email, " +
                    "e.CourseID, co.Title, " +
                    "e.EnrollmentDate, " +
                    "e.CertificateID, ce.Date, ce.Grade " +
                    "FROM ENROLLMENT AS e " +
                    "LEFT JOIN STUDENT AS s ON s.ID = e.StudentID " +
                    "LEFT JOIN COURSE AS co ON co.ID = e.CourseID " +
                    "LEFT JOIN CERTIFICATE AS ce ON ce.ID = e.CertificateID");

            while (result.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentDate(result.getDate("EnrollmentDate"));

                Student student = new Student();
                student.setId(result.getInt("StudentID"));
                student.setEmail(result.getString("email"));
                enrollment.setStudent(student);

                Course course = new Course();
                course.setCourseId(result.getInt("CourseID"));
                course.setTitle(result.getString("Title"));
                enrollment.setCourse(course);

                Certificate certificate = new Certificate();
                certificate.setCertificateId(result.getInt("CertificateID"));
                certificate.setCertificationDate(result.getDate("Date"));
                certificate.setGrade(result.getDouble("Grade"));
                if (certificate.getCertificateId() != 0) { // If no certificate ID was given, the ID is set to 0
                    enrollment.setCertificate(certificate);
                }

                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement,result);
        }
        return enrollmentList;
    }

    @Override
    public boolean create(Enrollment enrollment) {
        boolean result = false;
        PreparedStatement statement = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO ENROLLMENT (StudentID, CourseID) VALUES(?,?)");
            statement.setInt(1, enrollment.getStudent().getId());
            statement.setInt(2, enrollment.getCourse().getCourseId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoSuchElementException("Create failed: no rows affected.");
            }

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return result;
    }

    @Override
    public boolean update(Enrollment enrollment) {
        return addCertification(enrollment);
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM ENROLLMENT WHERE StudentID = ? AND CourseID = ?");
            statement.setInt(1, enrollment.getStudent().getId());
            statement.setInt(2, enrollment.getCourse().getCourseId());
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        if (rowsDeleted == 0) {
            return false;
        }
        return true;
    }

    public boolean addCertification(Enrollment enrollment) {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO CERTIFICATE (Grade, Date) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, enrollment.getCertificate().getGrade());
            statement.setDate(2, enrollment.getCertificate().getCertificationDate());
//            statement.setString(3, enrollment.getCertificate().getSignedBy());

            if (statement.executeUpdate() == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Creating certification failed, no ID obtained.");
            }

            enrollment.getCertificate().setCertificateId(generatedKeys.getInt(1));

            statement = connection.prepareStatement("UPDATE ENROLLMENT SET CertificateID = ? WHERE StudentID = ? AND CourseID = ?");
            statement.setInt(1, enrollment.getCertificate().getCertificateId());
            statement.setInt(2, enrollment.getStudent().getId());
            statement.setInt(3, enrollment.getCourse().getCourseId());
            statement.setInt(3, enrollment.getCourse().getCourseId());

            if (statement.executeUpdate() == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }
            closeRequest(statement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void closeRequest(Statement statement, ResultSet resultSet) {
        closeRequest(statement);
        try {
            resultSet.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void closeRequest(Statement statement) {
        try {
            statement.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
