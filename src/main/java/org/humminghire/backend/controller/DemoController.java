package org.humminghire.backend.controller;

import org.humminghire.backend.model.Demo;
import org.humminghire.backend.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping()
    public List<Demo> getDemos() {
        return demoService.getDemos();
    }

    @GetMapping("/{name}")
    public Demo getDemo(@PathVariable String name) {
        return demoService.getDemo(name);
    }

    @PostMapping
    public String addDemo(@RequestBody Demo demo) {
        demoService.add(demo);
        return "Demo Added Successfully";
    }
}
