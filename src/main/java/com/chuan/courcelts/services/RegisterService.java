package com.chuan.courcelts.services;

import com.chuan.courcelts.advices.exceptions.CourceNotFoundException;
import com.chuan.courcelts.advices.exceptions.RegisterNotFoundException;
import com.chuan.courcelts.advices.exceptions.StatusStudyNotFoundException;
import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.RegisterDTO;
import com.chuan.courcelts.models.entities.Cource;
import com.chuan.courcelts.models.entities.Register;
import com.chuan.courcelts.models.entities.Student;
import com.chuan.courcelts.repositories.dbcontexts.DbContext;
import com.chuan.courcelts.services.mappers.RegisterMapper;
import com.chuan.courcelts.services.paginators.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterService implements IRegisterService{

    @Autowired
    public DbContext dbContext;

    @Override
    public List<RegisterDTO> findAll(){
        return dbContext.registerRepository.findAll().stream().map(RegisterMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public StatusReturn add(RegisterDTO dto){
        if(dto.getRegisterId() > 0 && dbContext.registerRepository.existsById(dto.getRegisterId())) return StatusReturn.CONFLICT;

        if(dto.getStudentId() <= 0 || dto.getCourceId() <= 0)
            return StatusReturn.INVALID2;

        if(!dbContext.studentRepository.existsById(dto.getStudentId()))
            return StatusReturn.IS_NOT_FOUND;
        if(!dbContext.courceRepository.existsById(dto.getCourceId()))
            return StatusReturn.IS_NOT_FOUND;

        if(dbContext.courceRepository.counterStudentCource(dto.getCourceId(), dto.getStudentId()) > 0)
            return StatusReturn.CONFLICT;

        if(dto.getAccountId() > 0 && !dbContext.courceRepository.existsById(dto.getCourceId()))
            return StatusReturn.IS_NOT_FOUND;
        if(dto.getStatusId() > 0 && !dbContext.statusRepository.existsById(dto.getStatusId()))
            return StatusReturn.IS_NOT_FOUND;

        setupDate(dto);

        if(dto.getStartDate() != null && dto.getStartDate().compareTo(dto.getRegisDate()) < 0)
            return StatusReturn.INVALID;
        if(dto.getEndDate() != null){
            if(dto.getStartDate() != null && dto.getEndDate().compareTo(dto.getStartDate()) < 0)
                return StatusReturn.INVALID;
            if(dto.getEndDate().compareTo(dto.getRegisDate()) < 0)
                return StatusReturn.INVALID;
        }

        dbContext.registerRepository.save(RegisterMapper.toEntity(dto, dbContext));

        if(dto.getStatusId() > 0 && dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow()
                .getStatusName().equals(IStatusService.DANG_HOC_CHINH)){
            dbContext.courceRepository.findById(dto.getCourceId()).map(cource ->{
                cource.setTotalStudent(dbContext.courceRepository.counterStudent(dto.getCourceId(), IStatusService.DANG_HOC_CHINH));
                return dbContext.courceRepository.save(cource);
            }).orElseThrow();
        }

        return StatusReturn.SUCCESS;
    }

    private void setupDate(RegisterDTO dto) {
        if(dto.getRegisDate() == null) dto.setRegisDate(LocalDate.now());

        if(dto.getStartDate() == null && dto.getEndDate() == null
        && dto.getStatusId() > 0){
            if(dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow()
                    .getStatusName().equals(IStatusService.CHO_DUYET)
                    || dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow()
                    .getStatusName().equals(IStatusService.DANG_HOC_CHINH)){
                dto.setStartDate(LocalDate.now());
            }
        }

        if(dto.getStartDate() != null && dto.getEndDate() == null){
            dto.setEndDate(dto.getStartDate().plusDays(dbContext.courceRepository.findById(dto.getCourceId())
                    .orElseThrow().getTimer()));
        } else if(dto.getStartDate() == null && dto.getEndDate() != null){
            dto.setStartDate(dto.getEndDate().plusDays(-1 * dbContext.courceRepository.findById(dto.getCourceId())
                    .orElseThrow().getTimer()));
        }
    }
    private void setupDate(Register entity) {
    }

    @Override
    public StatusReturn update(int registerId, RegisterDTO dto){

        Register register = dbContext.registerRepository.findById(registerId)
                        .orElseThrow(()->new RegisterNotFoundException(registerId));

        if(dto.getCourceId() > 0 && !dbContext.courceRepository.existsById(dto.getCourceId()) ||
                dto.getStatusId() > 0 && !dbContext.statusRepository.existsById(dto.getStatusId()) ||
                dto.getStudentId() > 0 && !dbContext.studentRepository.existsById(dto.getStudentId()) ||
                dto.getAccountId() > 0 && !dbContext.accountRepository.existsById(dto.getAccountId())){
            return StatusReturn.IS_NOT_FOUND;
        }

        if(dto.getStudentId() > 0 && dto.getStudentId() != register.getStudent().getStudentId()){
            if(dbContext.courceRepository.counterStudentCource(dto.getCourceId(), dto.getStudentId()) > 0)
                return StatusReturn.CONFLICT;
        }

        Cource cource = register.getCource();

        if(dto.getRegisDate() != null) {
            register.setRegisDate(dto.getRegisDate());
        }

        if(dto.getStatusId() > 0 && (dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow()
                .getStatusName().equals(IStatusService.CHO_DUYET)||
                dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow()
                        .getStatusName().equals(IStatusService.DANG_HOC_CHINH))){
            if(register.getStartDate() == null && dto.getStartDate() == null){
                register.setStartDate(LocalDate.now());
                if(register.getEndDate() == null && dto.getEndDate() == null){
                    register.setEndDate(register.getStartDate().plusDays(cource.getTimer()));
                    if(dto.getCourceId() > 0 && dto.getCourceId() != cource.getCourceId()){
                        register.setEndDate(register.getStartDate()
                                .plusDays(dbContext.courceRepository.findById(dto.getCourceId())
                                        .orElseThrow().getTimer()));
                    }
                }
            }
        }

        if(dto.getStartDate() != null) {
            register.setStartDate(dto.getStartDate());
            if(register.getEndDate() == null) register.setEndDate(register.getStartDate()
                    .plusDays(cource.getTimer()));
        }
        if(dto.getEndDate() != null) {
            register.setEndDate(dto.getEndDate());
            if(register.getStartDate() == null) register.setStartDate(register.getEndDate()
                    .plusDays(-1 * cource.getTimer()));
        }

        if(register.getRegisDate() != null &&
                register.getStartDate() != null && register.getStartDate().compareTo(register.getRegisDate()) < 0)
            return StatusReturn.INVALID;
        if(register.getEndDate() != null && register.getEndDate().compareTo(register.getStartDate()) < 0)
            return StatusReturn.INVALID;

        if(dto.getStudentId() > 0) register.setStudent(dbContext.studentRepository.findById(dto.getStudentId()).orElseThrow());
        if(dto.getAccountId() > 0) register.setAccount(dbContext.accountRepository.findById(dto.getAccountId()).orElseThrow());
        if(dto.getCourceId() > 0) register.setCource(dbContext.courceRepository.findById(dto.getCourceId()).orElseThrow());
        if(dto.getStatusId() > 0) register.setStatusStudy(dbContext.statusRepository.findById(dto.getStatusId()).orElseThrow());

        dbContext.courceRepository.findById(register.getCource().getCourceId()).map(cource1 -> {
            cource1.setTotalStudent(dbContext.courceRepository.counterStudent(cource1.getCourceId(), IStatusService.DANG_HOC_CHINH));
            return dbContext.courceRepository.save(cource1);
        });
        if(cource.getCourceId() != register.getCource().getCourceId()){
            dbContext.courceRepository.findById(cource.getCourceId()).map(cource1 -> {
                cource1.setTotalStudent(dbContext.courceRepository.counterStudent(cource1.getCourceId(), IStatusService.DANG_HOC_CHINH));
                return dbContext.courceRepository.save(cource1);
            });
        }

        dbContext.registerRepository.save(register);
        return StatusReturn.SUCCESS;
    }

    @Override
    public StatusReturn delete(int registerId){
        if(!dbContext.registerRepository.existsById(registerId)) return StatusReturn.IS_NOT_FOUND;
        dbContext.registerRepository.deleteById(registerId);
        return StatusReturn.SUCCESS;
    }

    @Override
    public List<RegisterDTO> pagingFindAll(int pageNum, int sizeOf){
        return (new Paginator<RegisterDTO>(findAll())).pageOf(pageNum, sizeOf);
    }
}
