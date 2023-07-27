package com.example.realsourcejdbc.repository;

import com.example.realsourcejdbc.model.Tutorial;
import com.example.realsourcejdbc.rowmapper.TutorialRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*  Tip: Use "BeanPropertyRowMapper.newInstance(Tutorial.class)" when model properties and table columns have the
    same names  */
    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query(String.format("SELECT %s, %s, %s, %s FROM tutorials", TutorialRowMapper.idColumn,
                TutorialRowMapper.titleColumn, TutorialRowMapper.descriptionColumn,
                TutorialRowMapper.publishedColumn), TutorialRowMapper.build());
    }

    @Override
    public Tutorial findById(Long id) {
        List<Tutorial> tutorials = jdbcTemplate.query(String.format("SELECT %s, %s, %s, %s FROM tutorials WHERE %s=?",
                TutorialRowMapper.idColumn, TutorialRowMapper.titleColumn, TutorialRowMapper.descriptionColumn,
                TutorialRowMapper.publishedColumn, TutorialRowMapper.idColumn), TutorialRowMapper.build(), id);
        return tutorials.isEmpty() ? null : tutorials.get(0);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        return jdbcTemplate.query(String.format("SELECT %s, %s, %s, %s FROM tutorials WHERE UPPER(%s) LIKE ",
                        TutorialRowMapper.idColumn, TutorialRowMapper.titleColumn,
                        TutorialRowMapper.descriptionColumn, TutorialRowMapper.publishedColumn,
                        TutorialRowMapper.titleColumn).concat("'%").concat(title.toUpperCase()).concat("%'"),
                TutorialRowMapper.build());
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return jdbcTemplate.query(String.format("SELECT %s, %s, %s, %s FROM tutorials WHERE %s=?",
                        TutorialRowMapper.idColumn, TutorialRowMapper.titleColumn, TutorialRowMapper.descriptionColumn,
                        TutorialRowMapper.publishedColumn, TutorialRowMapper.publishedColumn),
                TutorialRowMapper.build(), published);
    }

    @Override
    public Tutorial save(Tutorial book) {
        String query = String.format(
                "INSERT INTO tutorials (%s, %s, %s) VALUES (?, ?, ?) RETURNING %s, %s, %s, %s;",
                TutorialRowMapper.titleColumn, TutorialRowMapper.descriptionColumn,
                TutorialRowMapper.publishedColumn, TutorialRowMapper.idColumn, TutorialRowMapper.titleColumn,
                TutorialRowMapper.descriptionColumn, TutorialRowMapper.publishedColumn);
        List<Tutorial> tutorials = jdbcTemplate.query(query, TutorialRowMapper.build(), book.getTitle(),
                book.getDescription(), book.isPublished());
        return tutorials.isEmpty() ? null : tutorials.get(0);
    }

    @Override
    public int update(Tutorial book) {
        // Returns: the number of rows affected
        return jdbcTemplate.update(String.format("UPDATE tutorials SET %s=?, %s=?, %s=? WHERE %s=?",
                        TutorialRowMapper.titleColumn, TutorialRowMapper.descriptionColumn, TutorialRowMapper.publishedColumn, TutorialRowMapper.idColumn),
                book.getTitle(), book.getDescription(), book.isPublished(), book.getId());
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM tutorials");
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(String.format("DELETE FROM tutorials WHERE %s=?", TutorialRowMapper.idColumn), id);
    }
}
