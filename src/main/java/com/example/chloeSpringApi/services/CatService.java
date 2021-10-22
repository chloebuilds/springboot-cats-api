package com.example.chloeSpringApi.services;


// ! Tells spring that this class is a service
// ! this is where the business logic is going to live
// ! A service also the class that's aware of the repository

import com.example.chloeSpringApi.models.Cat;
import com.example.chloeSpringApi.repos.CatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CatService {

    // ! Autowired annotation means spring will 'inject; a station Repo
    // ! into our station service when we create it
    @Autowired
    private CatRepo catRepo;

    public Iterable<Cat> findAllCats() {
        return catRepo.findAll();
    }

    public Cat createCat(Cat cat) {
        return catRepo.save(cat);
    }

    public Cat getCat(Integer catId) throws Exception {
        Optional<Cat> catBox =  catRepo.findById(catId);
        if (catBox.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found :(");
        }
        return catBox.get();
        // we've checked the box and we're throwing an exception if there is nothing in the box
        // after that there must be something in the box because we have checked that it is not empty
        // so we can get the cat out of the box and return it
    }

    public Cat editCat(Integer catId, Cat cat) throws Exception {
        Optional<Cat> catBox =  catRepo.findById(catId);
        if (catBox.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found :(");
        }
        return catRepo.save(cat);
    }

    public Cat deleteCat(Integer catId) throws Exception {
        Optional<Cat> catBox =  catRepo.findById(catId);
        if (catBox.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found :(");
    }
//        catRepo.delete(catBox.get());
//        return catBox.get();

        catRepo.deleteById(catId);
        return catBox.get();
}
}