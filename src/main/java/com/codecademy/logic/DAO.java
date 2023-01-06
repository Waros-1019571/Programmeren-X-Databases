package com.codecademy.logic;

import com.codecademy.entity.Organisation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

        Object get(long id);

        List<T> getAll();

        boolean create(T t);

        boolean update(T t);

        boolean delete(T t);

}
