package ca.ucalagary.seng696.g12.databases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.jdbc.*;

public class DatabaseUtils {
	// Run this main function just for test
	public static void main(String[] args) {
		DatabaseUtils.initUsersTable();
		DatabaseUtils.initResumesTable();
		DatabaseUtils.printUsers();
		DatabaseUtils.printResumes();
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		return conn;
	}
	
	public static void closeConnection() {
		// Closing the connection
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (ex.getErrorCode() != 50000) {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}
	public static void initUsersTable() {
		String createUsersSQL = "CREATE TABLE USERS ("
				+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "NAME VARCHAR(30) NOT NULL," + "EMAIL VARCHAR(30)," + "PASSWORD VARCHAR(30),"
				+ "TYPE VARCHAR(1) CONSTRAINT TYPE_CONSTRAINT CHECK (TYPE IN ('P', 'C')),"
				+ "CONSTRAINT USERS_PK PRIMARY KEY (ID)," + "CONSTRAINT USERS_UQ UNIQUE (EMAIL))";
		// Creating the table and insert initial data
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			DatabaseMetaData databaseMetadata = conn.getMetaData();
			ResultSet resultSet = databaseMetadata.getTables(null, null, "USERS", null);
			if (resultSet.next()) {
				System.out.println("TABLE USERS ALREADY EXISTS");
			} else {
				// create table
				stmt.execute(createUsersSQL);
				conn.commit();
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
		// initialize
		DatabaseUtils.addProvider("Mahdi J.Ansari", "m.j@ucalgary.ca", "p1");
		DatabaseUtils.addProvider("Majid Askari", "m.a@ucalgary.ca", "p2");
		DatabaseUtils.addClient("Mahta Ghorbani", "m.g@ucalgary.ca", "c1");
		DatabaseUtils.addClient("Mitra Mir", "m.m@ucalgary.ca", "c2");
	}

	public static void addProvider(String name, String email, String password) {
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setString(4, "P");
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
	}

	public static void addClient(String name, String email, String password) {
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setString(4, "C");
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
	}

	public static void initResumesTable() {
		String createResumesSQL = "CREATE TABLE RESUMES (" + "ID INTEGER NOT NULL," + "KEYWORDS VARCHAR(100) NOT NULL,"
				+ "BODY VARCHAR(1000)," + "UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
				+ "CONSTRAINT RESUMES_PK PRIMARY KEY (ID),"
				+ "CONSTRAINT RESUMES_FK FOREIGN KEY (ID) REFERENCES USERS(ID))";
		// Creating the table and insert initial data
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			DatabaseMetaData databaseMetadata = conn.getMetaData();
			ResultSet resultSet = databaseMetadata.getTables(null, null, "RESUMES", null);
			if (resultSet.next()) {
				System.out.println("TABLE RESUMES ALREADY EXISTS");
			} else {
				stmt.execute(createResumesSQL);
				conn.commit();
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
		
		DatabaseUtils.addResume("1", "C++, JAVA, C#, SQLSERVER", "I did 200 projects ...");
		DatabaseUtils.addResume("2", "PHP, MYSQL, PHYTHON", "I am better than others ...");
	}

	public static void addResume(String userId, String keywords, String body) {
		try {
			Connection conn = DatabaseUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RESUMES (ID,KEYWORDS,BODY) VALUES(?,?,?)");
			pstmt.setString(1, userId);
			pstmt.setString(2, keywords);
			pstmt.setString(3, body);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
	}

	public static void printUsers() {
		ResultSet rs = null;
		String readUsersSQL = "SELECT ID, NAME, EMAIL, PASSWORD, TYPE FROM USERS";
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
	}

	public static void printResumes() {
		ResultSet rs = null;
		String readResumesSQL = "SELECT ID, KEYWORDS, BODY, UPDATED_AT FROM RESUMES";
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readResumesSQL);
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
	}

	public static ResultSet getProviders() {
		ResultSet rs = null;
		String readUsersSQL = "SELECT ID, NAME, EMAIL, TYPE FROM USERS WHERE TYPE = 'P'";
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readUsersSQL);
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
		return rs;
	}

	public static ResultSet getResumes() {
		ResultSet rs = null;
		String readResumesSQL = "SELECT ID, KEYWORDS, BODY, UPDATED_AT FROM RESUMES";
		try {
			Connection conn = DatabaseUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readResumesSQL);
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DatabaseUtils.closeConnection();
		return rs;
	}
}
