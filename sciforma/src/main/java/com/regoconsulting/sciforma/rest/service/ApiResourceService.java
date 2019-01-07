package com.regoconsulting.sciforma.rest.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.regoconsulting.sciforma.rest.model.ApiResource;
import com.regoconsulting.sciforma.util.AuthenticationUtil;
import com.sciforma.psnext.api.PSException;
import com.sciforma.psnext.api.Session;

@Path("/api/v1.0/resources")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ApiResourceService{
	private static final SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
    private static List<ApiResource> resources = null;

    static {
        resources = new ArrayList<>();
        resources.add(new ApiResource(1L, "Resource One", LocalDateTime.now(), null));
        resources.add(new ApiResource(2L, "Resource Two", LocalDateTime.now(), null));
        resources.add(new ApiResource(3L, "Resource Three", LocalDateTime.now(), null));
        resources.add(new ApiResource(4L, "Resource Four", LocalDateTime.now(), null));
        resources.add(new ApiResource(5L, "Resource Five", LocalDateTime.now(), null));
        resources.add(new ApiResource(6L, "Resource Six", LocalDateTime.now(), null));
        resources.add(new ApiResource(7L, "Resource Seven", LocalDateTime.now(), null));
        resources.add(new ApiResource(8L, "Resource Eight", LocalDateTime.now(), null));
        resources.add(new ApiResource(9L, "Resource Nine", LocalDateTime.now(), null));
        resources.add(new ApiResource(10L, "Resource Ten", LocalDateTime.now(), null));
    }

    @GET
    public String getResources(@HeaderParam("Authorization") String authString) throws PSException {
    		
    		Session session = AuthenticationUtil.generateSession(authString);
    		List pubResourceList = session.getResourceList();
    		Iterator pubIt = pubResourceList.iterator();
    		JsonArrayBuilder jo = Json.createArrayBuilder();
    		while(pubIt.hasNext()){
    			com.sciforma.psnext.api.Resource res = (com.sciforma.psnext.api.Resource)(pubIt.next());
    			jo.add(displayResourceInfo(res));
    		}
    	
        return jo.build().toString();
    }
    


	//displays various information about each published resource
	public JsonObject displayResourceInfo(com.sciforma.psnext.api.Resource res) throws PSException{
		
		JsonObject resourceProperties = Json.createObjectBuilder()
			        .add("Name" , res.getStringField("Name"))
					.add("Actual Cost", res.getDoubleField("Actual Cost"))
					.add("Actual Effort" , res.getDoubleField("Actual Effort"))
					.add("Availability" , res.getDoubleField("Availability"))
					.add("Cost" , res.getDoubleField("Cost"))
					.add("Default Rate" , res.getDoubleField("Default Rate"))
					.add("Email Address 1" , res.getStringField("Email Address 1"))
					.add("Email Address 2" , res.getStringField("Email Address 2"))
					.add("Email Address 3" , res.getStringField("Email Address 3"))
					.add("End Date" , ""+df.format(res.getDateField("End Date")))
					.add("First Name" , res.getStringField("First Name"))
					.add("ID" , res.getStringField("ID"))
					.add("Job Classification : " , res.getStringField("Job Classification"))
					.add("Last Name : " , res.getStringField("Last Name"))
					.add("Middle Name : " , res.getStringField("Middle Name"))
					.add("Organization : " , res.getStringField("Organization"))
					.add("RBS : " , res.getStringField("RBS"))
					.add("Remaining Cost : " , res.getDoubleField("Remaining Cost"))
					.add("Remaining Effort : " , res.getDoubleField("Remaining Effort"))
					.add("Should Level : " , res.getBooleanField("Should Level"))
					.add("Skills : " , ""+res.getStringListField("Skills"))
					.add("Start Date : " , ""+ df.format(res.getDateField("Start Date")))
					.add("Status : " , res.getStringField("Status"))
					.add("Time Entry Override Set : " , res.getStringField("Time Entry Override Set"))
					.add("Time Entry Override Start : " , ""+ df.format(res.getDateField("Time Entry Override Start")))
					.add("Time Entry Override Stop : " , ""+df.format(res.getDateField("Time Entry Override Stop")))
					.add("Time Entry Set : " , res.getStringField("Time Entry Set"))
					.add("Writable : " , res.getStringField("Writable"))
			  .build();
		return resourceProperties;
	}


    @GET
    @Path("{id: [0-9]+}")
    public ApiResource getResource(@PathParam("id") Long id) {
    	ApiResource resource = new ApiResource(id, null, null, null);

        int index = Collections.binarySearch(resources, resource, Comparator.comparing(ApiResource::getId));

        if (index >= 0)
            return resources.get(index);
        else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createResource(ApiResource resource) {
        if (Objects.isNull(resource.getId()))
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        int index = Collections.binarySearch(resources, resource, Comparator.comparing(ApiResource::getId));

        if (index < 0) {
            resource.setCreatedTime(LocalDateTime.now());
            resources.add(resource);
            return Response
                    .status(Response.Status.CREATED)
                    .location(URI.create(String.format("/api/v1.0/resources/%s", resource.getId())))
                    .build();
        } else
            throw new WebApplicationException(Response.Status.CONFLICT);
    }


    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateResource(@PathParam("id") Long id, ApiResource resource) {
        resource.setId(id);
        int index = Collections.binarySearch(resources, resource, Comparator.comparing(ApiResource::getId));

        if (index >= 0) {
        	ApiResource updatedResource = resources.get(index);
            updatedResource.setModifiedTime(LocalDateTime.now());
            updatedResource.setDescription(resource.getDescription());
            resources.set(index, updatedResource);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteResource(@PathParam("id") Long id) {
    	ApiResource resource = new ApiResource(id, null, null, null);
        int index = Collections.binarySearch(resources, resource, Comparator.comparing(ApiResource::getId));

        if (index >= 0) {
            resources.remove(index);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();

        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}