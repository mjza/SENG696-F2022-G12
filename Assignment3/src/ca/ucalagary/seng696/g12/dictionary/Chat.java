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
package ca.ucalagary.seng696.g12.dictionary;

import java.util.Date;

/**
 * The Class Chat.
 */
public class Chat {

	/** The global nextId. */
	private static int nextId = 3019;

	/** The id. */
	private int id;

	/** The text. */
	private String text;

	/** The sender user. */
	private User senderUser;

	/** The receiver user. */
	private User receiverUser;

	/** The from me. */
	private boolean fromMe = false;

	/** The timestamp. */
	private Date timestamp = new Date();

	/**
	 * Instantiates a new chat.
	 *
	 * @param text         the text
	 * @param senderUser   the sender user
	 * @param receiverUser the receiver user
	 * @param fromMe       the from me
	 */
	public Chat(String text, User senderUser, User receiverUser, boolean fromMe) {
		super();
		this.id = ++Chat.nextId;
		this.text = text;
		this.senderUser = senderUser;
		this.receiverUser = receiverUser;
		this.fromMe = fromMe;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the sender user.
	 *
	 * @return the sender user
	 */
	public User getSenderUser() {
		return senderUser;
	}

	/**
	 * Gets the receiver user.
	 *
	 * @return the receiver user
	 */
	public User getReceiverUser() {
		return receiverUser;
	}

	/**
	 * Checks if is from me.
	 *
	 * @return true, if is from me
	 */
	public boolean isFromMe() {
		return fromMe;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp.getTime();
	}
	
	/**
	 * Gets the timestamp as Date.
	 *
	 * @return the timestamp as Date
	 */
	public Date getDateTime() {
		return this.timestamp;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public static String[] getColumns() {
		String[] columnNames = { "ID", "Sender", "At", "Text" };
		return columnNames;
	}

	/**
	 * To array.
	 *
	 * @param isGuest the is guest
	 * @return the string[]
	 */
	public String[] toArray() {
		String[] data = { String.valueOf(this.getId()), (this.isFromMe() ? "You" : "They"), this.getDateTime().toString(), this.getText()
				 };
		return data;
	}
}
