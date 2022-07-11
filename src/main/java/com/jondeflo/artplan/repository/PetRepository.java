package com.jondeflo.artplan.repository;

import com.jondeflo.artplan.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findFirstById(Long petId);
    Pet findFirstByName(String name);
    List<Pet> findAllByOwnerId(Long id);

}