package com.vitorfurini.codegroup.services.impl;

import com.vitorfurini.codegroup.dto.AssociateEmployeeDto;
import com.vitorfurini.codegroup.entity.Projeto;
import com.vitorfurini.codegroup.enums.StatusEnum;
import com.vitorfurini.codegroup.repository.PessoaRepository;
import com.vitorfurini.codegroup.repository.ProjectRepository;
import com.vitorfurini.codegroup.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final PessoaRepository pessoaRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, PessoaRepository pessoaRepository) {
        this.projectRepository = projectRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public List<Projeto> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Projeto save(Projeto projeto) {
        return projectRepository.save(projeto);
    }

    public Projeto updateProject(Long id, StatusEnum status) {

        var projectDto = projectRepository.findById(id);

        if (projectDto.isPresent()) {
            Projeto update = projectDto.get();
            update.setStatus(status);
            return this.projectRepository.save(update);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public Optional<Projeto> findById(Long id) {
        return this.projectRepository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> associateEmployee(AssociateEmployeeDto associateEmployeeDto) {

        var funcionario = pessoaRepository.findById(associateEmployeeDto.getIdPessoa());
        Optional<Projeto> projetoSemGerente = projectRepository.findById(associateEmployeeDto.getIdProjeto());

        try {
            if (funcionario.isPresent() && projetoSemGerente.isPresent() && funcionario.get().getFuncionario().equals(true)) {

                Projeto projeto = modelMapper.map(projetoSemGerente, Projeto.class);

                projeto.setId(projetoSemGerente.get().getId());
                projeto.setNome(projetoSemGerente.get().getNome());
                projeto.setDataInicio(projetoSemGerente.get().getDataInicio());
                projeto.setDataPrevisaoFim(projetoSemGerente.get().getDataPrevisaoFim());
                projeto.setDataFim(projetoSemGerente.get().getDataFim());
                projeto.setDescricao(projetoSemGerente.get().getDescricao());
                projeto.setOrcamento(projetoSemGerente.get().getOrcamento());
                projeto.setRisco(projetoSemGerente.get().getRisco());
                projeto.setGerente(funcionario.get());
                projeto.setStatus(projetoSemGerente.get().getStatus());
                projectRepository.save(projeto);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new InternalError(e.getMessage());
        }
    }
}
