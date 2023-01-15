package com.codecademy.model;

import com.codecademy.entity.*;
import com.codecademy.entity.Module;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleProgressDAO implements DAO<ModuleProgress> {
    private DBConnection dbConnection;

    public ModuleProgressDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ModuleProgress> getAll() {
        return null;
    }

    @Override
    public boolean create(ModuleProgress moduleProgress) {
        PreparedStatement statement = null;
        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO Student_Module (ModuleID, StudentID, Progress) VALUES (?,?,?)");
            statement.setInt(1, moduleProgress.getModule().getID());
            statement.setInt(2, moduleProgress.getStudent().getId());
            statement.setInt(3, moduleProgress.getProgress());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return false;
    }

    @Override
    public boolean update(ModuleProgress moduleProgress) {
        return false;
    }

    @Override
    public boolean delete(ModuleProgress moduleProgress) {
        return false;
    }

    public List<ModuleProgress> getAll(Student student, Course course) {
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<ModuleProgress> moduleProgressList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT m.ID AS ModuleID, m.title AS moduleTitle, st.Progress " +
                    "FROM Module AS m " +
                    "FULL JOIN Student_Module AS st ON m.ID = st.ModuleID " +
                    "JOIN Course AS co ON co.ID = m.CourseID AND co.ID = ?");
            statement.setInt(1, course.getCourseId());
            result = statement.executeQuery();

            while (result.next()) {
                ModuleProgress moduleProgress = new ModuleProgress();
                Module module = new Module();
                module.setID(result.getInt("ModuleID"));
                module.setTitle(result.getString("moduleTitle"));
                module.setCourse(course);
                moduleProgress.setStudent(student);
                moduleProgress.setModule(module);
                moduleProgress.setProgress(result.getByte("Progress"));
                moduleProgressList.add(moduleProgress);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return moduleProgressList;
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
