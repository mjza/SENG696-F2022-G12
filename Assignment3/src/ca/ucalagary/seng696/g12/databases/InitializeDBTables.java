package ca.ucalagary.seng696.g12.databases;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.*;

public class InitializeDBTables {
	public static void main(String[] args) {
		InitializeDBTables e = new InitializeDBTables();
		e.testDerby();
	}

	public void testDerby() {
		Connection conn = null;
		PreparedStatement pstmt;
		Statement stmt;
		ResultSet rs = null;
		String createUsersSQL = "CREATE TABLE USERS ("
				+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "NAME VARCHAR(30) NOT NULL," + "EMAIL VARCHAR(30)," + "PASSWORD VARCHAR(30),"
				+ "TYPE VARCHAR(1) CONSTRAINT TYPE_CONSTRAINT CHECK (TYPE IN ('P', 'C')),"
				+ "CONSTRAINT PRIMARY_KEY PRIMARY KEY (ID))";

		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(createUsersSQL);

			pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
			pstmt.setString(1, "Mahdi J.Ansari");
			pstmt.setString(2, "m.j@ucalgary.ca");
			pstmt.setString(3, "p1");
			pstmt.setString(4, "P");
			pstmt.executeUpdate();

			conn.commit();

		} catch (SQLException ex) {
			if ("X0Y32".equals(ex.getSQLState()) == false)
				System.out.println("in connection" + ex);
		}
		
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
				System.out.println("Derby shut down normally");
			} else {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}

		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(true);
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM USERS");
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (SQLException ex) {
			//System.out.println("in connection" + ex);
		}

		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
				System.out.println("Derby shut down normally");
			} else {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}
}
