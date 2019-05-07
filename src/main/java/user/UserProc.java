package user;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/userServlet")
public class UserProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UserProc.class);

    public UserProc() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO uDao = new UserDAO();
		UserDTO uDto = null;
		RequestDispatcher rd = null;
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String uid = null;
		String upass = null;
		String url = null;
		
		if (action.equals("prepareForm")) {
			LOG.trace("prepareForm:");
			List<CompanyDTO> cList = uDao.getAllCompanies();
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../user/register.jsp");
	        rd.forward(request, response);
		} 
		else if (action.equals("register")) {
			uid = request.getParameter("uid");
			upass = request.getParameter("upass");
			String uname = request.getParameter("uname");
			int ucomId = Integer.parseInt(request.getParameter("ucomId"));
			LOG.debug("register: {}, {}, {}, {}", uid, upass, uname, ucomId);
			uDto = new UserDTO(uid, upass, uname, ucomId);
			uDao.registerUser(uDto);
			response.sendRedirect("../user/login.jsp");
		} 
		else if (action.equals("login")) {
			uid = request.getParameter("uid");
			upass = request.getParameter("upass");
			int result = uDao.verifyIdPassword(uid, upass);
			String errorMessage = null;
			switch (result) {
			case UserDAO.ID_PASSWORD_MATCH:
				break;
			case UserDAO.ID_DOES_NOT_EXIST:
				errorMessage = "ID가 없음"; break;
			case UserDAO.PASSWORD_IS_WRONG:
				errorMessage = "패스워드가 틀렸음"; break;
			case UserDAO.DATABASE_ERROR:
				errorMessage = "DB 오류";
			}
			if (result == UserDAO.ID_PASSWORD_MATCH) {
				uDto = uDao.getUserInfo(uid);
				session.setAttribute("userId", uid);
				session.setAttribute("userName", uDto.getUname());
				session.setAttribute("companyId", uDto.getUcomId());
				session.setAttribute("companyName", uDto.getUcomName());
				switch(uDto.getUcomRole()) {
				case UserDAO.ROLE_COMPANY:
					url = "../admin/index.jsp"; break;
				case UserDAO.ROLE_LOGISTICS:
					url = "../deliver/index.jsp"; break;
				case UserDAO.ROLE_SUPPLIER:
					url = "../purchase/index.jsp"; break;
				}
				response.sendRedirect(url);
			} else {
				request.setAttribute("message", errorMessage);
				request.setAttribute("url", "../user/login.jsp");
				rd = request.getRequestDispatcher("../common/alertMsg.jsp");
				rd.forward(request, response);
			}
		}
		else if (action.equals("logout")) {
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.removeAttribute("companyId");
			session.removeAttribute("companyName");
			response.sendRedirect("../user/login.jsp");
		} 
	}
}
