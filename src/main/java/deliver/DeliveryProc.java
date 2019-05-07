package deliver;

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
		HandleDate hDate = null;
		String date = null;
		int logisId = 0;
		// 세션이 만료되었으면 다시 로그인하게 만들어 줌
		try {
			logisId = (Integer)session.getAttribute("companyId");
		} catch (NullPointerException e) {
			System.out.println("세션이 만료되었습니다.");
		}
		if (logisId == 0) {
			rd = request.getRequestDispatcher("../user/login.jsp");
	        rd.forward(request, response);
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
			List<DeliveryDTO> dList = dDao.getDeliveryReleasedList(logisId, date);
			request.setAttribute("deliveryReleasedList", dList);
			rd = request.getRequestDispatcher("release.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("releaseList")) {	// 일별 출고목록 메뉴를 클릭하였을 때
			date = request.getParameter("dateRelease");
			if (date == null) {
				hDate = new HandleDate();
				date = hDate.getToday() + "%";
			}
			List<DeliveryDTO> dList = dDao.getDeliveryReleasedList(logisId, date);
			request.setAttribute("deliveryReleasedList", dList);
			rd = request.getRequestDispatcher("release.jsp");
	        rd.forward(request, response);
		}
	}
}
