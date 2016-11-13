package ru.smartcoder.spring_rest_example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smartcoder.spring_rest_example.dao.ApplicationsDao;
import ru.smartcoder.spring_rest_example.model.Application;

import java.util.List;

import static ru.smartcoder.spring_rest_example.controller.Endpoints.*;

@RestController
public class ApplicationController {

    private static final String VERSION = VERSION_1_URL;

    @Autowired
    private ApplicationsDao applicationsDao;

    @GetMapping("/" + VERSION + "/" + CATEGORIES_URL + "/{categoryId}/" + APPLICATIONS_URL)
    public List<Application> getApplicationsByCategory(@PathVariable Long categoryId) {
        return applicationsDao.list(categoryId);
    }

    @GetMapping("/" + VERSION + "/" + CATEGORIES_URL + "/{categoryId}/" + APPLICATIONS_URL + "/{id}")
    public ResponseEntity getApplication(@PathVariable Long categoryId,
                                         @PathVariable Long id) {

        Application application = applicationsDao.get(categoryId, id);
        if (application == null) {
            return new ResponseEntity<>("No Application found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @PostMapping(value = "/" + VERSION + "/" + CATEGORIES_URL + "/{categoryId}/" + APPLICATIONS_URL)
    public ResponseEntity createAppCategory(@PathVariable Long categoryId,
                                            @RequestBody Application application) {

        application.setCategoryId(categoryId);
        applicationsDao.create(application);

        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @DeleteMapping("/" + VERSION + "/" + CATEGORIES_URL + "/{categoryId}/" + APPLICATIONS_URL + "/{id}")
    public ResponseEntity deleteAppCategory(@PathVariable Long categoryId,
                                            @PathVariable Long id) {

        if (null == applicationsDao.delete(categoryId, id)) {
            return new ResponseEntity<>("No Application found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @PutMapping("/" + VERSION + "/" + CATEGORIES_URL + "/{categoryId}/" + APPLICATIONS_URL + "/{id}")
    public ResponseEntity updateAppCategory(@PathVariable Long categoryId,
                                            @PathVariable Long id,
                                            @RequestBody Application application) {

        application.setCategoryId(categoryId);
        application = applicationsDao.update(application);

        if (null == application) {
            return new ResponseEntity<>("No Application found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(application, HttpStatus.OK);
    }

}
