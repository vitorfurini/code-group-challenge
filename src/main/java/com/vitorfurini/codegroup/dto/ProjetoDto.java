package com.vitorfurini.codegroup.dto;

import com.vitorfurini.codegroup.entity.Pessoa;
import com.vitorfurini.codegroup.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDto {

    Long id;
    String nome;
    Date dataInicio;
    Date dataPrevisaoFim;
    Date dataFim;
    String risco;
    Pessoa gerente;
    StatusEnum status;
    Float orcamento;
}
