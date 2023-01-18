package com.artsiombaranau.memorize.repository;

import com.artsiombaranau.memorize.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    Optional<Word> findByValueIgnoreCase(String value);

    @Query(value = "SELECT w FROM Word w WHERE w." + Word.Fields.value + " = :value")
    Optional<Word> findByValueWithQuery(@Param(value = "value") String value);

    List<Word> findAllByPartOfSpeech(Word.PartOfSpeech partOfSpeech);

    Page<Word> findAllByPartOfSpeech(Word.PartOfSpeech partOfSpeech, Pageable pageable);
}
