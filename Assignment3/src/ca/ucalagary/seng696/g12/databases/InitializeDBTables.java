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

public class InitializeDBTables {
	public static void main(String[] args) {
		InitializeDBTables e = new InitializeDBTables();
		e.initUsersTable();
		e.initResumesTable();
		e.readUsers();
		e.readResumes();
	}

	public void initUsersTable() {
		Connection conn = null;
		PreparedStatement pstmt;
		Statement stmt;
		String createUsersSQL = "CREATE TABLE USERS ("
				+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "NAME VARCHAR(30) NOT NULL," 
				+ "EMAIL VARCHAR(30)," 
				+ "PASSWORD VARCHAR(30),"
				+ "TYPE VARCHAR(1) CONSTRAINT TYPE_CONSTRAINT CHECK (TYPE IN ('P', 'C')),"
				+ "CONSTRAINT USERS_PK PRIMARY KEY (ID))";
		// Creating the table and insert initial data 
		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			DatabaseMetaData databaseMetadata = conn.getMetaData();
		    ResultSet resultSet = databaseMetadata.getTables(null, null, "USERS", null);
		    if (resultSet.next()) {
		       System.out.println("TABLE USERS ALREADY EXISTS");
		    } else {			
				stmt.execute(createUsersSQL);
	
				pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
				pstmt.setString(1, "Mahdi J.Ansari");
				pstmt.setString(2, "m.j@ucalgary.ca");
				pstmt.setString(3, "p1");
				pstmt.setString(4, "P");
				pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
				pstmt.setString(1, "Majid Askari");
				pstmt.setString(2, "m.a@ucalgary.ca");
				pstmt.setString(3, "p2");
				pstmt.setString(4, "P");
				pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
				pstmt.setString(1, "Mahta Ghorbani");
				pstmt.setString(2, "m.g@ucalgary.ca");
				pstmt.setString(3, "c1");
				pstmt.setString(4, "C");
				pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE) VALUES(?,?,?,?)");
				pstmt.setString(1, "Mitra Mir");
				pstmt.setString(2, "m.m@ucalgary.ca");
				pstmt.setString(3, "c2");
				pstmt.setString(4, "C");
				pstmt.executeUpdate();
				
				conn.commit();
		    }

		} catch (SQLException ex) {
				System.out.println("in connection" + ex);
		}
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
	
	public void initResumesTable() {
		Connection conn = null;
		PreparedStatement pstmt;
		Statement stmt;
		String createResumesSQL = "CREATE TABLE RESUMES ("
				+ "ID INTEGER NOT NULL,"
				+ "KEYWORDS VARCHAR(100) NOT NULL," 
				+ "BODY VARCHAR(1000)," 
				+ "UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
				+ "CONSTRAINT RESUMES_PK PRIMARY KEY (ID),"
				+ "CONSTRAINT RESUMES_FK FOREIGN KEY (ID) REFERENCES USERS(ID))";
		// Creating the table and insert initial data 
		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			DatabaseMetaData databaseMetadata = conn.getMetaData();
		    ResultSet resultSet = databaseMetadata.getTables(null, null, "RESUMES", null);
		    if (resultSet.next()) {
		       System.out.println("TABLE RESUMES ALREADY EXISTS");
		    } else {			
				stmt.execute(createResumesSQL);
	
				pstmt = conn.prepareStatement("INSERT INTO RESUMES (ID,KEYWORDS,BODY) VALUES(?,?,?)");
				pstmt.setString(1, "1");
				pstmt.setString(2, "C++, JAVA, C#, SQLSERVER");
				pstmt.setString(3, "I did 200 projects ...");
				pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("INSERT INTO RESUMES (ID,KEYWORDS,BODY) VALUES(?,?,?)");
				pstmt.setString(1, "2");
				pstmt.setString(2, "PHP, MYSQL, PHYTHON");
				pstmt.setString(3, "I am better than others ...");
				pstmt.executeUpdate();
				
				conn.commit();
		    }

		} catch (SQLException ex) {
				System.out.println("in connection" + ex);
		}
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
	
	public void readUsers() {
		Connection conn = null;
		Statement stmt;
		ResultSet rs = null;
		String readUsersSQL = "SELECT * FROM USERS";		

		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(true);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}

		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (ex.getErrorCode() != 50000) {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}
	
	public void readResumes() {
		Connection conn = null;
		Statement stmt;
		ResultSet rs = null;
		String readResumesSQL = "SELECT * FROM RESUMES";		

		try {
			Driver derbyEmbeddedDriver = new EmbeddedDriver();
			DriverManager.registerDriver(derbyEmbeddedDriver);
			conn = DriverManager.getConnection("jdbc:derby:DB;create=true", "SENG696", null);
			conn.setAutoCommit(true);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(readResumesSQL);
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4));
			}

		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}

		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException ex) {
			if (ex.getErrorCode() != 50000) {
				System.err.println("Derby did not shut down normally");
				System.err.println(ex.getMessage());
			}
		}
	}
}
