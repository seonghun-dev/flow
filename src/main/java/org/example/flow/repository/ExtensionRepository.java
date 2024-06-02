package org.example.flow.repository;

import org.example.flow.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {

    boolean existsByName(String name);

    long countByIsCustomTrue();

}
