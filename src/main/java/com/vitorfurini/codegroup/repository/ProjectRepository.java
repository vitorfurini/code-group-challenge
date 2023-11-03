package com.vitorfurini.codegroup.repository;

import com.vitorfurini.codegroup.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Projeto, Long> {
}
