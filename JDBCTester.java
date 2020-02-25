package com.imcs.jdbc;

import java.sql.Connection;
import java.util.Date;

public class JDBCTester {

	public static void main(String[] args) {
		Connection con = null;
		try {
		JDBCHelper jdbc = new JDBCHelper();
		con = jdbc.getConnection();
//		jdbc.getEmployeeInfo(con);
//		jdbc.getEmployeeInfoUsingPreparedStmt(con, 100, "Steven", "King");
//		jdbc.removeEmployeeInfo(con, 216);
//		jdbc.addEmployeeInfoUsingPreparedStmt(con, 217, "Sandesh", "Bhandari", "sbhand", "12333", new Date(), "AC_MGR", 1245, 0, 101, 110);
//		jdbc.updateEmployeeInfoUsingPreparedStmt(con, 216, "Hem", "BK", "bkhem");
		}
		catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Exception Details: \n"+exception.getClass()+ " "+ exception.getMessage()+"\n");
		}
		finally {
			try {
			con.close();
			}
			catch (Exception exception) {
				exception.printStackTrace();
				
			}
		}
	}

}
