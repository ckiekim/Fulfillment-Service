package purchase;

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
		else if (action.equals("purchaseList")) {	// 일별 구매목록 메뉴를 클릭했을 때
			String date = request.getParameter("datePurchase");
			if (date == null) {
				HandleDate hDate = new HandleDate();
				date = hDate.getToday();
			}
			date += "%";
			rList = rDao.getSuppliedListBySupplierAndDate(suppId, date);
			request.setAttribute("purchaseSuppliedList", rList);
			rd = request.getRequestDispatcher("purchased.jsp");
	        rd.forward(request, response);
		}
	}
}
