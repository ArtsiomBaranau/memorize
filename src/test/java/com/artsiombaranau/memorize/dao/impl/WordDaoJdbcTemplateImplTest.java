package com.artsiombaranau.memorize.dao.impl;

import com.artsiombaranau.memorize.dao.WordDao;
import com.artsiombaranau.memorize.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"com.artsiombaranau.memorize.dao.impl", "com.artsiombaranau.memorize.mapper"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WordDaoJdbcTemplateImplTest {

    @Autowired
    @Qualifier(value = "wordDaoJdbcTemplateImpl")
    private WordDao wordDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Disabled
    void getById() {
        Optional<Word> byId = wordDao.findById(UUID.randomUUID());
        assertNull(byId.get());
    }

    @Test
    @Disabled
    void getByIdNotFound() {
        assertThrows(EmptyResultDataAccessException.class, () -> wordDao.findById(UUID.randomUUID()));
    }

    @Test
    @Disabled
    void save() {
        Word saved = wordDao.save(Word.builder()
                .value("bye")
                .build());
        assertNotNull(saved);
    }
}