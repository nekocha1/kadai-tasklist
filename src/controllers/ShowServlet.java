package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import util.DBUtil;

@WebServlet("/show")
public class ShowServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 該当のIDのタスクをデータベースから取得
		EntityManager em = DBUtil.createEntityManager();
		Task t = em.find(Task.class,Integer.parseInt(req.getParameter("id")));
		em.close();

		// タスクデータをリクエストスコープにセット
		req.setAttribute("task",t);

		//show.jspにフォワード
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/tasks/show.jsp");
        rd.forward(req, resp);
	}


}
