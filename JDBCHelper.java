package com.imcs.jdbc;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementation of oracle database with java using jdbc
 * Includes methods for jdbc connection, SELECT, INSERT, UPDATE, and DELETE
 * @author Hem BK
 *
 */
public class JDBCHelper {
	//Establish a connection to oracle db using jdbc
	public Connection getConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");

		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
			throw classNotFoundException;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw sqlException;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		return con;
	}
	
	//retrieve the list of employees from employees table
	public void getEmployeeInfo(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
			System.out.println("Employee Data");

			while (rs.next()) {
				int employeeID = rs.getInt("EMPLOYEE_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");

				System.out.println("The employee is " + employeeID + " " + firstName + " " + lastName + " " + email);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	//retrieve specific record or row from employees table
	public void getEmployeeInfoUsingPreparedStmt(Connection con, int pEmpId, String pFirstName, String pLastName) {
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			pStmt = con
					.prepareStatement("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID=? AND FIRST_NAME=? AND LAST_NAME=?");
			pStmt.setInt(1, pEmpId);
			pStmt.setString(2, pFirstName);
			pStmt.setString(3, pLastName);
			rs = pStmt.executeQuery();
//			System.out.println("The details of employee just inserted is given below:");
			while (rs.next()) {
				int employeeID = rs.getInt("EMPLOYEE_ID");
				String firstName = rs.getString("FIRST_NAME");
				String lastName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");

				System.out.println("The employee ID is " + employeeID + ", full name is " + firstName + " " + lastName + ", and email is " + email);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pStmt.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	//adds a row or record into the employees table
	public void addEmployeeInfoUsingPreparedStmt(Connection con, int pEmpId, String pFirstName, String pLastName,
			String pEmail, String pPhone, Date pHireDate, String pJobId, int pSalary, int pComId, int pMgrId,
			int pDept_id) throws Exception {
		PreparedStatement pStmt = null;
		String insertQuery = "insert into employees (employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id )"
				           + " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
		try {
			pStmt = con.prepareStatement(insertQuery);
			pStmt.setInt(1, pEmpId);
			pStmt.setString(2, pFirstName);
			pStmt.setString(3, pLastName);
			pStmt.setString(4, pEmail);
			pStmt.setString(5, pPhone);
			pStmt.setDate(6, new java.sql.Date(pHireDate.getTime()));
			pStmt.setString(7, pJobId);
			pStmt.setInt(8, pSalary);
			pStmt.setInt(9, pComId);
			pStmt.setInt(10, pMgrId);
			pStmt.setInt(11, pDept_id);

			int insertRow = pStmt.executeUpdate();
			if (insertRow == 1) {
				System.out.println("Employee with ID " + pEmpId + " is added!");
				System.out.println("The details of employee just inserted is given below:");
				getEmployeeInfoUsingPreparedStmt(con,pEmpId, pFirstName, pLastName);
			}
			else if (insertRow == 0){
				System.out.println("Insertion unsuccessful!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	//update the specific row or record in the employees table
	public void updateEmployeeInfoUsingPreparedStmt(Connection con, int pEmpId, String pFirstName, String pLastName,
			String pEmail) throws Exception {
		PreparedStatement pStmt = null;
		String query = "UPDATE employees SET first_name = ? , " + "last_name = ? , " + "EMAIL = ? "
				+ "WHERE employee_id = ?";
		try {
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, pFirstName);
			pStmt.setString(2, pLastName);
			pStmt.setString(3, pEmail);
			pStmt.setInt(4, pEmpId);
			int updateRow = pStmt.executeUpdate();
			if (updateRow == 1) {
				System.out.println("Employee with ID " + pEmpId + " is successfully updated!");
				System.out.println("The details of employee just updated is given below:");
				getEmployeeInfoUsingPreparedStmt(con,pEmpId, pFirstName, pLastName);
				
			} else if (updateRow == 0) {
				System.out.println("update Unsucessful!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	// Removes an existing record in the table
	public void removeEmployeeInfo(Connection con, int pEmpId) throws Exception {
		PreparedStatement pStmt = null;
		String query = "DELETE FROM employees WHERE employee_id = ?";
		try {
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, pEmpId);
			int deletedRow = pStmt.executeUpdate();
			if (deletedRow == 1) {
				System.out.println("Employee with ID " + pEmpId + " is successfully deleted!");
			} else if (deletedRow == 0) {
				System.out.println("Delete Unsucessful!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

}
