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

import deliver.*;
import purchase.*;
import user.*;
import inventory.*;
import util.HandleDate;

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
		int currentPage = 1;
		List<String> pageList = new ArrayList<String>();
		UserDAO uDao = new UserDAO();
		ProductDAO pDao = new ProductDAO();
		InvoiceDAO vDao = new InvoiceDAO();
		SoldProductDAO sDao = new SoldProductDAO();
		DeliveryDAO dDao = new DeliveryDAO();
		PurchaseDAO rDao = new PurchaseDAO();
		InventoryDAO iDao = new InventoryDAO();
		List<UserDTO> uList = null;
		List<CompanyDTO> cList = null;
		List<ProductDTO> pList = null;
		List<InvoiceDTO> vList = null;
		List<SoldProductDTO> sList = null;
		List<DeliveryDTO> dList = null;
		List<PurchaseDTO> rList = null;
		List<InventoryDTO> iList = null;
		String date = null;
		HandleDate hDate = null;
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		if (session.getAttribute("companyName") == null) {
			LOG.debug("Session timed-out!!!");
			rd = request.getRequestDispatcher("../user/userServlet?action=logout");
	        rd.forward(request, response);
		}
		
		if (action.equals("userList")) {	// 내비게이션 메뉴에서 사용자 조회를 클릭했을 때
			uList = uDao.getAllUsers();
			cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("userList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("productList")) {	// 내비게이션 메뉴에서 상품 조회를 클릭했을 때
			String category = request.getParameter("category");
			LOG.trace(category);
			pList = pDao.getProductsByCategory(category);
			request.setAttribute("productList", pList);
			rd = request.getRequestDispatcher("productList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("invoice")) {	// 내비게이션 메뉴에서 주문을 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			int count = vDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", currentPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			vList = vDao.getInvoicesByPage(currentPage);
			
			request.setAttribute("invoiceList", vList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("invoice.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("procInvoice")) {	// 주문처리 버튼을 클릭했을 때
			HandleInvoice hi = new HandleInvoice();
			hi.handleFile();
			response.sendRedirect("adminServlet?action=invoice&page=1");
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
		else if (action.equals("deliver")) {	// 내비게이션 메뉴에서 출고확정 대기를 클릭했을 때
			dList = dDao.getDeliveryListByStatus(DeliveryDAO.DELIVERY_EXECUTED);
			request.setAttribute("deliverList", dList);
			rd = request.getRequestDispatcher("deliverList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("deliverConfirm")) {	// 출고 확정 버튼을 클릭했을 때
			dList = dDao.getDeliveryListByStatus(DeliveryDAO.DELIVERY_EXECUTED);
			HandleDelivery hDelivery = new HandleDelivery();
			hDelivery.confirmDelivery(dList);
			response.sendRedirect("adminServlet?action=deliverDaily");
		}
		else if (action.equals("deliverDaily")) {	// 내비게이션 메뉴에서 일별 출고내역을 클릭했을 때
			date = request.getParameter("dateRelease");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday();
			}
			dList = dDao.getDeliveryListByDate(date+"%");
			request.setAttribute("deliverList", dList);
			request.setAttribute("deliveryDate", date);
			rd = request.getRequestDispatcher("deliverDaily.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("deliverMonthly")) {	// 내비게이션 메뉴에서 월별 출고내역을 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			String month = request.getParameter("month");
			if (month == null) {
				hDate = new HandleDate();
				month = hDate.getToday().substring(0, 7);
			}
			int count = dDao.getCountMonthly(0, month+"-01");
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", currentPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			
			dList = dDao.getDeliveryListByMonth(0, month+"-01", currentPage);
			request.setAttribute("deliveryReleasedList", dList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("deliverMonthly.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchase")) {	// 내비게이션 메뉴에서 구매를 클릭했을 때
			rList = rDao.getPurchaseListByStatus(PurchaseDAO.PURCHASE_SUPPLIED);
			request.setAttribute("purchaseList", rList);
			rd = request.getRequestDispatcher("purchaseList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchaseConfirm")) {	// 입고 확정 버튼을 클릭했을 때
			rList = rDao.getPurchaseListByStatus(PurchaseDAO.PURCHASE_SUPPLIED);
			HandlePurchase hPurchase = new HandlePurchase();
			hPurchase.confirmPurchase(rList);
			response.sendRedirect("adminServlet?action=purchaseDaily");
		}
		else if (action.equals("purchaseDaily")) {	// 내비게이션 메뉴에서 일별 구매내역 메뉴를 클릭했을 때
			date = request.getParameter("dateSupply");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday();
			}
			rList = rDao.getSuppliedListByDate(date+"%");
			request.setAttribute("purchaseList", rList);
			request.setAttribute("purchaseDate", date);
			rd = request.getRequestDispatcher("purchaseDaily.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchaseMonthly")) {	// 내비게이션 메뉴에서 월별 구매내역 메뉴를 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			String month = request.getParameter("month");
			if (month == null) {
				hDate = new HandleDate();
				month = hDate.getToday().substring(0, 7);
			}
			int count = rDao.getCountMonthly(0, month+"-01");
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", currentPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			
			rList = rDao.getSuppliedListByMonth(0, month+"-01", currentPage);
			request.setAttribute("purchaseList", rList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("purchaseMonthly.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("inventory")) {	// 내비게이션 메뉴에서 재고를 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			int count = iDao.getCount();
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", currentPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			iList = iDao.getInventoriesByPage(currentPage);
			request.setAttribute("inventoryList", iList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("inventoryList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("inventoryMonth")) {	// 월별 재고를 클릭했을 때
			String month = request.getParameter("month");
			HandleInventory hInventory = new HandleInventory();
			iList = hInventory.getMonthlyInventories(month);
			request.setAttribute("inventoryList", iList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("inventoryMonthly.jsp");
	        rd.forward(request, response);
		}
	}
}
