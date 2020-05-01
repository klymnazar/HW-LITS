package com.lits.springboot.repository;

import com.lits.springboot.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

    Optional<Course> findOneById(Integer id);

    List<Course> findAll();

    List<Course> findAllByStartDateAfterOrderByStartDate(LocalDate startDate);

    List<Course> findAllByEndDateBeforeOrderByEndDate(LocalDate endDate);

    List<Course> findAllByStartDateBeforeAndEndDateAfterOrderByStartDate(LocalDate startDate, LocalDate endDate);

    @Query("select c from Course c where datediff(c.endDate, c.startDate) > ?1 and datediff(c.endDate, c.startDate) < ?2")
    List<Course> findAllCoursesDurationMonths(Integer startDays, Integer endDays);

    List<Course> findAllByOrderByStartDateAsc();

}
