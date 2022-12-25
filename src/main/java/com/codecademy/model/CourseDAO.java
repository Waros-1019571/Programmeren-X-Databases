package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.entity.Organisation;
import com.codecademy.logic.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {
    @Override
    public Course get(long id) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public boolean create(Course course) {
        return false;
    }

    @Override
    public boolean update(Course course) {
        return false;
    }
    @Override
    public boolean delete(Course course) {
        return false;
    }
}
