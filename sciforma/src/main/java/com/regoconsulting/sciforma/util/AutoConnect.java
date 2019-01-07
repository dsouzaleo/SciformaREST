package com.regoconsulting.sciforma.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sciforma.psnext.api.PSException;
import com.sciforma.psnext.api.Session;

@WebListener
public class AutoConnect implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*System.out.println("ServletContextListener started");	
		ServletContext context = arg0.getServletContext();
		String serverURL = "https://rego-consulting-test.sciforma.net/sciforma";
		Session session = null;
		try {
			session = new Session(serverURL);
			String pwd = "4W2UY%9rtquK";
			session.login("psnextadmin", pwd.toCharArray());
			if (session.isLoggedIn())
			{
				System.out.println("Yes logged in");
				session.pause();
				context.setAttribute("SciformaSession", session);
			}
		} catch (PSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
