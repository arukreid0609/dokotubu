package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MuttersDAO;
import model.Mutter;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// つぶやきリストをアプリケーションスコープから取得
		//		ServletContext application = this.getServletContext();
		//		List<Mutter> mutterList = (List<Mutter>)application.getAttribute("mutterList");
		MuttersDAO dao = new MuttersDAO();
		List<Mutter> mutterList = dao.findAll();
		request.setAttribute("mutterList", mutterList);

		// 取得できなかった場合は、つぶやきリストを新規作成して
		// アプリケーションスコープに保存
		//		if(mutterList == null) {
		//			mutterList = new ArrayList<Mutter>();
		//			application.setAttribute("mutterList", mutterList);
		//		}

		// ログインしているか確認するため
		// セッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) { // ログインしていない場合
			// リダイレクト
			response.sendRedirect("inde.jsp");
		} else {
			// フォワード
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/main.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");

		MuttersDAO dao = new MuttersDAO();

		// 入力値チェック
		if (text != null && text.length() != 0) {
			// セッションスコープに保存されたユーザー情報を取得
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");

			// つぶやきを作成してつぶやきリストに追加
			Mutter mutter = new Mutter(loginUser.getName(), text);
			dao.insertOne(mutter);
			//			PostMutterLogic postMutterLogic = new PostMutterLogic();
			//			postMutterLogic.execute(mutter, mutterList);
		} 
		
		// アプリケーションスコープに保存されたつぶやきリストを取得
		//			ServletContext application = this.getServletContext();
		//			List<Mutter> mutterList = (List<Mutter>)application.getAttribute("mutterList");
		List<Mutter> mutterList = dao.findAll();

		// アプリケーションスコープにつぶやきリストを保存
		//			application.setAttribute("mutterList", mutterList);
		request.setAttribute("mutterList", mutterList);

		// メイン画面にフォワード
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/main.jsp");
		rd.forward(request, response);
	}
}
