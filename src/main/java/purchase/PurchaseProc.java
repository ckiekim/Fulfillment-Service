package purchase;

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

import admin.*;
import user.*;
import util.HandleDate;
import weather.*;

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
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		ProductDAO pDao = new ProductDAO();
		PurchaseDAO rDao = new PurchaseDAO();  
		List<PurchaseDTO> rList = null;
		HandleDate hDate = null;
		String date = null;
		int curSupplyPage = 1;
		List<String> pageList = new ArrayList<String>();
		
		String cookieId = null;
		int suppId = 0;
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie: cookies) {
			LOG.debug("{}, {}", cookie.getName(), cookie.getValue());
			if (cookie.getName().equals("EzenFS")) {
				cookieId = cookie.getValue();
				break;
			}
		}
		LOG.debug("{}, {}", cookieId, (String)session.getAttribute(cookieId+"companyName"));
		request.setAttribute("CookieId", cookieId);
		try {
			suppId = (Integer)session.getAttribute(cookieId+"companyId");
		} catch (NullPointerException e) {
			LOG.info("NullPointerException occurred!!!");
		}
		if (suppId == 0) {
			LOG.debug("Session timed-out!!!");
			action = "timeout";
		}
		// 일기예보 반영
		WeatherUtil util = new WeatherUtil();
		if (util.checkWeather()) {
			util.changeWeather();
			String weatherInfo = util.getWeatherInfo();
			session.setAttribute("WeatherInfo", weatherInfo);
		}
		
		switch(action) {
		case "list":		// 공급요청 목록 메뉴를 클릭하였을 때
			rList = rDao.getPurchaseListBySupplier(suppId);
			request.setAttribute("purchaseWaitList", rList);
			rd = request.getRequestDispatcher("../purchase/list.jsp");
	        rd.forward(request, response);
			break;
		case "supply":		// 입고처리 버튼을 클릭했을 때
			HandlePurchase hp = new HandlePurchase();
			hp.processPurchase(suppId);
			response.sendRedirect("../purchase/purchaseServlet?action=purchaseList");
			break;
		case "purchaseList":	// 일별 공급내역 메뉴를 클릭했을 때
			date = request.getParameter("datePurchase");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday();
			}
			LOG.trace(date);
			rList = rDao.getSuppliedListBySupplierAndDate(suppId, date+"%");
			request.setAttribute("purchaseSuppliedList", rList);
			request.setAttribute("purchaseDate", date);
			rd = request.getRequestDispatcher("../purchase/supply.jsp");
	        rd.forward(request, response);
			break;
		case "purchaseMonthly":	// 월별 공급내역 메뉴를 클릭했을 때
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
			rd = request.getRequestDispatcher("../purchase/supplyMonthly.jsp");
			rd.forward(request, response);
			break;
		case "closingResult":	// 정산 메뉴를 클릭하였을 때
			int[] closingRecords = {23500, 12450, 50000, 34550, 54000, 39000, 50000, 48000, 40000, 55000, 62000, 49000};
			request.setAttribute("ClosingRecords", closingRecords);
			rd = request.getRequestDispatcher("../purchase/closingGraph.jsp");
			rd.forward(request, response);
			break;
		case "timeout":		// 강제 로그아웃 당하는 경우
			String message = "30분 동안 액션이 없어서 로그아웃 되었습니다.";
			request.setAttribute("message", message);
			request.setAttribute("url", "../user/login.jsp");
			rd = request.getRequestDispatcher("../common/alertMsg.jsp");
			rd.forward(request, response);
			break;
		default:
		}
	}
}
