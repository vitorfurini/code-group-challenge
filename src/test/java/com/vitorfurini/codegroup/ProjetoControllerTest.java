package com.vitorfurini.codegroup;


import com.vitorfurini.codegroup.controller.ProjectController;
import com.vitorfurini.codegroup.entity.Projeto;
import com.vitorfurini.codegroup.exception.custom.ServiceException;
import com.vitorfurini.codegroup.services.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjetoControllerTest {

    @Mock
    private ProjectServiceImpl projetoService;

    @InjectMocks
    private ProjectController projetoController;

    @Test
    void testGetAllProjetos() {
        // given
        List<Projeto> projetos = Arrays.asList(new Projeto(), new Projeto());
        when(projetoService.findAll()).thenReturn(projetos);

        // when
        ResponseEntity<?> response = projetoController.getAllProjects();


        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            System.out.println(response.getBody());
        }

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projetos, response.getBody());
    }

    @Test
    void testGetAllProjetosWithNoAvailableProjetos() {
        // given
        when(projetoService.findAll()).thenReturn(Collections.emptyList());

        // when
        ResponseEntity<?> response = projetoController.getAllProjects();

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    void testCreateProject() {
        // given
        Projeto projeto = new Projeto();
        Projeto createdProjeto = new Projeto();
        createdProjeto.setId(1L);
        when(projetoService.save(projeto)).thenReturn(createdProjeto);

        // when
        ResponseEntity<?> response = projetoController.createProject(projeto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProjeto, response.getBody());
    }

    @Test
    void testUpdateProjectUpdatedSuccessfully() {
        // given
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        Projeto updatedProjeto = new Projeto();
        updatedProjeto.setId(projetoId);

        when(projetoService.existsById(projetoId)).thenReturn(true);
        when(projetoService.save(projeto)).thenReturn(updatedProjeto);

        // when
        ResponseEntity<?> response = projetoController.updateProject(projetoId, projeto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProjeto, response.getBody());
    }
}
