package admin;

import java.io.IOException;
import java.util.ArrayList;
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
		int curInvoicePage = 1;
		List<String> pageList = new ArrayList<String>();
		
		if (action.equals("userList")) {
			UserDAO uDao = new UserDAO();
			List<UserDTO> uList = uDao.getAllUsers();
			List<CompanyDTO> cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../admin/userList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("productList")) {
			String category = request.getParameter("category");
			LOG.trace(category);
			ProductDAO pDao = new ProductDAO();
			List<ProductDTO> pList = pDao.getProductsByCategory(category);
			request.setAttribute("productList", pList);
			rd = request.getRequestDispatcher("../admin/productList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("invoice")) {
			if (!request.getParameter("page").equals("")) {
				curInvoicePage = Integer.parseInt(request.getParameter("page"));
			}
			InvoiceDAO vDao = new InvoiceDAO();
			int count = vDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			if (curInvoicePage > pageNo)	// 경계선에 걸렸을 때 대비
				curInvoicePage--;
			session.setAttribute("currentInvoicePage", curInvoicePage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
/*	int totalPage = aDao.getTotalPage();
	String pageArray[] = new String[totalPage];
	for (int i=0; i<totalPage; i++)
		pageArray[i] = Integer.toString(i+1);
	currentPage = Integer.parseInt(request.getParameter("page"));
	request.setAttribute("currentPage", Integer.toString(currentPage));	*/	
			List<InvoiceDTO> vList = vDao.getAllInvoices(curInvoicePage);
			request.setAttribute("invoiceList", vList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("../admin/invoice.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("procInvoice")) {
			HandleInvoice hi = new HandleInvoice();
			hi.handleFile();
			rd = request.getRequestDispatcher("adminServlet?action=invoice&page=1");
	        rd.forward(request, response);
		}
	}
}
