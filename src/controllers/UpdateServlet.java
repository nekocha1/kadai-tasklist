package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import util.DBUtil;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String _token = req.getParameter("_token");
		if(_token != null && _token.equals(req.getSession().getId())) {	//セッションIDが同じなら（CSRF対策）

			// セッションスコープからメッセージのIDを取得して
            // 該当のIDのメッセージ1件のみをデータベースから取得
			EntityManager em = DBUtil.createEntityManager();
			Task t = em.find(Task.class,(Integer)(req.getSession().getAttribute("task_id")));

			// フォームの内容を各フィールドに上書き
			String content = req.getParameter("content");
			t.setContent(content);

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			t.setUpdated_at(currentTime);// 更新日時のみ上書き

			// バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = TaskValidator.validate(t);
            if(errors.size() > 0) {
                em.close();

                // フォームに初期値を設定、さらにエラーメッセージを送る
                req.setAttribute("_token", req.getSession().getId());
                req.setAttribute("message", t);
                req.setAttribute("errors", errors);

                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(req, resp);
            } else {
				// データベースを更新
				em.getTransaction().begin();
				em.getTransaction().commit();
				req.getSession().setAttribute("flush","更新が完了しました");
				em.close();

				// セッションスコープ上の不要になったデータを削除
				req.getSession().removeAttribute("tasks_id");

				// indexページへリダイレクト
				resp.sendRedirect(req.getContextPath()+"/index");
            }
		}
	}

}
