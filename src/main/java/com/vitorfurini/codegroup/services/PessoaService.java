package com.vitorfurini.codegroup.services;

import com.vitorfurini.codegroup.dto.PessoaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface PessoaService {
    PessoaDto save(PessoaDto pessoa);

    List<PessoaDto> findAll();

    Optional<?> findById(Long id);

    ResponseEntity<?> delete(Long id);

}
