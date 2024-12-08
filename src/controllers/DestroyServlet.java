package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import util.DBUtil;

@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String _token = req.getParameter("_token");
		if(_token != null && _token.equals(req.getSession().getId())) {//セッションIDが同じなら(CSRF対策)
			EntityManager em = DBUtil.createEntityManager();

			// セッションスコープからIDを取得して
            // 該当のIDのタスクをデータベースから取得
			Task m = em.find(Task.class,(Integer)req.getSession().getAttribute("task_id"));

			em.getTransaction().begin();
			em.remove(m);					// データ削除
			em.getTransaction().commit();
			req.getSession().setAttribute("flush", "削除が完了しました");
			em.close();

			// セッションスコープ上の不要になったデータを削除
			req.getSession().removeAttribute("task_id");

			// indexページへリダイレクト
			resp.sendRedirect(req.getContextPath()+"/index");
		}
	}

}
