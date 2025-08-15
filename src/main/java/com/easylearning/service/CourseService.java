package com.easylearning.service;

import com.easylearning.dto.CourseDto;
import com.easylearning.dto.LessonDto;
import com.easylearning.model.Course;
import com.easylearning.repository.CourseDao;
import com.easylearning.repository.LessonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private LessonDao lessonDao;

    public List<CourseDto> getAllCourses() {
        return courseDao.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Course course = courseDao.findById(id);
        return course != null ? convertToDto(course) : null;
    }

    public Course createCourse(Course course) {
        return courseDao.save(course);
    }

    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    public void deleteCourse(Long id) {
        courseDao.deleteById(id);
    }

    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setLevel(course.getLevel());
        dto.setCreatedAt(course.getCreatedAt());

        // Load lessons for this course
        List<LessonDto> lessons = lessonDao.findByCourseId(course.getId()).stream()
            .map(lesson -> {
                LessonDto lessonDto = new LessonDto();
                lessonDto.setId(lesson.getId());
                lessonDto.setTitle(lesson.getTitle());
                lessonDto.setContent(lesson.getContent());
                lessonDto.setOrderIndex(lesson.getOrderIndex());
                return lessonDto;
            })
            .collect(Collectors.toList());
        
        dto.setLessons(lessons);
        return dto;
    }
}
