package company;

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

@WebServlet("/company/comServlet")
public class CompanyProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CompanyProc.class);

    public CompanyProc() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyDAO comDao = new CompanyDAO();
		RequestDispatcher rd = null;
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if (action.equals("prepareForm")) {
			LOG.trace("prepareForm:");
			List<CompanyDTO> cList = comDao.getAllCompanies();
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("register.jsp");
	        rd.forward(request, response);
		} else if (action.equals("register")) {
			String uid = request.getParameter("uid");
			String upass = request.getParameter("upass");
			String uname = request.getParameter("uname");
			int ucomId = Integer.parseInt(request.getParameter("ucomId"));
			LOG.debug("register: {}, {}, {}, {}", uid, upass, uname, ucomId);
			UserDTO uDto = new UserDTO(uid, upass, uname, ucomId);
			comDao.registerUser(uDto);
			response.sendRedirect("../company/login.jsp");
		}
	}
}
