package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.entity.Student;
import com.codecademy.entity.Webcast;
import com.codecademy.entity.WebcastProgression;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WebcastProgressionDAO implements DAO<WebcastProgression> {

    private final DBConnection dbConnection;

    public WebcastProgressionDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<WebcastProgression> getAll() {
        Statement statement = null;
        ResultSet result = null;
        List<WebcastProgression> webcastProgressionList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT p.StudentID, s.Name, p.WebcastID, w.Title, p.Progress FROM STUDENT_WEBCAST AS p JOIN STUDENT AS s ON s.ID = p.StudentID JOIN WEBCAST as w ON w.ID = p.WebcastID");

            while (result.next()) {
                WebcastProgression webcastProgression = new WebcastProgression();

                Student student = new Student();
                student.setId(result.getInt(1));
                student.setName(result.getString(2));
                webcastProgression.setStudent(student);

                Webcast webcast = new Webcast();
                webcast.setId(result.getInt(3));
                webcast.setTitle(result.getString(4));
                webcastProgression.setWebcast(webcast);

                webcastProgression.setProgress(result.getInt(5));
                webcastProgressionList.add(webcastProgression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement,result);
        }
        return webcastProgressionList;
    }

    @Override
    public boolean create(WebcastProgression webcastProgression) {
        boolean result = false;
        PreparedStatement statement = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO STUDENT_WEBCAST (StudentID, WebcastID, Progress) VALUES(?,?,?)");
            statement.setInt(1, webcastProgression.getStudent().getId());
            statement.setInt(2, webcastProgression.getWebcast().getId());
            statement.setDouble(3, webcastProgression.getProgress());

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
    public boolean update(WebcastProgression webcastProgression) {
        return false;
    }

    public boolean update(WebcastProgression webcastProgression, Course course) {
        boolean result = false;
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE STUDENT_WEBCAST SET Progress = ? WHERE StudentID = ? AND WebcastID = " +
                    "(SELECT WebcastID FROM Webcast WHERE CourseID = ?)");
            statement.setDouble(1, webcastProgression.getProgress());
            statement.setInt(2, webcastProgression.getStudent().getId());
            statement.setInt(3, course.getCourseId());

            if (statement.executeUpdate() == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }
            result = true;
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return result;
    }

    @Override
    public boolean delete(WebcastProgression webcastProgression) {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM STUDENT_WEBCAST WHERE StudentID = ? AND WebcastID = ?");
            statement.setInt(1, webcastProgression.getStudent().getId());
            statement.setInt(2, webcastProgression.getWebcast().getId());
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

    public double getProgress(Student student, Course course) {
        PreparedStatement statement = null;
        ResultSet result = null;
        double progress = 0;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT sw.Progress " +
                    "FROM Student_Webcast AS sw " +
                    "JOIN Webcast AS w ON w.ID = sw.WebcastID " +
                    "JOIN Course AS co ON co.ID = w.CourseID AND co.ID = ? AND sw.StudentID = ?");
            statement.setInt(1, course.getCourseId());
            statement.setInt(2, student.getId());

            result = statement.executeQuery();
            if (result.next()) {
                progress = result.getInt(1);
            }
            return progress;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return progress;
    }

    public boolean saveProgression(WebcastProgression webcastProgression, Course course) {
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            Connection connection = dbConnection.getConnection();
    statement = connection.prepareStatement("SELECT COUNT(*) FROM Webcast AS w JOIN Student_Webcast AS sw ON w.ID = sw.WebcastID AND sw.StudentID = ? AND w.CourseID = ?");
            statement.setInt(1, course.getCourseId());
            statement.setInt(2, webcastProgression.getStudent().getId());

            result = statement.executeQuery();

            if (result.next()) {
                if (result.getInt(1) > 1) {
                    return create(webcastProgression);
                }
                return update(webcastProgression, course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement,result);
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
