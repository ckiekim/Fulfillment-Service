package admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import closing.*;
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
		ClosingDAO cDao = new ClosingDAO();
		List<UserDTO> uList = null;
		List<CompanyDTO> cList = null;
		List<ProductDTO> pList = null;
		List<InvoiceDTO> vList = null;
		List<SoldProductDTO> sList = null;
		List<DeliveryDTO> dList = null;
		List<PurchaseDTO> rList = null;
		List<InventoryDTO> iList = null;
		List<RecordDTO> recList = null;
		ClosingDTO cDto = null;
		int sellers[] = {1011, 1012, 1013};
		int logistics[] = {1002, 1003, 1004, 1005};
		int suppliers[] = {1006, 1007, 1008, 1009, 1010};
		HandleClosing hClosing = null;
		HandleDate hDate = null;
		String date = null;
		String month = null;
		
		Cookie[] cookies = null; 
		String cookieId = null;
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		if (!action.equals("init")) {
			cookies = request.getCookies();
			for (Cookie cookie: cookies) {
				LOG.trace("{}, {}", cookie.getName(), cookie.getValue());
				if (cookie.getName().equals("EzenFS")) {
					cookieId = cookie.getValue();
					break;
				}
			}
			LOG.trace("{}, {}", cookieId, (String)session.getAttribute(cookieId+"companyName"));
			request.setAttribute("CookieId", cookieId);
			try {
				if (session.getAttribute(cookieId+"companyName") == null) {
					LOG.debug("Session timed-out!!!");
					action = "timeout";
				}
			} catch (IllegalStateException e) {
				LOG.info("IllegalStateException occurred!!!");
			}
		}
		
		if (action.equals("init")) {	// 관리자가 로그인하였을 때
			InitDTO iDto = (InitDTO)request.getAttribute("InitDTO");
			HandleDate hd = new HandleDate();
			cookieId = iDto.getUid() + hd.getDateAndTime();
			LOG.debug("cookieId = {}", cookieId);
			Cookie efsCookie = new Cookie("EzenFS", cookieId);
			efsCookie.setPath("/ezenFS/admin");
			response.addCookie(efsCookie);
			request.setAttribute("CookieId", cookieId);
			session.setAttribute(cookieId+"userId", iDto.getUid());
			session.setAttribute(cookieId+"userName", iDto.getUname());
			session.setAttribute(cookieId+"companyId", iDto.getCid());
			session.setAttribute(cookieId+"companyName", iDto.getCname());
			
			uList = uDao.getAllUsers();
			cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../admin/userList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("userList")) {	// 내비게이션 메뉴에서 사용자 조회를 클릭했을 때
			uList = uDao.getAllUsers();
			cList = uDao.getAllCompanies();
			request.setAttribute("userList", uList);
			request.setAttribute("companyList", cList);
			rd = request.getRequestDispatcher("../admin/userList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("productList")) {	// 내비게이션 메뉴에서 상품 조회를 클릭했을 때
			String category = request.getParameter("category");
			LOG.trace(category);
			pList = pDao.getProductsByCategory(category);
			request.setAttribute("productList", pList);
			rd = request.getRequestDispatcher("../admin/productList.jsp");
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
			rd = request.getRequestDispatcher("../admin/invoice.jsp");
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
			rd = request.getRequestDispatcher("../admin/invoiceDetail.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("deliver")) {	// 내비게이션 메뉴에서 출고확정 대기를 클릭했을 때
			dList = dDao.getDeliveryListByStatus(DeliveryDAO.DELIVERY_EXECUTED);
			request.setAttribute("deliverList", dList);
			rd = request.getRequestDispatcher("../admin/deliverList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("deliverConfirm")) {	// 출고 확정 버튼을 클릭했을 때
			dList = dDao.getDeliveryListByStatus(DeliveryDAO.DELIVERY_EXECUTED);
			HandleDelivery hDelivery = new HandleDelivery();
			hDelivery.confirmDelivery(dList);
			response.sendRedirect("../admin/adminServlet?action=deliverDaily");
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
			rd = request.getRequestDispatcher("../admin/deliverDaily.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("deliverMonthly")) {	// 내비게이션 메뉴에서 월별 출고내역을 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			month = request.getParameter("month");
			if (month == null || month.equals("")) {
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
			rd = request.getRequestDispatcher("../admin/deliverMonthly.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchase")) {	// 내비게이션 메뉴에서 구매확정 대기 메뉴를 클릭했을 때
			rList = rDao.getPurchaseListByStatus(PurchaseDAO.PURCHASE_SUPPLIED);
			request.setAttribute("purchaseList", rList);
			rd = request.getRequestDispatcher("../admin/purchaseList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchaseConfirm")) {	// 입고 확정 버튼을 클릭했을 때
			rList = rDao.getPurchaseListByStatus(PurchaseDAO.PURCHASE_SUPPLIED);
			HandlePurchase hPurchase = new HandlePurchase();
			hPurchase.confirmPurchase(rList);
			response.sendRedirect("../admin/adminServlet?action=purchaseDaily");
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
			rd = request.getRequestDispatcher("../admin/purchaseDaily.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("purchaseMonthly")) {	// 내비게이션 메뉴에서 월별 구매내역 메뉴를 클릭했을 때
			if (!request.getParameter("page").equals("")) {
				currentPage = Integer.parseInt(request.getParameter("page"));
			}
			month = request.getParameter("month");
			if (month == null || month.equals("")) {
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
			rd = request.getRequestDispatcher("../admin/purchaseMonthly.jsp");
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
			rd = request.getRequestDispatcher("../admin/inventoryList.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("inventoryMonth")) {	// 월별 재고를 클릭했을 때
			month = request.getParameter("month");
			if (month == null || month.equals("")) {
				hDate = new HandleDate();
				month = hDate.getToday().substring(0, 7);
			}
			HandleInventory hInventory = new HandleInventory();
			iList = hInventory.getMonthlyInventories(month);
			request.setAttribute("inventoryList", iList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("../admin/inventoryMonthly.jsp");
	        rd.forward(request, response);
		} 
		else if (action.equals("doClosing")) {	// 내비게이션 메뉴에서 정산 실행을 클릭했을 때
			hDate = new HandleDate();
			date = hDate.getToday().substring(8);
			if (Integer.parseInt(date) > 10)
				month = hDate.getToday().substring(0, 7);
			else 
				month = hDate.getLastMonth();
			
			hClosing = new HandleClosing();
			cDto = hClosing.processClosing(sellers, logistics, suppliers, month, HandleClosing.READY);
			request.setAttribute("ClosingDto", cDto);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SELLER, month);
			request.setAttribute("SellerList", recList);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_LOGISTICS, month);
			request.setAttribute("LogisticsList", recList);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SUPPLIER, month);
			request.setAttribute("SupplierList", recList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("../admin/closing.jsp");
	        rd.forward(request, response);
		} 
		else if (action.equals("closingConfirm")) {	// 정산 확정 버튼을 클릭했을 때
			hDate = new HandleDate();
			date = hDate.getToday().substring(8);
			if (Integer.parseInt(date) > 10)
				month = hDate.getToday().substring(0, 7);
			else 
				month = hDate.getLastMonth();
			hClosing = new HandleClosing();
			cDto = hClosing.processClosing(sellers, logistics, suppliers, month, HandleClosing.EXECUTE);
			response.sendRedirect("../admin/adminServlet?action=showClosingMonthly");
		}
		else if (action.equals("showClosingMonthly")) {	// 내비게이션 메뉴에서 월별 정산내역을 클릭했을 때
			month = request.getParameter("month");
			if (month == null || month.equals("")) {
				hDate = new HandleDate();
				date = hDate.getToday().substring(8);
				if (Integer.parseInt(date) > 10)
					month = hDate.getToday().substring(0, 7);
				else 
					month = hDate.getLastMonth();
			}
			hClosing = new HandleClosing();
			cDto = hClosing.showClosing(month);
			request.setAttribute("ClosingDto", cDto);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SELLER, month);
			request.setAttribute("SellerList", recList);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_LOGISTICS, month);
			request.setAttribute("LogisticsList", recList);
			recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SUPPLIER, month);
			request.setAttribute("SupplierList", recList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("../admin/closingMonthly.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("showClosingYearly")) {	// 내비게이션 메뉴에서 년간 정산내역을 클릭했을 때
			rd = request.getRequestDispatcher("../admin/closingYearly.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("showClosingGraph")) {	// 내비게이션 메뉴에서 월간 추이를 클릭했을 때
			int[] closingRecords = {23500, 19450, 50000, 34550, 54000, 39000, 50000, 48000, 40000, 55000, 62000, 49000};
			int[] closingRecords2 = {50000, 48000, 40000, 55000, 62000, 49000, 28500, 22450, 50000, 34550, 54000, 39000};
			request.setAttribute("ClosingRecords", closingRecords);
			request.setAttribute("ClosingRecords2", closingRecords2);
			rd = request.getRequestDispatcher("../admin/closingGraph.jsp");
			rd.forward(request, response);
		}
		else if (action.equals("timeout")) {	// 강제 로그아웃 당하는 경우
			String message = "30분 동안 액션이 없어서 로그아웃 되었습니다.";
			request.setAttribute("message", message);
			request.setAttribute("url", "../user/login.jsp");
			rd = request.getRequestDispatcher("../common/alertMsg.jsp");
			rd.forward(request, response);
		}
		else if (action.equals("logout")) {		// 상단 로그아웃을 클릭하였을 때
			cookies = request.getCookies();
			for (Cookie cookie: cookies) {
				LOG.debug("logout: {}, {}", cookie.getName(), cookie.getValue());
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			rd = request.getRequestDispatcher("../user/login.jsp");
			rd.forward(request, response);
		}
	}
}
