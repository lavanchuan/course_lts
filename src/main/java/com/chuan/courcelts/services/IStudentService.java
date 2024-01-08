package com.chuan.courcelts.services;

import com.chuan.courcelts.models.entities.Student;

import java.util.List;

public interface IStudentService {

    int NOT_FOUND = 0;
    int SUCCESS = 1;
    int CONFLICT = 2; // other table referencing
    int IS_EXISTS = 3;
    int IS_NULL = 4;

    List<Student> findAll();

    List<Student> findByNameAndEmail(String name, String email);

    List<Student> pagingFindAll(int pageNum, int sizeOf);

    List<Student> pagingFindByNameAndEmail(String name, String email, int pageNum, int sizeOf);

    int add(Student student);

    int update(int studentId, Student student);

    int delete(int studentId, boolean isDelete);
}
