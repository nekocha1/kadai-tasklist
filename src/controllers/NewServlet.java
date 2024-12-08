package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;

@WebServlet("/new")
public class NewServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションIDをリクエストスコープに保存
		req.setAttribute("_token", req.getSession().getId());

		//
		req.setAttribute("task", new Task());

		//new.jspにフォワード
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
        rd.forward(req, resp);
	}

}
