package com.shashi.srv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shashi.beans.UserBean;
import com.shashi.service.impl.UserServiceImpl;

/**
 * Servlet implementation class RegisterSrv
 */
@WebServlet("/RegisterSrv")
public class RegisterSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String userName = request.getParameter("username");
		Long mobileNo = Long.parseLong(request.getParameter("mobile"));
		String emailId = request.getParameter("email");
		String address = request.getParameter("address");
		int pinCode = Integer.parseInt(request.getParameter("pincode"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String status = "";
		
		// Validate if username is a combination of small and capital letters
		if (!userName.matches("^[a-zA-Z]+$")) {
		    String errorMessage = "Username should contain only small and capital letters.";
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + errorMessage);
		    rd.forward(request, response);
		    return;
		}


		// Validate if the password meets the specified criteria
		if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) {
		    String errorMessage = "Password should contain at least 8 characters, one capital letter, one small letter, one number, and one special character.";
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + errorMessage);
		    rd.forward(request, response);
		    return;
		}
		
		if (password == null || !password.equals(confirmPassword)) {
		    String errorMessage = "Passwords do not match.";
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + errorMessage);
		    rd.forward(request, response);
		    return;
		}

		// Validate if email is in the format of @gmail.com
		if (!emailId.matches("^[a-zA-Z0-9_.]+@gmail\\.com$")) {
		    String errorMessage = "Email should be in the format of @gmail.com.";
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + errorMessage);
		    rd.forward(request, response);
		    return;
		}
        
		// Validate if mobile number is numeric and has exactly 10 digits
		if (!mobileNo.toString().matches("^\\d{10}$")) {
		    String errorMessage = "Mobile number should be 10 digits.";
		    RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + errorMessage);
		    rd.forward(request, response);
		    return;
		}
		
		// Continue with the registration logic if all validations pass
		UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password);
		UserServiceImpl dao = new UserServiceImpl();
		status = dao.registerUser(user);

		RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
