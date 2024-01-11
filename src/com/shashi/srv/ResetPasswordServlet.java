package com.shashi.srv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shashi.service.impl.UserServiceImpl;


@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 public ResetPasswordServlet() {
     super();
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String userEmail = request.getParameter("email");
	    String newPassword = request.getParameter("newPassword");
	    String confirmPassword = request.getParameter("confirmPassword");

	    // Check if new password and confirm password are equal
	    if (!newPassword.equals(confirmPassword)) {
	        String errorMessage = "New password and confirm password do not match.";
	        redirectToErrorMessagePage(request, response, errorMessage);
	        return;
	    }

	    // Validate the new password
	    if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) {
	        String errorMessage = "Password should contain at least 8 characters, one capital letter, one small letter, one number, and one special character.";
	        redirectToErrorMessagePage(request, response, errorMessage);
	        return;
	    }

	    System.out.println("User email: " + userEmail);
	    // Update the password in the database
	    UserServiceImpl userService = new UserServiceImpl();
	    boolean passwordUpdated = false;
	    try {
	        passwordUpdated = userService.updatePassword(userEmail, newPassword);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    if (passwordUpdated) {
	        String successMessage = "Password updated successfully. Please login with your new password.";
	        response.sendRedirect("login.jsp?message=" + successMessage);
	    } else {
	        String errorMessage = "Error updating password. Please try again later.";
	        redirectToErrorMessagePage(request, response, errorMessage);
	    }
	}

 private void redirectToErrorMessagePage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
         throws ServletException, IOException {
     RequestDispatcher rd = request.getRequestDispatcher("newPassword.jsp?message=" + errorMessage);
     rd.forward(request, response);
 }
}
