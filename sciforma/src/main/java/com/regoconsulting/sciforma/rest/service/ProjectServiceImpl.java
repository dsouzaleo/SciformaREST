package com.regoconsulting.sciforma.rest.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.regoconsulting.sciforma.rest.model.Project;
import com.regoconsulting.sciforma.rest.model.Response;

@Path("/api/v1.0/projects")
@Consumes(MediaType.APPLICATION_XML)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ProjectServiceImpl implements ProjectService {

	private static Map<Integer,Project> projects = new HashMap<Integer,Project>();
	
	@Override
	@POST
    @Path("/add")
	public Response addProject(Project p) {
		Response response = new Response();
		if(projects.get(p.getId()) != null){
			response.setStatus(false);
			response.setMessage("Project Already Exists");
			return response;
		}
		projects.put(p.getId(), p);
		response.setStatus(true);
		response.setMessage("Project created successfully");
		return response;
	}

	@Override
	@GET
    @Path("/{id}/delete")
	public Response deleteProject(@PathParam("id") int id) {
		Response response = new Response();
		if(projects.get(id) == null){
			response.setStatus(false);
			response.setMessage("Project Doesn't Exists");
			return response;
		}
		projects.remove(id);
		response.setStatus(true);
		response.setMessage("Project deleted successfully");
		return response;
	}

	@Override
	@GET
	@Path("/{id}/get")
	public Project getProject(@PathParam("id") int id) {
		return projects.get(id);
	}
	
	@GET
	@Path("/{id}/getDummy")
	public Project getDummyProject(@PathParam("id") int id) {
		Project p = new Project();
		p.setAge(99);
		p.setName("Dummy");
		p.setId(id);
		return p;
	}

	@Override
	@GET
	@Path("/getAll")
	public Project[] getAllProjects() {
		Set<Integer> ids = projects.keySet();
		Project[] p = new Project[ids.size()];
		int i=0;
		for(Integer id : ids){
			p[i] = projects.get(id);
			i++;
		}
		return p;
	}

}