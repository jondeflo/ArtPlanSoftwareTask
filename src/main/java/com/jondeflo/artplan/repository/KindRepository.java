package com.jondeflo.artplan.repository;

import com.jondeflo.artplan.model.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface KindRepository  extends JpaRepository<Kind, Long> {

	Kind findFirstById(Long Id);
	Kind findFirstByKind(String kind);

}
