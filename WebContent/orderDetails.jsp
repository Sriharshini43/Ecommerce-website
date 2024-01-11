<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.shashi.beans.OrderDetails" %>
<%@ page import="com.shashi.service.OrderService" %>
<%@ page import="com.shashi.service.impl.OrderServiceImpl" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
<title>Order Details</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/changes.css">
</head>
<body style="background-color: #E6F9E6;">

	<%
	/* Checking the user credentials */
	String userName = (String) session.getAttribute("username");
	String password = (String) session.getAttribute("password");

	if (userName == null || password == null) {

		response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");

	}

	OrderService dao = new OrderServiceImpl();
	List<OrderDetails> orders = dao.getAllOrderDetails(userName);

	
	%>

<%
    // Assuming you have a reference to your OrderService implementation
    OrderService orderService = new OrderServiceImpl();

    // Assuming this method returns all shipped orders
    List<OrderDetails> shippedOrders = orderService.getAllShippedOrders();

    long currentTimeMillis = Calendar.getInstance().getTimeInMillis();

    long deliveryThreshold = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

    for (OrderDetails order : shippedOrders) {
        // Check if the order has been shipped for more than the threshold time
        if (order.getTime() != null && order.getTime().before(new Timestamp(currentTimeMillis - deliveryThreshold))) {
       // Update the order status to "DELIVERED"
             orderService.updateOrderStatusToDelivered(order.getOrderId());
        }
    }
%>

	<jsp:include page="header.jsp" />

	<!-- <script>document.getElementById('mycart').innerHTML='<i data-count="20" class="fa fa-shopping-cart fa-3x icon-white badge" style="background-color:#333;margin:0px;padding:0px; margin-top:5px;"></i>'</script>
 -->
	<div class="text-center"
		style="color: green; font-size: 24px; font-weight: bold;">Order
		Details</div>
	<!-- Start of Product Items List -->
	<div class="container">
		<div class="table-responsive ">
			<table class="table table-hover table-sm">
				<thead
					style="background-color: black; color: white; font-size: 14px; font-weight: bold;">
					<tr>
						<th>Picture</th>
						<th>ProductName</th>
						<th>OrderId</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Time</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody
					style="background-color: white; font-size: 15px; font-weight: bold;">
					<%
					for (OrderDetails order : orders) {
					%>

					<tr>
						<td><img src="./ShowImage?pid=<%=order.getProductId()%>"
							style="width: 50px; height: 50px;"></td>
						<td><%=order.getProdName()%></td>
						<td><%=order.getOrderId()%></td>
						<td><%=order.getQty()%></td>
						<td><%=order.getAmount()%></td>
						<td><%=order.getTime() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getTime()) : "" %></td>
						<td class="text-success">
    <%
    int shippedStatus = order.getShipped();
    if (shippedStatus == 0) {
        out.println("ORDER_PLACED");
    } else if (shippedStatus == 1) {
        out.println("ORDER_SHIPPED");
    } else if (shippedStatus == 2) {
        out.println("DELIVERED");
    }
    %>
</td>
					</tr>

					<%
					}
					%>

				</tbody>
			</table>
		</div>
	</div>
	<!-- ENd of Product Items List -->


	<%@ include file="footer.html"%>
</body>
</html>