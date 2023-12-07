package org.springframework.samples.petclinic.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetsRepository extends JpaRepository<Pets, Integer> {
}
