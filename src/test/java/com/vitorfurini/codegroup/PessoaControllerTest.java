package com.vitorfurini.codegroup;

import com.vitorfurini.codegroup.controller.PessoaController;
import com.vitorfurini.codegroup.dto.PessoaDto;
import com.vitorfurini.codegroup.services.PessoaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @Test
    void testCreatePessoaWithValidData() {
        //when
        PessoaDto pessoa = new PessoaDto();
        pessoa.setName("Vitor Furini");
        pessoa.setFuncionario(true);

        when(pessoaService.save(pessoa)).thenReturn(pessoa);

        // given
        PessoaDto response = pessoaService.save(pessoa);

        // then
        assertEquals(pessoa.getName(), response.getName());
        assertEquals(pessoa.getCpf(), response.getCpf());
    }

    @Test
    void testCreateMembroWithNullName() {
        // given
        PessoaDto pessoa = new PessoaDto();
        pessoa.setName(null);
        pessoa.setFuncionario(true);

        // when
        PessoaDto response = pessoaService.save(pessoa);

        // then
        assertNull(response);
    }

    @Test
    void testCreateMembroWithNullIfIsFuncionario() {
        // given
        PessoaDto pessoa = new PessoaDto();
        pessoa.setName("Vitor Furini");
        pessoa.setFuncionario(null);

        // when
        PessoaDto response = pessoaService.save(pessoa);

        // then
        assertNull(pessoa.getFuncionario());

    }
}
