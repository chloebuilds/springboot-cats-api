package com.example.chloeSpringApi.controllers;

import com.example.chloeSpringApi.models.Cat;
import com.example.chloeSpringApi.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CatController {
    // ! Rest controller annotation is needed for each controller

    @Autowired
    //! does the dependency injection - tells SB to create the service & makes sure it's defined
    private CatService catService;

    // ! Get Mapping is used to specify the route path for a GET request
    @GetMapping("/hello")
    public String helloWorld() { return "Hello World!"; }

    @GetMapping("/world")
    public String goodbyeCats() { return "Goodbye Cats!"; }

    @GetMapping("/cats")
    public Iterable<Cat> getCats() { return catService.findAllCats(); }

    @GetMapping("/cat")
    public Cat getCat() {
        Cat cat = new Cat();
            cat.setBreed("Russian Blue");
            cat.setDescription("");
            cat.setLifeSpan("10-16 Years");
            cat.setPersonality("Active, Dependent, Easy Going, Gentle, Intelligent, Loyal, Playful, Quiet");
            return cat;
        }

        @GetMapping("/cat/{catId}")
        // pathVariable (similar to :id)
        public Cat getCat(@PathVariable String catId) throws Exception {
            return catService.getCat(Integer.valueOf(catId));
        }

    @PostMapping("/cats")
    // ! @RequestBody annotation gives us access to the body of the request (the thing we're posting)
    public Cat postCat(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }

    @PutMapping("/cat/{catId}")
    public Cat editCat(@PathVariable String catId, @RequestBody Cat cat) throws Exception {
        return catService.editCat(Integer.valueOf(catId), cat);
    }

    @DeleteMapping("/cat/{catId}")
    public Cat deleteCat(@PathVariable String catId) throws Exception {
        return catService.deleteCat(Integer.valueOf(catId));
    }
}
