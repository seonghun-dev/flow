package org.example.flow.domain.extension.repository;

import org.example.flow.domain.extension.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {

    boolean existsByName(String name);

    long countByIsCustomTrue();

    Optional<Extension> findByName(String name);

}
