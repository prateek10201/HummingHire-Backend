package org.humminghire.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String jobName;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean jobStatus = true;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @Column(nullable = false)
    private Date dateOfPosting;

    @Column(nullable = false)
    private String salaryRange;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkPlaceType workPlaceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobRole jobRole;

    @Column(nullable = false)
    private int jobExperienceLevel;

    @Column(nullable = false)
    private String receiveEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_user_id", nullable = false)
    @JsonIgnoreProperties({"profile", "password"})
    private User postedBy;

    // One-to-One relationship with JobProfile
    @OneToOne(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("job")
    private JobProfile profile;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("job")
    private List<JobApplicationStatus> applicationStatuses;

    @ManyToMany
    @JoinTable(
            name = "job_applications",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "applicant_id")
    )
    @JsonIgnoreProperties({"profile", "password"})
    private List<User> applicants;

    @Getter
    public enum JobPosition {
        ENTRY_LEVEL("Entry Level"),
        JUNIOR("Junior"),
        MID_LEVEL("Mid Level"),
        SENIOR("Senior"),
        LEAD("Lead"),
        MANAGER("Manager");

        private final String position;

        JobPosition(String position) {
            this.position = position;
        }
    }

    @Getter
    public enum WorkPlaceType {
        ONSITE("Onsite"),
        REMOTE("Remote"),
        HYBRID("Hybrid");

        private final String type;

        WorkPlaceType(String type) {
            this.type = type;
        }
    }

    @Getter
    public enum JobRole {
        FULL_TIME("Full Time"),
        PART_TIME("Part Time"),
        CONTRACT("Contract"),
        INTERNSHIP("Internship");

        private final String role;

        JobRole(String role) {
            this.role = role;
        }
    }
}
