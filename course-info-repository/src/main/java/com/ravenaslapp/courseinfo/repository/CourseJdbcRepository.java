package com.ravenaslapp.courseinfo.repository;

import com.ravenaslapp.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

class CourseJdbcRepository implements CourseRepository {

    private static final String H2_DATABASE_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";

    private static final String INSERT_COURSE = """
            MERGE INTO Courses (id, name, length, url)
                VALUES (?, ?, ?, ?)
            """;
    private static final String ADD_NOTES = """
            UPDATE Courses SET notes = ?
                WHERE id = ?                
            """;

    private final DataSource dataSource;
    public CourseJdbcRepository(String databaseFile) {
        var jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(H2_DATABASE_URL.formatted(databaseFile));

        dataSource = jdbcDataSource;
    }


    @Override
    public void saveCourse(Course course) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, course.id());
            statement.setString(2, course.name());
            statement.setLong(3, course.length());
            statement.setString(4, course.url());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save " + course, e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM COURSES");

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                var course = new Course(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4),
                        Optional.ofNullable(resultSet.getString(5)));
                courses.add(course);
            }
            return Collections.unmodifiableList(courses);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to retrieve courses", e);
        }
    }

    @Override
    public void addNotes(String id, String notes) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement(ADD_NOTES);
            statement.setString(1, notes);
            statement.setString(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to add notes to " + id, e);
        }
    }
}
