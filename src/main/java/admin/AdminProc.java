package admin;

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

import user.*;

@WebServlet("/admin/adminServlet")
public class AdminProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(AdminProc.class);
   
    public AdminProc() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if (action.equals("userList")) {
			UserDAO uDao = new UserDAO();
			List<UserDTO> uList = uDao.getAllUsers();
			List<CompanyDTO> cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../admin/userList.jsp");
	        rd.forward(request, response);
		}
	}
}
