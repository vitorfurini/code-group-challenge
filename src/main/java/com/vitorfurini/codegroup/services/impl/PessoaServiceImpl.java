package com.vitorfurini.codegroup.services.impl;

import com.vitorfurini.codegroup.dto.PessoaDto;
import com.vitorfurini.codegroup.entity.Pessoa;
import com.vitorfurini.codegroup.repository.PessoaRepository;
import com.vitorfurini.codegroup.services.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public PessoaDto save(PessoaDto pessoa) {

        var pessoa1 = new Pessoa(pessoa.getName(), pessoa.getDataAniversario(), pessoa.getCpf(),
                pessoa.getFuncionario());

        pessoaRepository.save(pessoa1);

        return modelMapper.map(pessoa, (Type) PessoaDto.class);
    }

    @Override
    public List<PessoaDto> findAll() {
        var pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(pessoa -> new PessoaDto(pessoa.getNome(), pessoa.getCpf(),
                pessoa.getDataNascimento(), pessoa.getFuncionario())).toList();
    }

    @Override
    public Optional<?> findById(Long id) {
        return Optional.of(pessoaRepository.findById(id));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        pessoaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
