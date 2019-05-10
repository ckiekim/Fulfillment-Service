package purchase;

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

import admin.ProductDAO;
import util.HandleDate;

@WebServlet("/purchase/purchaseServlet")
public class PurchaseProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(PurchaseProc.class);

	public PurchaseProc() {
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
		ProductDAO pDao = new ProductDAO();
		PurchaseDAO rDao = new PurchaseDAO();  
		List<PurchaseDTO> rList = null;
		HandleDate hDate = null;
		String date = null;
		int curSupplyPage = 1;
		List<String> pageList = new ArrayList<String>();
		
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		int suppId = 0;
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		try {
			suppId = (Integer)session.getAttribute("companyId");
		} catch (NullPointerException e) {
			System.out.println("세션이 만료되었습니다.");
		}
		if (suppId == 0) {
			rd = request.getRequestDispatcher("../user/login.jsp");
	        rd.forward(request, response);
		}
		
		if (action.equals("list")) {	// 공급업체 담당자가 로그인하였을 때
			rList = rDao.getPurchaseListBySupplier(suppId);
			request.setAttribute("purchaseWaitList", rList);
			rd = request.getRequestDispatcher("list.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("supply")) {		// 입고처리 버튼을 클릭했을 때
			HandlePurchase hp = new HandlePurchase();
			hp.processPurchase(suppId);
			response.sendRedirect("purchaseServlet?action=purchaseList");
		}
		else if (action.equals("purchaseList")) {	// 일별 공급내역 메뉴를 클릭했을 때
			date = request.getParameter("datePurchase");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday();
			}
			LOG.trace(date);
			rList = rDao.getSuppliedListBySupplierAndDate(suppId, date+"%");
			request.setAttribute("purchaseSuppliedList", rList);
			request.setAttribute("purchaseDate", date);
			rd = request.getRequestDispatcher("supply.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchaseMonthly")) {	// 월별 공급내역 메뉴를 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				curSupplyPage = Integer.parseInt(request.getParameter("page"));
			}
			String month = request.getParameter("month");
			if (month == null) {
				hDate = new HandleDate();
				month = hDate.getToday().substring(0, 7);
			}
			LOG.trace(month);
			int count = rDao.getCountMonthly(suppId, month+"-01");
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", curSupplyPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			
			rList = rDao.getSuppliedListByMonth(suppId, month+"-01", curSupplyPage);
			request.setAttribute("purchaseSuppliedList", rList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("purchaseMonth", month);
			rd = request.getRequestDispatcher("supplyMonthly.jsp");
			rd.forward(request, response);
		}
	}
}
