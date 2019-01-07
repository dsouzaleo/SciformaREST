package com.regoconsulting.sciforma.rest.service;

import com.regoconsulting.sciforma.rest.model.Project;
import com.regoconsulting.sciforma.rest.model.Response;

public interface ProjectService {

	public Response addProject(Project p);
	
	public Response deleteProject(int id);
	
	public Project getProject(int id);
	
	public Project[] getAllProjects();

}