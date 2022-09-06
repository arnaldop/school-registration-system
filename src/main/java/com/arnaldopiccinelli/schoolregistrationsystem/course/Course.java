package com.arnaldopiccinelli.schoolregistrationsystem.course;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.Hibernate;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseEntity;
import com.arnaldopiccinelli.schoolregistrationsystem.student.Student;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String identifier;

	@Column
	private String name;

	@Column
	private String description;

	@ManyToMany
	@JoinTable(name = "courses_students",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id",
					referencedColumnName = "id"))
	@OrderBy("lastName ASC, firstName ASC")
	private Set<Student> students = new HashSet<>();

	public Course addStudent(@NonNull final Student student) {
		students.add(student);
		student.addCourse(this);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(
				this) != Hibernate.getClass(o)) {
			return false;
		}
		Course course = (Course) o;
		return id != null && Objects.equals(id, course.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
