package org.humminghire.backend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "job_application_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnoreProperties({"profile", "postedBy", "applicationStatuses"})
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    @JsonIgnoreProperties({"profile", "password"})
    private User applicant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING; // PENDING, REVIEWING, ACCEPTED, REJECTED

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @Column(nullable = false)
    private Date applicationDate;

    private String additionalNotes;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
        if (applicationDate == null) {
            applicationDate = new Date();
        }
    }

    @Getter
    public enum ApplicationStatus {
        PENDING("PENDING"),
        REVIEWING("REVIEWING"),
        ACCEPTED("ACCEPTED"),
        REJECTED("REJECTED");

        private final String status;

        ApplicationStatus(String status) {
            this.status = status;
        }
    }
}
