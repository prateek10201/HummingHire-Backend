package org.humminghire.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.humminghire.backend.converter.*;
import org.humminghire.backend.model.Certification;
import org.humminghire.backend.model.Education;
import org.humminghire.backend.model.Project;
import org.humminghire.backend.model.Work;

import java.util.List;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"profile", "password"})
    private User user;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(length = 2097152, columnDefinition = "oid")
    private byte[] avatar;

    @Column(name = "avatar_name")
    private String avatarName;

    @Column(name = "avatar_type")
    private String avatarType;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(length = 2097152, columnDefinition = "oid")
    private byte[] resume;

    @Column(name = "resume_name")
    private String resumeName;

    @Column(name = "resume_type")
    private String resumeType;

    @Convert(converter = WorkConverter.class)
    @Column(columnDefinition = "text")
    private Work currentWork;

    @Convert(converter = WorkListConverter.class)
    @Column(columnDefinition = "text")
    private List<Work> previousWorks;

    @Convert(converter = CertificationListConverter.class)
    @Column(columnDefinition = "text")
    private List<Certification> certifications;

    @Convert(converter = EducationListConverter.class)
    @Column(columnDefinition = "text")
    private List<Education> education;

    @Convert(converter = ProjectListConverter.class)
    @Column(columnDefinition = "text")
    private List<Project> projects;

    @ElementCollection
    @CollectionTable(name = "user_profile_social_links")
    private List<String> socialLinks;

    private String languages;

    private String skills;
}
