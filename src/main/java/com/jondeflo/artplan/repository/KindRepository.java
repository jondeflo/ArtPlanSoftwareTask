package com.jondeflo.artplan.repository;

import com.jondeflo.artplan.model.Kind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindRepository  extends JpaRepository<Kind, Long> {

	Kind findFirstByKind(String kind);
}
