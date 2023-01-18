package com.artsiombaranau.memorize.dao.impl;

import com.artsiombaranau.memorize.dao.WordDao;
import com.artsiombaranau.memorize.mapper.WordMapper;
import com.artsiombaranau.memorize.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WordDaoJdbcTemplateImpl implements WordDao {
    private final JdbcTemplate jdbcTemplate;
    private final WordMapper wordMapper;

    @Override
    public List<Word> findAll() {
        return jdbcTemplate.queryForList("SELECT * FROM WORD", Word.class);
    }

    @Override
    public List<Word> findAll(Pageable pageable) {
        return jdbcTemplate.queryForList("SELECT * FROM WORD limit ? offset ?", Word.class, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public Optional<Word> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM WORD WHERE ID = ?", wordMapper, id));
    }

    @Override
    public Optional<Word> findByValue(String value) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM WORD WHERE VALUE = ?", wordMapper, value));
    }

    @Override
    public Word save(Word word) {
        jdbcTemplate.update("INSERT INTO WORD (ID, VALUE, ORIGIN, PHONETIC, PART_OF_SPEECH) VALUES (?, ?, ?, ?, ?)", UUID.randomUUID(), word.getValue(), word.getOrigin(), word.getPhonetic(), word.getPartOfSpeech());

        return this.findById(jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", UUID.class)).get();
    }

    @Override
    public Word update(Word word) {
        jdbcTemplate.update("UPDATE WORD SET VALUE = ?, ORIGIN = ?, PHONETIC = ?, PART_OF_SPEECH = ? WHERE ID = ?", word.getValue(), word.getOrigin(), word.getPhonetic(), word.getPartOfSpeech(), word.getId());

        return this.findById(word.getId()).get();
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM WORD WHERE ID = ?", id);
    }
}
