package com.example.realsourcejdbc.controller;

import com.example.realsourcejdbc.model.Tutorial;
import com.example.realsourcejdbc.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<>();
        try {
            if (title == null) {
                tutorials = tutorialRepository.findAll();
            } else {
                tutorials = tutorialRepository.findByTitleContaining(title);
                if (tutorials.isEmpty())
                    return new ResponseEntity<>(tutorials, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(tutorials, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        try {
            Tutorial tutorial = tutorialRepository.findById(id);
            if (tutorial == null) {
                return new ResponseEntity<>(new Tutorial(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Tutorial(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published/{already}")
    public ResponseEntity<List<Tutorial>> findByPublished(@PathVariable("already") boolean already) {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(already);
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial newTutorial = tutorialRepository.save(tutorial);
            if (newTutorial == null)
                return new ResponseEntity<>(new Tutorial(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Tutorial(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tutorials/update")
    public ResponseEntity<Tutorial> updateTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial dbTutorial = tutorialRepository.findById(tutorial.getId());
            if (dbTutorial == null)
                return new ResponseEntity<>(new Tutorial(), HttpStatus.NOT_FOUND);
            return tutorialRepository.update(tutorial) == 1 ? new ResponseEntity<>(tutorial, HttpStatus.OK) :
                    new ResponseEntity<>(new Tutorial(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Tutorial(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<String> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>("All tutorials were successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the tutorials.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
        try {
            if (tutorialRepository.deleteById(id) == 0)
                return new ResponseEntity<>("No tutorial found to delete.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("Tutorial successfully removed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the tutorial.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
