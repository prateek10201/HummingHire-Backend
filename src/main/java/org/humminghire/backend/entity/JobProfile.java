package org.humminghire.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.humminghire.backend.converter.ScreeningQAListConverter;
import org.humminghire.backend.model.ScreeningQuestionAndAnswer;

import java.util.List;

@Entity
@Table(name = "job_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnoreProperties({"profile", "postedBy"})
    private Job job;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Convert(converter = ScreeningQAListConverter.class)
    @Column(columnDefinition = "text")
    private List<ScreeningQuestionAndAnswer> screeningQuestions;

    @Lob
    @Column(length = 2097152)
    private byte[] logo;

    @ManyToMany
    @JoinTable(
            name = "job_bookmarks",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"profile", "password"})
    private List<User> bookmarkedBy;
}
