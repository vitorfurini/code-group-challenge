package com.vitorfurini.codegroup.controller;

import com.vitorfurini.codegroup.dto.AssociateEmployeeDto;
import com.vitorfurini.codegroup.entity.Projeto;
import com.vitorfurini.codegroup.enums.ClassificacaoEnum;
import com.vitorfurini.codegroup.enums.StatusEnum;
import com.vitorfurini.codegroup.exception.custom.ServiceException;
import com.vitorfurini.codegroup.responses.Response;
import com.vitorfurini.codegroup.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Retorna todos os projetos que estão registrados na base")
    @GetMapping
    public ResponseEntity<List<Projeto>> getAllProjects() {
        try {
            List<Projeto> projetos = projectService.findAll();
            if (projetos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projetos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Cria um novo projeto")
    @PostMapping
    public ResponseEntity<Projeto> createProject(@Valid @RequestBody Projeto projeto) {
        try {
            Projeto createdProjeto = projectService.save(projeto);
            return new ResponseEntity<>(createdProjeto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/associateEmployee")
    public ResponseEntity<Object> associateEmployee(@Valid @RequestBody AssociateEmployeeDto associateEmployeeDto) throws Exception {

        try {
            ResponseEntity<Object> savedPessoa = projectService.associateEmployee(associateEmployeeDto);

            return ResponseEntity.status(201).body(savedPessoa);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao associar o "
                    + "membro ao projeto.");
        }
    }

    @Operation(summary = "Atualiza um projeto existente")
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> updateProject(@PathVariable(name = "id") Long id, @RequestBody Projeto projeto) {
        try {
            if (!projectService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            projeto.setId(id);
            Projeto updatedProjeto = projectService.save(projeto);
            return ResponseEntity.ok(updatedProjeto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Atualiza o status de um projeto existente")
    @PutMapping("updateStatus/{id}")
    public ResponseEntity<Projeto> updateStatusProject(@PathVariable Long id, @RequestBody StatusEnum status) {
        try {
            if (!projectService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            var updatedProjeto = projectService.updateProject(id, status);
            return ResponseEntity.status(200).body(updatedProjeto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //Se um projeto foi mudado o status para iniciado, em andamento ou encerrado não pode
    //mais ser excluído
    @Operation(summary = "Exclui um projeto existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Projeto>> deleteProject(@PathVariable Long id) throws ServiceException {
        Optional<Projeto> opt = projectService.findById(id);
        if (opt.isPresent()) {
            StatusEnum statusEnum = opt.get().getStatus();
            if(statusEnum.equals(StatusEnum.INICIADO)
                    || statusEnum.equals(StatusEnum.EM_ANDAMENTO)
                    || statusEnum.equals(StatusEnum.ENCERRADO)) {
                throw new ServiceException("O projeto não pode ser excluído pois está no status de: " + statusEnum.name());
            }
            projectService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ServiceException("O projeto já foi excluído!");
        }
    }

    @Operation(summary = "Consulta a classificação de risco de um determinado projeto")
    @GetMapping("/{id}/risco")
    public ResponseEntity<ClassificacaoEnum> getClassificacaoProject(@PathVariable Long id) {
        Optional<Projeto> project = projectService.findById(id);
        if (project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            ClassificacaoEnum risco = ClassificacaoEnum.valueOf(project.get().getRisco());
            return ResponseEntity.ok(risco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
