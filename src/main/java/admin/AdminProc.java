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
		UserDAO uDao = new UserDAO();
		ProductDAO pDao = new ProductDAO();
		InvoiceDAO vDao = new InvoiceDAO();
		SoldProductDAO sDao = new SoldProductDAO();
		List<UserDTO> uList = null;
		List<CompanyDTO> cList = null;
		List<ProductDTO> pList = null;
		List<InvoiceDTO> vList = null;
		List<SoldProductDTO> sList = null;
		
		if (action.equals("userList")) {	// 내비게이션 메뉴에서 사용자 조회를 클릭했을 때
			uList = uDao.getAllUsers();
			cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../admin/userList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("productList")) {	// 내비게이션 메뉴에서 제품 조회를 클릭했을 때
			String category = request.getParameter("category");
			LOG.trace(category);
			pList = pDao.getProductsByCategory(category);
			request.setAttribute("productList", pList);
			rd = request.getRequestDispatcher("../admin/productList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("invoice")) {	// 내비게이션 메뉴에서 주문을 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				curInvoicePage = Integer.parseInt(request.getParameter("page"));
			}
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
			vList = vDao.getAllInvoices(curInvoicePage);
			
			request.setAttribute("invoiceList", vList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("../admin/invoice.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("procInvoice")) {	// 주문처리 버튼을 클릭했을 때
			HandleInvoice hi = new HandleInvoice();
			hi.handleFile();
			rd = request.getRequestDispatcher("adminServlet?action=invoice&page=1");
	        rd.forward(request, response);
		}
		else if (action.equals("invoiceDetail")) {	// invoice 리스트에서 id를 클릭했을 때
			int invId = 0;
			if (!request.getParameter("vid").equals("")) {
				invId = Integer.parseInt(request.getParameter("vid"));
			}
			InvoiceDTO vDto = vDao.getInvoiceById(invId);
			sList = sDao.getSoldProducts(invId);
			request.setAttribute("invoiceDTO", vDto);
			request.setAttribute("soldProductList", sList);
			rd = request.getRequestDispatcher("invoiceDetail.jsp");
	        rd.forward(request, response);
		}
	}
}
