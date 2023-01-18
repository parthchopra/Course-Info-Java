package com.ravenaslapp.courseinfo.repository;

import com.ravenaslapp.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepository {
    void saveCourse(Course course);
    List<Course> getAllCourses();

    void addNotes(String id, String notes);

    static CourseRepository openCourseRepository (String databaseFile) {
        return new CourseJdbcRepository(databaseFile);
    }
}
