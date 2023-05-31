package com.billa.sprgroovy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradleIndexController {
    @GetMapping
    public String indx()
    {
        return "HelloWorld!";
    }
}
