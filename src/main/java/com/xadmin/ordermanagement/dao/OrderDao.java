package com.xadmin.ordermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.jdbc.PreparedStatement;
import com.xadmin.ordermanagement.bean.Order;

public class OrderDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/orderdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "@Janindu1999#";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	
	private static final String INSERT_ORDERS_SQL = "INSERT INTO orders" + "  (pname, supplier, price, quantity, date) VALUES "
			+ " (?,?,?,?,?);";

	private static final String SELECT_ORDERS_BY_ID = "select id,pname,supplier,price, quantity, date from orders where id =?";
	private static final String SELECT_ALL_ORDERS = "select * from orders";
	private static final String DELETE_ORDERS_SQL = "delete from orders where id = ?;";
	private static final String UPDATE_ORDERS_SQL = "update orders set pname = ?,supplier= ?, price =?, quantity=?,date=? where id = ?;";
	
	public OrderDao() {
	}
	
	protected Connection getConnection()
	{
		Connection connection = null; 
		//Connection connection = getConnection();
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return connection;
	}
	
	//insert order
	public void insertOrder(Order order) throws SQLException{
		System.out.println(INSERT_ORDERS_SQL);
		try(
				Connection connection = getConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDERS_SQL)){
			preparedStatement.setString(1, order.getPname());
			preparedStatement.setString(2, order.getSupplier());
			preparedStatement.setString(3, order.getPrice());
			preparedStatement.setString(4, order.getQuantity());
			preparedStatement.setString(5, order.getDate());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		}catch(SQLException e) {
			printSQLException(e);
		}
	}
	
	
	//select order by id
	public Order selectOrder(int id) {
		Order order = null;
		//Step 1: Establishing connection
		try(Connection connection = getConnection();
				//Step 2:  Create a statement using connection object 
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_ID);){
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			//Step 3: execute the query or update the query
			ResultSet rs = preparedStatement.executeQuery();
			
			//Step 4: process the ResultSet object
			while(rs.next()) {
				String pname = rs.getString("pname");
				String supplier = rs.getString("supplier");
				String price = rs.getString("price");
				String quantity = rs.getString("price");
				String date = rs.getString("date");
				order = new Order(id,pname,supplier,price,quantity,date);
			}
		}catch(SQLException e) {
			printSQLException(e);
		}
		return order;
	}
	
	
	//select all orders
	
	public List<Order> selectAllOrders(){
		
		//using try-with -resources to avoid closing resources(boiler plate code)
		List<Order> order =  new ArrayList<>();
		//step 1: Establishing the connection
		
		try(Connection connection = getConnection();
				
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);){
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String pname = rs.getString("pname");
				String supplier = rs.getString("supplier");
				String price = rs.getString("price");
				String quantity = rs.getString("quantity");
				String date = rs.getString("date");
				order.add(new Order(id, pname, supplier, price, quantity, date));
			}
		}catch(SQLException e) {
			printSQLException(e);
		}
		return order;
	}
	
	
	//update order
	public boolean updateOrder(Order order) throws SQLException {
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_ORDERS_SQL);){
			System.out.println("update order:"+statement);
			statement.setString(1, order.getPname());
			statement.setString(2, order.getSupplier());
			statement.setString(3, order.getPrice());
			statement.setString(4, order.getQuantity());
			statement.setString(5, order.getDate());
			statement.setInt(6, order.getId());
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		
		return rowUpdated;
	}
	

	//delete order
	
	public boolean deleteOrder(int id) throws SQLException{
		boolean rowDeleted;
		
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_ORDERS_SQL);){
			statement.setInt(1,  id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
	
	
}
