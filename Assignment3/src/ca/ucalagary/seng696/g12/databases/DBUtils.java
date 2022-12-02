/**
 * MIT License
 * 
 * Copyright (c) 2022 Mahdi Jaberzadeh Ansari
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. 
 */
package ca.ucalagary.seng696.g12.databases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.jdbc.*;

import ca.ucalagary.seng696.g12.dictionary.Client;
import ca.ucalagary.seng696.g12.dictionary.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class DBUtils.
 */
public class DBUtils {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	// Run this main function just for test
	public static void main(String[] args) {
		DBUtils.initDB();
		DBUtils.printUsers();
		DBUtils.printResumes();
		DBUtils.getProvider("m.j@ucalgary.ca");
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
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

	/**
	 * Close connection.
	 */
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

	/**
	 * Inits the DB.
	 */
	public static void initDB() {
		DBUtils.initUsersTable();
		DBUtils.initResumesTable();
	}

	/**
	 * Inits the users table.
	 */
	public static void initUsersTable() {
		String createUsersSQL = "CREATE TABLE USERS ("
				+ "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ "NAME VARCHAR(30) NOT NULL," + "EMAIL VARCHAR(30)," + "PASSWORD VARCHAR(30),"
				+ "TYPE VARCHAR(1) NOT NULL DEFAULT 'C' CONSTRAINT TYPE_CONSTRAINT CHECK (TYPE IN ('P', 'C')),"
				+ "PREMIUM VARCHAR(1) NOT NULL DEFAULT 'N' CONSTRAINT PREMIUM_CONSTRAINT CHECK (PREMIUM IN ('N', 'Y')),"
				+ "RATING INTEGER NOT NULL DEFAULT 0 CONSTRAINT RATING_CONSTRAINT CHECK (RATING >=0 AND RATING <= 5),"
				+ "CONSTRAINT USERS_PK PRIMARY KEY (ID)," + "CONSTRAINT USERS_UQ UNIQUE (EMAIL))";
		// Creating the table and insert initial data
		try {
			Connection conn = DBUtils.getConnection();
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
		DBUtils.closeConnection();
		// initialize
		DBUtils.addProvider("Mahdi J.Ansari", "m.j@ucalgary.ca", "p1", true, 3);
		DBUtils.addProvider("Majid Askari", "m.a@ucalgary.ca", "p2", false, 5);
		DBUtils.addClient("Mahta Ghorbani", "m.g@ucalgary.ca", "c1", 2);
		DBUtils.addClient("Mitra Mir", "m.m@ucalgary.ca", "c2", 4);
	}

	/**
	 * Adds the provider.
	 *
	 * @param name      the name
	 * @param email     the email
	 * @param password  the password
	 * @param isPremium the is premium
	 * @param rating    the rating
	 */
	public static void addProvider(String name, String email, String password, Boolean isPremium, int rating) {
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE,PREMIUM,RATING) VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setString(4, "P");
			pstmt.setString(5, isPremium ? "Y" : "N");
			pstmt.setInt(6, rating);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
	}

	/**
	 * Adds the client.
	 *
	 * @param name     the name
	 * @param email    the email
	 * @param password the password
	 * @param rating   the rating
	 */
	public static void addClient(String name, String email, String password, int rating) {
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO USERS (NAME,EMAIL,PASSWORD,TYPE,RATING) VALUES(?,?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setString(4, "C");
			pstmt.setInt(5, rating);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
	}

	/**
	 * Inits the resumes table.
	 */
	public static void initResumesTable() {
		String createResumesSQL = "CREATE TABLE RESUMES (" + "ID INTEGER NOT NULL," + "KEYWORDS VARCHAR(100) NOT NULL,"
				+ "BODY VARCHAR(1000)," + "UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
				+ "CONSTRAINT RESUMES_PK PRIMARY KEY (ID),"
				+ "CONSTRAINT RESUMES_FK FOREIGN KEY (ID) REFERENCES USERS(ID))";
		// Creating the table and insert initial data
		try {
			Connection conn = DBUtils.getConnection();
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
		DBUtils.closeConnection();

		DBUtils.addResume("1", "C++, JAVA, C#, SQLSERVER", "I did 200 projects ...");
		DBUtils.addResume("2", "PHP, MYSQL, PHYTHON", "I am better than others ...");
	}

	/**
	 * Adds the resume.
	 *
	 * @param userId   the user id
	 * @param keywords the keywords
	 * @param body     the body
	 */
	public static void addResume(String userId, String keywords, String body) {
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO RESUMES (ID,KEYWORDS,BODY) VALUES(?,?,?)");
			pstmt.setString(1, userId);
			pstmt.setString(2, keywords);
			pstmt.setString(3, body);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
	}

	/**
	 * Prints the users.
	 */
	public static void printUsers() {
		ResultSet rs = null;
		String readUsersSQL = "SELECT ID, NAME, EMAIL, PASSWORD, TYPE, PREMIUM, RATING FROM USERS";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s, %s, %s, %d\n", rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
	}

	/**
	 * Prints the resumes.
	 */
	public static void printResumes() {
		ResultSet rs = DBUtils.getResumes();
		try {
			while (rs.next()) {
				System.out.printf("%d, %s, %s, %s\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the provider.
	 *
	 * @param username the username
	 * @return the provider
	 */
	public static Provider getProvider(String username) {
		Provider provider = null;
		String readUsersSQL = "SELECT U.ID, U.NAME, U.TYPE, U.PREMIUM, U.RATING, R.KEYWORDS, R.BODY FROM USERS U \n"
				+ "LEFT JOIN RESUMES R ON \n" + " U.ID = R.ID \n" + "WHERE U.TYPE = 'P' AND U.EMAIL = '" + username
				+ "'";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				Boolean isPremium = rs.getBoolean(4);
				int rating = rs.getInt(5);
				String keywords = rs.getString(5);
				String resume = rs.getString(6);
				provider = new Provider(id, name, username, null, type, rating, keywords, resume, isPremium);

			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
		return provider;
	}

	/**
	 * Gets the providers.
	 *
	 * @param keyword the keyword
	 * @return the providers
	 */
	public static List<Provider> getProviders(String keyword) {
		List<Provider> providers = new ArrayList<>();
		String readUsersSQL = "SELECT U.ID, U.NAME, U.EMAIL, U.TYPE, U.PREMIUM, U.RATING, R.KEYWORDS, R.BODY FROM USERS AS U \n"
				+ "LEFT JOIN RESUMES AS R ON \n" + " U.ID = R.ID \n" + "WHERE U.TYPE = 'P'\n"
				+ (keyword != null ? " AND R.KEYWORDS LIKE '%" + keyword +"%'\n" : "")
				+ "ORDER BY U.PREMIUM DESC";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String username = rs.getString(3);
				String type = rs.getString(4);
				Boolean isPremium = rs.getBoolean(5);
				int rating = rs.getInt(6);
				String keywords = rs.getString(7);
				String resume = rs.getString(8);
				Provider provider = new Provider(id, name, username, null, type, rating, keywords, resume, isPremium);
				providers.add(provider);
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
		return providers;
	}
	
	public static Client getClient(String username) {
		Client client = null;
		String readUsersSQL = "SELECT U.ID, U.NAME, U.TYPE, U.RATING FROM USERS U \n"
				+ "WHERE U.TYPE = 'C' AND U.EMAIL = '" + username + "'";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				int rating = rs.getInt(4);
				client = new Client(id, name, username, null, type, rating);
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
		return client;
	}

	/**
	 * Gets the resumes.
	 *
	 * @return the resumes
	 */
	public static ResultSet getResumes() {
		ResultSet rs = null;
		String readResumesSQL = "SELECT ID, KEYWORDS, BODY, UPDATED_AT FROM RESUMES";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(readResumesSQL);
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
		return rs;
	}

	/**
	 * Gets the user type.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the user type
	 */
	public static String getUserType(String username, String password) {
		String type = null;
		String readUsersSQL = "SELECT TYPE FROM USERS WHERE EMAIL = '" + username + "' AND PASSWORD = '" + password
				+ "'";
		try {
			Connection conn = DBUtils.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersSQL);
			if (rs.getFetchSize() == 1) {
				while (rs.next()) {
					type = rs.getString(1);
				}
			}
		} catch (SQLException ex) {
			System.out.println("in connection" + ex);
		}
		DBUtils.closeConnection();
		return type;
	}
}
