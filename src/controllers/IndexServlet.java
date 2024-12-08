package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 開くページ数を取得（デフォルトは1ページ目）
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));//doGetで送信された値を取得
        } catch(NumberFormatException e) {
        	System.out.println("int型に変換できません");
        }

        // 最大15件のタスクをDBから取得
        EntityManager em = DBUtil.createEntityManager();
        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                                   .setFirstResult(15 * (page - 1))		//15 * (page - 1)件目から
                                   .setMaxResults(15)					//15件まで
                                   .getResultList();

        // 登録されているタスクの件数をDBから取得
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                                      .getSingleResult();
        em.close();

        //リクエストスコープに保存
		request.setAttribute("tasks", tasks);				//task一覧
		request.setAttribute("tasks_count", tasks_count);   // 全件数
        request.setAttribute("page", page);					// ページ数

		// フラッシュメッセージがセッションスコープにセットされていたら
		if(request.getSession().getAttribute("flush")!=null) {
			request.setAttribute("flush",request.getSession().getAttribute("flush"));//リクエストスコープに保存
			request.getSession().removeAttribute("flush");// セッションスコープからは削除
		}

		//index.jspにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
	    rd.forward(request, response);
	}

}
