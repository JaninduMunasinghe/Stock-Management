package com.xadmin.ordermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.ordermanagement.bean.Order;
import com.xadmin.ordermanagement.dao.OrderDao;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDao orderDao;
       
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		orderDao = new OrderDao();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
	try {	
	switch(action)
	{
	case "/new":
		showNewForm(request, response);
		break;
	
	case "/insert":
		insertOrder(request, response);
		break;
	
	case "/delete":
		deleteOrder(request, response);
		break;
	
	case "/edit":
		showEditForm(request,response);
		break;
	
	case "/update":
		updateOrder(request, response);
		break;
	
	default:
		listOrder(request,response);
		break;
	}
	
	}catch(SQLException ex) {
		throw new ServletException(ex);
	}
	
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
		dispatcher.forward(request, response);

	}
	
	//insert order
	private void insertOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		String pname = request.getParameter("pname");
		String supplier = request.getParameter("supplier");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		String date = request.getParameter("date");
		Order newOrder = new Order(pname, supplier, price, quantity, date);
		
		orderDao.insertOrder(newOrder);
		response.sendRedirect("list");
		
	}
	
	//delete user
	private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		
	    orderDao.deleteOrder(id);
		
		response.sendRedirect("list");
	}
	
	
	//edit 
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		
		Order existingOrder;
		try {
			existingOrder = orderDao.selectOrder(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
			request.setAttribute("order", existingOrder);
			dispatcher.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//update
	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String pname = request.getParameter("pname");
		String supplier = request.getParameter("supplier");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		String date = request.getParameter("date");
		
		Order order = new Order(id, pname, supplier, price, quantity, date);
			orderDao.updateOrder(order);
		response.sendRedirect("list");
	}
	
	//default
	
	private void listOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		try {
			List<Order> listOrder = orderDao.selectAllOrders();
			request.setAttribute("listOrder", listOrder);
			RequestDispatcher dispatcher = request.getRequestDispatcher("order-list.jsp");
			dispatcher.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	}
