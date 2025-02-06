package org.humminghire.backend.repository;

import org.humminghire.backend.model.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepo extends JpaRepository<Demo, String> {

    Demo findByName(String name);
}
