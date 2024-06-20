package com.virtusa.questor.dao;

import com.virtusa.questor.dto.ProjectDTO;
import com.virtusa.questor.model.Project;
import com.virtusa.questor.model.User;
import com.virtusa.questor.repository.ProjectRepository;
import com.virtusa.questor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectDAO {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private BoardDAO boardDAO;

    @Autowired
    private UserRepository userRepo;

    public ProjectDTO save(ProjectDTO projectDTO){
        Project projectModel = toModel(projectDTO);
        User userModel = userRepo.findById(projectDTO.getUserId()).orElse(null);
        if(userModel != null){
            projectModel.setUser(userModel);
            projectModel = projectRepo.save(projectModel);
            return toDTO(projectModel);
        } else {
            throw new IllegalArgumentException("userId not Found:" + projectDTO.getUserId());
        }
    }

    public ProjectDTO findById(Long id){
        Project projectModel = projectRepo.findById(id).orElse(null);
        return projectModel != null ? toDTO(projectModel) : null;
    }

    public List<ProjectDTO> findAll(){
        List<Project> projects = projectRepo.findAll();
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProjectDTO updateProjectById(Long id, ProjectDTO projectDTO){
        Project existingProject = projectRepo.findById(id).orElse(null);
        if (existingProject != null){
            Project updatedProject = toModel(projectDTO);
            updatedProject.setProjectId(id);
            updatedProject = projectRepo.save(updatedProject);
            return toDTO(updatedProject);
        } else {
            throw new IllegalArgumentException("Project Not found" + projectDTO.getUserId());
        }
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO){
        Project existingProject = projectRepo.findById(projectDTO.getProjectId()).orElse(null);
        if (existingProject != null){
            Project updatedProject = toModel(projectDTO);
            updatedProject = projectRepo.save(updatedProject);
            return toDTO(updatedProject);
        } else {
            throw new IllegalArgumentException("Project not Found" + projectDTO.getProjectId());
        }
    }

    public void deleteById(Long id){
        Project project = projectRepo.findById(id).orElse(null);
        if (project != null){
            projectRepo.delete(project);
        } else {
            throw new IllegalArgumentException("Project not found" + id);
        }
    }

    public List<ProjectDTO> findProjectsByUserID(Long userId){
        List<Project> projects = projectRepo.findByUserId(userId);
        return projects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProjectDTO toDTO(Project project){

        if (project == null){
            return null;
        }
        return ProjectDTO.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .userId(project.getUser() != null ? project.getUser().getUserId():null)
                .boards(project.getBoards() != null ? project.getBoards().stream().map(boardDAO::toDTO).collect(Collectors.toList()) : null)
                .build();
    }

    public Project toModel(ProjectDTO projectDTO){
        if (projectDTO == null){
            return null;
        }
        Project project = Project.builder()
                .projectId(projectDTO.getProjectId())
                .title(projectDTO.getTitle())
                .description(projectDTO.getDescription())
                .status(projectDTO.getStatus())
                .startDate(projectDTO.getStartDate())
                .build();

        if(projectDTO.getUserId() != null){
            User user = userRepo.findById(projectDTO.getUserId()).orElse(null);
            if(user != null){
                project.setUser(user);
            }
        }
        return project;
    }

}