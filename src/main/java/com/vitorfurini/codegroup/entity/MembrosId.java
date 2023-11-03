package com.vitorfurini.codegroup.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MembrosId implements Serializable {
    private Long idProjeto;
    private Long idPessoa;

}
