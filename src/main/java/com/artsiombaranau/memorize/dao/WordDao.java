package com.artsiombaranau.memorize.dao;

import com.artsiombaranau.memorize.model.Word;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordDao {
    List<Word> findAll();

    Optional<Word> findById(UUID id);

    Optional<Word> findByValue(String value);

    Word save(Word word);

    Word update(Word word);

    void delete(UUID id);
}
