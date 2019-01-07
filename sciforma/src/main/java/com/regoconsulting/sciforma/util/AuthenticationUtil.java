package com.regoconsulting.sciforma.util;

import javax.ws.rs.*;
import com.sciforma.psnext.api.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;

@Path("/api/v1.0/login")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationUtil {
	@GET
	public Response login(@HeaderParam("Authorization") String authString) {
		String reponseHeader = "";
		String reponseHeaderValue = "";
		Session session = null;
		int errorCode = 0;
		long start = System.currentTimeMillis(); 
		try {
			if (authString != null) {
				System.out.println("#########" + authString);
				String[] authParts = authString.split("\\s+");
				String authInfo = authParts[1];
				// Decode the data back to original string				
				String decodedAuth = Base64.decodeAsString(authInfo.getBytes());
				System.out.println(decodedAuth);
				String[] credentials = decodedAuth.split(":");
				String userName = credentials[0];
				String pwd = credentials[1];
				String serverURL = "https://rego-consulting-test.sciforma.net/sciforma";
				session = new Session(serverURL);
				session.login(userName, pwd.toCharArray());
				
				if (session.isLoggedIn())
				{
					System.out.println("Yes logged in");
				}
				
				reponseHeader = "Authorization:";
				errorCode = 200;
			} else {
				reponseHeaderValue = "User Visible Realm";
				reponseHeader = "WWW-Authenticate: Basic realm=";
				errorCode = 401;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long end = System.currentTimeMillis(); 
        System.out.println("Time Taken for Session: " + (end - start)/1000 + "s"); 
		return Response.ok().header(reponseHeader, reponseHeaderValue).status(errorCode).build();
	}
	
	public static Session generateSession(String authString)
	{
		Session session = null;

		try {
			if (authString != null) {
				String[] authParts = authString.split("\\s+");
				String authInfo = authParts[1];
				// Decode the data back to original string				
				String decodedAuth = Base64.decodeAsString(authInfo.getBytes());
				System.out.println(decodedAuth);
				String[] credentials = decodedAuth.split(":");
				String userName = credentials[0];
				String pwd = credentials[1];
				String serverURL = "https://rego-consulting-test.sciforma.net/sciforma";
				long start = System.currentTimeMillis(); 
				session = new Session(serverURL);
				session.login(userName, pwd.toCharArray());
				if (session.isLoggedIn())
				{
					System.out.println("Yes logged in");
				}
				long end = System.currentTimeMillis(); 
		        System.out.println("Time Taken for Session: " + (end - start)/1000 + "s"); 
				return session;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return session;
	}

}
