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
		int logisId = 0;
		
		if (action.equals("list")) {	// 운송업체 담당자가 로그인하였을 때
			logisId = Integer.parseInt(request.getParameter("company"));
			List<InvoiceDTO> vList = dDao.getInvoicesByLogis(logisId);
			request.setAttribute("deliveryWaitList", vList);
			rd = request.getRequestDispatcher("list.jsp");
	        rd.forward(request, response);
		}
		else if (action.equals("release")) {
			String time = request.getParameter("time");
			HandleDelivery hd = new HandleDelivery();
			hd.processDelivery(time, logisId);
		}
	}
}
