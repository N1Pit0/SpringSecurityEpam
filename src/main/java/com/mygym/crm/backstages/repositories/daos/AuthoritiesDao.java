package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesDao extends JpaRepository<Authorities, Integer> {

}
