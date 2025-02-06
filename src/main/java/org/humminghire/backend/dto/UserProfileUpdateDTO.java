package org.humminghire.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.humminghire.backend.model.Certification;
import org.humminghire.backend.model.Education;
import org.humminghire.backend.model.Project;
import org.humminghire.backend.model.Work;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDTO {
    private Work currentWork;
    private List<Work> previousWorks;
    private List<Certification> certifications;
    private List<Education> education;
    private List<Project> projects;
    private List<String> socialLinks;
    private String languages;
    private String skills;
}
