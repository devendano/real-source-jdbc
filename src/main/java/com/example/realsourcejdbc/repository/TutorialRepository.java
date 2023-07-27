package com.example.realsourcejdbc.repository;

import com.example.realsourcejdbc.model.Tutorial;

import java.util.List;

public interface TutorialRepository {

    List<Tutorial> findAll();

    Tutorial findById(Long id);

    List<Tutorial> findByTitleContaining(String title);

    List<Tutorial> findByPublished(boolean published);

    Tutorial save(Tutorial book);

    int update (Tutorial book);

    int deleteAll();

    int deleteById(Long id);
}
