package deliver;

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

import admin.*;
import util.HandleDate;

@WebServlet("/deliver/deliverServlet")
public class DeliveryProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryProc.class);
       
    public DeliveryProc() {
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
		DeliveryDAO dDao = new DeliveryDAO();
		List<DeliveryDTO> dList = null;
		HandleDate hDate = null;
		String date = null;
		int curDeliveryPage = 1;
		List<String> pageList = new ArrayList<String>();
		
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		int logisId = 0;
		try {
			logisId = (Integer)session.getAttribute("companyId");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if (logisId == 0) {
			LOG.debug("Session timed-out!!!");
			action = "timeout";
		}
		
		if (action.equals("list")) {	// 운송업체 담당자가 로그인하였을 때
			List<InvoiceDTO> vList = dDao.getInvoicesByLogis(logisId);
			request.setAttribute("deliveryWaitList", vList);
			rd = request.getRequestDispatcher("list.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("release")) {	// 출고처리 버튼을 클릭하였을 때
			String time = request.getParameter("time");
			HandleDelivery hd = new HandleDelivery();
			hd.processDelivery(time, logisId);
			hDate = new HandleDate();
			date = hDate.getToday() + hDate.getNumericTime(time);
			dList = dDao.getDeliveryReleasedList(logisId, date);
			request.setAttribute("deliveryReleasedList", dList);
			rd = request.getRequestDispatcher("release.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("releaseList")) {	// 일별 출고목록 메뉴를 클릭하였을 때
			date = request.getParameter("dateRelease");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday();
			}
			dList = dDao.getDeliveryReleasedList(logisId, date+"%");
			request.setAttribute("deliveryReleasedList", dList);
			request.setAttribute("deliveryDate", date);
			rd = request.getRequestDispatcher("release.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("releaseMonthly")) {	// 월별 출고목록 메뉴를 클릭하였을 때
			if (!request.getParameter("page").equals("")) {
				curDeliveryPage = Integer.parseInt(request.getParameter("page"));
			}
			String month = request.getParameter("monthRelease");
			if (month == null) {
				hDate = new HandleDate();
				month = hDate.getToday().substring(0, 7);
			}
			int count = dDao.getCountMonthly(logisId, month+"-01");
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			request.setAttribute("currentPage", curDeliveryPage);
			for (int i=1; i<=pageNo; i++) 
				pageList.add(Integer.toString(i));
			
			dList = dDao.getDeliveryListByMonth(logisId, month+"-01", curDeliveryPage);
			request.setAttribute("deliveryReleasedList", dList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("Month", month);
			rd = request.getRequestDispatcher("releaseMonthly.jsp");
	        rd.forward(request, response);
		} 
		else if (action.equals("timeout")) {
			String message = "30분 동안 액션이 없어서 로그아웃 되었습니다.";
			request.setAttribute("message", message);
			request.setAttribute("url", "../user/login.jsp");
			rd = request.getRequestDispatcher("../common/alertMsg.jsp");
			rd.forward(request, response);
		}
	}
}
