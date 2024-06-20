package com.virtusa.questor.dao;

import com.virtusa.questor.dto.CourseDTO;
import com.virtusa.questor.model.Category;
import com.virtusa.questor.model.Course;
import com.virtusa.questor.repository.CategoryRepository;
import com.virtusa.questor.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseDAO {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseSectionDAO courseSectionDAO;

    public CourseDTO save(CourseDTO courseDTO) {
        Course courseModel = toModel(courseDTO);
        Category categoryModel = categoryRepository.findById(courseDTO.getCategoryId()).orElse(null);
        if (categoryModel != null){
            courseModel.setCategory(categoryModel);
            courseModel = courseRepository.save(courseModel);
            return toDTO(courseModel);
        } else {
            throw new IllegalArgumentException("Category not found: " + courseDTO.getCategoryId());
        }
    }

    public CourseDTO findById(Long id) {
        Course courseModel = courseRepository.findById(id).orElse(null);
        return courseModel != null ? toDTO(courseModel) : null;
    }

    public List<CourseDTO> findAll() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            courseRepository.delete(course);
        } else {
            throw new IllegalArgumentException("Course not found: " + id);
        }
    }

    public List<CourseDTO> findByCategoryId(Long categoryId){
        List<Course> courses = courseRepository.findByCategoryId(categoryId);
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CourseDTO updateById(Long id, CourseDTO courseDTO){
        Course exisitingCourse = courseRepository.findById(id).orElse(null);
        if (exisitingCourse != null){
            Course updatedCourse = toModel(courseDTO);
            updatedCourse.setCourseId(id);
            updatedCourse = courseRepository.save(updatedCourse);
            return toDTO(updatedCourse);
        } else {
            throw new IllegalArgumentException("Course not found: " + id);
        }
    }

    public CourseDTO updateCourse(CourseDTO courseDTO){
        Course exisitingCourse = courseRepository.findById(courseDTO.getCourseId()).orElse(null);
        if (exisitingCourse != null){
            Course updatedCourse = toModel(courseDTO);
            updatedCourse = courseRepository.save(updatedCourse);
            return toDTO(updatedCourse);
        } else {
            throw new IllegalArgumentException("Course not found: " + courseDTO.getCourseId());
        }
    }

    public CourseDTO toDTO(Course courses){
        return CourseDTO.builder()
                .courseId(courses.getCourseId())
                .courseName(courses.getCourseName())
                .description(courses.getDescription())
                .duration(courses.getDuration())
                .categoryId(courses.getCategory() != null ? courses.getCategory().getCategoryId() : null)
                .courseSections(courses.getSections() != null ? courses.getSections().stream().map(courseSectionDAO::toDTO).collect(Collectors.toList()) : null)
                .build();
    }

    public Course toModel(CourseDTO courseDTO){
        Course course = Course.builder()
                .courseId(courseDTO.getCourseId())
                .courseName(courseDTO.getCourseName())
                .description(courseDTO.getDescription())
                .duration(courseDTO.getDuration())
                .build();

        if(courseDTO.getCategoryId() != null){
            Category categoryModel = categoryRepository.findById(courseDTO.getCategoryId()).orElse(null);
            if (categoryModel != null) {
                course.setCategory(categoryModel);
            }
        }
        return course;
    }

}
