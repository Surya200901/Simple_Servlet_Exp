package com.disptacher.com;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FirstServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) 
			throws ServletException, IOException
	{
		String Prdname=req.getParameter("pn");
		String Pquantity=req.getParameter("pq");
		//Add the data into scope
		req.setAttribute("pname",Prdname);
		req.setAttribute("pqty", Pquantity);
		RequestDispatcher rd=req.getRequestDispatcher("ss");
		rd.forward(req, resp);
	}
}
