package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import util.DBUtil;

@WebServlet("/index")
public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID=1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();
		List<Task> tasks = em.createNamedQuery("getAllTasks",Task.class).getResultList();
		resp.getWriter().append(Integer.valueOf(tasks.size()).toString());
	}

}
