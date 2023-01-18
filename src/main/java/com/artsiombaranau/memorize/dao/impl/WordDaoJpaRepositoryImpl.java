package com.artsiombaranau.memorize.dao.impl;

import com.artsiombaranau.memorize.dao.WordDao;
import com.artsiombaranau.memorize.model.Word;
import com.artsiombaranau.memorize.repository.WordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("default")
@Primary
@Component
@RequiredArgsConstructor
public class WordDaoJpaRepositoryImpl implements WordDao {
    private final WordRepository repository;

    @Override
    public List<Word> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Word> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Word> findByValue(String value) {
        return repository.findByValueIgnoreCase(value);
    }

    @Override
    public Word save(Word word) {
        return repository.save(word);
    }

    @Override
    @Transactional
    public Word update(Word word) {
        Optional<Word> wordOptional = repository.findById(word.getId());
        if (wordOptional.isPresent()) {
            Word foundWord = wordOptional.get();
            foundWord.setValue(word.getValue());
            foundWord.setOrigin(word.getOrigin());
            foundWord.setPhonetic(word.getPhonetic());
            foundWord.setPartOfSpeech(word.getPartOfSpeech());
            return repository.save(foundWord);
        } else throw new EntityNotFoundException();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
