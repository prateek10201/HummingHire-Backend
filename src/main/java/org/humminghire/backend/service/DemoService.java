package org.humminghire.backend.service;

import org.humminghire.backend.model.Demo;
import org.humminghire.backend.repository.DemoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoRepo repo;

    public List<Demo> getDemos() {
        return repo.findAll();
    }

    public Demo add(Demo demo) {
       return repo.save(demo);
    }

    public Demo getDemo(String name) {
        return repo.findByName(name);
    }
}
