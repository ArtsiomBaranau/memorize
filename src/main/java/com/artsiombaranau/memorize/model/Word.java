package com.artsiombaranau.memorize.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@NamedQueries({
        @NamedQuery(name = "word_find_all", query = "FROM Word"),
        @NamedQuery(name = "word_find_by_value", query = "FROM Word w WHERE w.value = :value")
})
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String value;
    private String phonetic;
    private String origin;

    @Enumerated(value = EnumType.STRING)
    private PartOfSpeech partOfSpeech;

    public enum PartOfSpeech {
        VERB("Verb"), NOUN("Noun"), ADJECTIVE("Adjective"), DETERMINER("Determiner"), ADVERB("Adverb"), PRONOUN("Pronoun"), PREPOSITION("Preposition"), CONJUNCTION("Conjunction"), INTERJECTION("Interjection");

        private final String value;

        PartOfSpeech(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
