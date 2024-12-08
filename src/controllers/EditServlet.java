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

@WebServlet("/edit")
public class EditServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {EntityManager em = DBUtil.createEntityManager();

    // 該当のIDのタスクをデータベースから取得
    Task t = em.find(Task.class, Integer.parseInt(req.getParameter("id")));
    em.close();

    // リクエストスコープに登録
    req.setAttribute("task", t);							//取得したタスク
    req.setAttribute("_token", req.getSession().getId());	//セッションID

    //該当のIDのタスクが存在していたら
    if(t != null) {
        req.getSession().setAttribute("task_id", t.getId());	//タスクIDをセッションスコープに登録
    }

    //edit.jspにフォワード
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
    rd.forward(req,resp);

	}

}
