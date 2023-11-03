package com.vitorfurini.codegroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDto {

    String name;
    String cpf;
    Date dataAniversario;
    Boolean funcionario;

}
