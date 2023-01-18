package com.ravenaslapp.courseinfo.cli;

import com.ravenaslapp.courseinfo.cli.service.CourseRetrievalService;
import com.ravenaslapp.courseinfo.cli.service.CourseStorageService;
import com.ravenaslapp.courseinfo.cli.service.PluralsightCourse;
import com.ravenaslapp.courseinfo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);
    public static void main(String... args) {
        LOG.info("CourseRetriever started!!");

        if(args.length == 0) {
            LOG.warn("Please provide an author name as first argument.");
            return;
        }
        try {

            retrieveCourses(args[0]);

        } catch (Exception e) {
            LOG.error("Unexpected error");
            e.printStackTrace();
        }
    }

    private static void retrieveCourses(String authorId) {
        LOG.info("Retrieving courses for author '{}'", authorId);
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();

        CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        List<PluralsightCourse> coursesToStore = courseRetrievalService.getCoursesFor(authorId)
                        .stream()
                        .filter(course -> !course.isRetired())
                        .toList();
        LOG.info("Received the following {} courses {}", coursesToStore.size(), coursesToStore);
        courseStorageService.storePluralsightCourses(coursesToStore);
    }
}
