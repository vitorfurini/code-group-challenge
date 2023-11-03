package com.vitorfurini.codegroup.controller;

import com.vitorfurini.codegroup.dto.PessoaDto;
import com.vitorfurini.codegroup.entity.Pessoa;
import com.vitorfurini.codegroup.exception.custom.ServiceException;
import com.vitorfurini.codegroup.responses.Response;
import com.vitorfurini.codegroup.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@RestController()
@RequestMapping("/api/pessoa")
@CrossOrigin("*")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping()
    public ResponseEntity<Object> createMembro(@Valid @RequestBody PessoaDto pessoa, BindingResult result) throws ServiceException {

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getAllErrors().forEach(erro -> errors.add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<Pessoa>(errors));
        }

        if (pessoa == null || pessoa.getName() == null) {
            return ResponseEntity.badRequest().body("Dados inválidos fornecidos.");
        }

        try {
            PessoaDto savedPessoa = pessoaService.save(pessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
        } catch (Exception e) {
            throw new ServiceException("Ocorreu um erro ao criar o membro, por favor tente novamente!");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<PessoaDto> list = pessoaService.findAll();
        return ResponseEntity.ok().body(list);

    }

    @Operation(summary = "Exclui uma pessoa cadastrada na base")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Project excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Project não encontrado para o ID fornecido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<Pessoa>> delete(@PathVariable Long id) throws ServiceException {

        var response = pessoaService.findById(id);
        if (response.isEmpty()) {
            pessoaService.delete(id);
            return ResponseEntity.status(204).build();
        } else {
            throw new ServiceException("Não existem pessoas cadastradas com o ID informado.");
        }
    }

}
