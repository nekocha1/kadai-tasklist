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

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID=1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String _token = req.getParameter("_token");
		if(_token != null && _token.equals(req.getSession().getId())) {//セッションIDが同じなら（CSRF対策）

			EntityManager em = DBUtil.createEntityManager();				//データベースに接続
			em.getTransaction().begin();

			Task t = new Task();
			String content = req.getParameter("content");					//リクエストパラメーターから取得
			t.setContent(content);											//タスク内容をセット

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());//現在日時の情報を取得
			t.setCreated_at(currentTime);									//作成日時をセット
			t.setUpdated_at(currentTime);									//更新日時をセット

			// バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = TaskValidator.validate(t);
            if(errors.size() > 0) {
                em.close();

                // リクエストスコープに保存
                req.setAttribute("_token", req.getSession().getId());//セッションID
                req.setAttribute("task", t);						//セットされたタスクオブジェクト
                req.setAttribute("errors", errors);					//エラー内容

                //new.jspにフォワード
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(req, resp);
            } else {
				em.persist(t);									//データベースをセーブ
				em.getTransaction().commit();					//データベースをコミット
				req.getSession().setAttribute("flush", "登録が完了しました");
				em.close();

				//IndexServletにリダイレクト
				resp.sendRedirect(req.getContextPath()+"/index");
            }
		}
	}

}
