package com.arnaldopiccinelli.schoolregistrationsystem.student;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.Hibernate;

import com.arnaldopiccinelli.schoolregistrationsystem.base.BaseEntity;
import com.arnaldopiccinelli.schoolregistrationsystem.course.Course;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "students")
public class Student extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String identifier;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@ToString.Exclude
	@ManyToMany(mappedBy="students")
	@OrderBy("name")
	private Set<Course> courses = new HashSet<>();

	public Student addCourse(@NonNull final Course course) {
		courses.add(course);
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
		Student student = (Student) o;
		return id != null && Objects.equals(id, student.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
