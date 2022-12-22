package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.entity.Organisation;
import com.codecademy.logic.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {
    @Override
    public Optional<Course> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Course> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Course course) throws SQLException {

    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {

    }
}
