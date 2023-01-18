package com.artsiombaranau.memorize.mapper;

import com.artsiombaranau.memorize.model.Word;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class WordMapper implements RowMapper<Word> {
    @Override
    public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Word.builder()
                .id(UUID.fromString(rs.getString(Word.Fields.id)))
                .value(rs.getString(Word.Fields.value))
                .phonetic(rs.getString(Word.Fields.phonetic))
                .origin(rs.getString(Word.Fields.origin))
                .partOfSpeech(Word.PartOfSpeech.valueOf(rs.getString(Word.Fields.partOfSpeech)))
                .build();
    }
}
