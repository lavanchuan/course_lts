package com.chuan.courcelts.services;

import com.chuan.courcelts.advices.exceptions.StudentNotFoundException;
import com.chuan.courcelts.models.entities.Student;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService{

    @Autowired
    public DbContext dbContext;

    @Override
    public List<Student> findAll(){
        return dbContext.studentRepository.findAll();
    }

    @Override
    public List<Student> findByNameAndEmail(String name, String email){
        List<Student> result = findAll();

        if(name != null) result = result.stream()
                .filter(student -> student.getFullname().toLowerCase().indexOf(name) != -1)
                .collect(Collectors.toList());

        if(email != null) result = result.stream()
                .filter(student -> student.getEmail().toLowerCase().indexOf(email) != -1)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<Student> pagingFindAll(int pageNum, int sizeOf){
        Paginator<Student> paginator = new Paginator<>(findAll());
        return paginator.pageOf(pageNum, sizeOf);
    }

    @Override
    public List<Student> pagingFindByNameAndEmail(String name, String email, int pageNum, int sizeOf){
        Paginator<Student> paginator = new Paginator<>(findByNameAndEmail(name, email));
        return paginator.pageOf(pageNum, sizeOf);
    }

    @Override
    public int add(Student student){
        if(student.getStudentId() != 0 && dbContext.studentRepository.existsById(student.getStudentId()))
            return CONFLICT;

        // check null email and phone
        if(student.getEmail() == null || student.getPhoneNumber() == null)
            return IS_NULL;


        // check unique phoneNumber and email
        if(dbContext.studentRepository.findAll().stream().filter(student1 -> student1.getEmail().equals(student.getEmail()))
                        .collect(Collectors.toList()).size() != 0 ||
                dbContext.studentRepository.findAll().stream().filter(student1 -> student1.getPhoneNumber().equals(student.getPhoneNumber()))
                    .collect(Collectors.toList()).size() != 0)
            return IS_EXISTS;

        student.setFullname(student.getFullname().trim());
        dbContext.studentRepository.save(student);
        return SUCCESS;
    }

    @Override
    public int update(int studentId, Student student){
        if(!dbContext.studentRepository.existsById(studentId)) return NOT_FOUND;

        // check email/phone if not null -- continue email or phone input equals
        if(student.getEmail() != null &&
        dbContext.studentRepository.findAll().stream().filter(student1 -> student1.getEmail().equals(student.getEmail()) && studentId != student1.getStudentId())
                .collect(Collectors.toList()).size() != 0
        || student.getPhoneNumber() != null &&
        dbContext.studentRepository.findAll().stream().filter(student1 -> student1.getPhoneNumber().equals(student.getPhoneNumber()) && studentId != student1.getStudentId())
                .collect(Collectors.toList()).size() != 0)
            return IS_EXISTS;

        dbContext.studentRepository.findById(studentId)
                .map(student1 -> {
                    // TODO update fields
                    if(student.getFullname() != null) student1.setFullname(student.getFullname());
                    if(student.getDistrict() != null) student1.setDistrict(student.getDistrict());
                    if(student.getProvince() != null) student1.setProvince(student.getProvince());
                    if(student.getWards() != null) student1.setWards(student.getWards());
                    if(student.getHouseNum() != null) student1.setHouseNum(student.getHouseNum());
                    if(student.getImage() != null) student1.setImage(student.getImage());
                    if(student.getBirthday() != null) student1.setBirthday(student.getBirthday());
                    if(student.getPhoneNumber() != null) student1.setPhoneNumber(student.getPhoneNumber());
                    if(student.getEmail() != null) student1.setEmail(student.getEmail());

                    return dbContext.studentRepository.save(student1);
                }).orElseThrow(()->new StudentNotFoundException(studentId));
        return SUCCESS;
    }

    @Override
    public int delete(int studentId, boolean isDelete){
        if(!dbContext.studentRepository.existsById(studentId)) return NOT_FOUND;
        int countRecords = dbContext.registerRepository.countRecordsByStudentId(studentId);
        if(countRecords > 0){
            if(isDelete) {
                dbContext.registerRepository.deleteRegistersByStudentId(studentId);
                dbContext.studentRepository.deleteById(studentId);
                return SUCCESS;
            } else return CONFLICT;
        }

        dbContext.studentRepository.deleteById(studentId);
        return SUCCESS;
    }
}
