package com.vitorfurini.codegroup.entity;

import com.vitorfurini.codegroup.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@Table(name = "PROJETO")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "data_inicio")
    private Date dataInicio;

    @Column(name = "data_previsao_fim")
    private Date dataPrevisaoFim;

    @Column(name = "data_fim")
    private Date dataFim;

    @Column(name = "descricao", length = 5000)
    private String descricao;

    @Column(name = "orcamento")
    private Float orcamento;

    @Column(name = "risco", length = 45)
    private String risco;

    @ManyToOne
    @JoinColumn(name = "idgerente", nullable = false)
    private Pessoa gerente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 45)
    private StatusEnum status;

    public Projeto(Long gerente, Long id) {
        this.id = id;
        this.getGerente().setId(gerente);
    }
    public Projeto() {}

}
