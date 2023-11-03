package com.vitorfurini.codegroup.services;

import com.vitorfurini.codegroup.dto.AssociateEmployeeDto;
import com.vitorfurini.codegroup.entity.Projeto;
import com.vitorfurini.codegroup.enums.StatusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Projeto> findAll();

    Projeto save(Projeto projeto);

    Projeto updateProject(Long id, StatusEnum projeto);

    boolean existsById(Long id);

    ResponseEntity<?> delete(Long id);

    Optional<Projeto> findById(Long id);

    ResponseEntity<Object> associateEmployee(AssociateEmployeeDto associateEmployeeDto) throws Exception;
}
