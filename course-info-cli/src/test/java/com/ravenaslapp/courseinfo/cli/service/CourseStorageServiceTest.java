package com.ravenaslapp.courseinfo.cli.service;

import com.ravenaslapp.courseinfo.domain.Course;
import com.ravenaslapp.courseinfo.repository.CourseRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseStorageServiceTest {

    @Test
    void storePluralsightCourses() {
        CourseRepository repo = new InMemoryCourseRepository();
        var service = new CourseStorageService(repo);

        var ps1 = new PluralsightCourse("1", "Title", "01:40:00.123", "/url-1", false);
        service.storePluralsightCourses(List.of(ps1));

        Course expected = new Course("1", "Title", 100, "https://app.pluralsight.com/url-1", Optional.empty());

        assertEquals(List.of(expected), repo.getAllCourses());
    }

    static class InMemoryCourseRepository implements CourseRepository {

        private final List<Course> courses = new ArrayList<>();
        @Override
        public void saveCourse(Course course) {
            courses.add(course);
        }

        @Override
        public List<Course> getAllCourses() {
            return courses;
        }

        @Override
        public void addNotes(String id, String notes) {
            throw new UnsupportedOperationException();
        }
    }
}