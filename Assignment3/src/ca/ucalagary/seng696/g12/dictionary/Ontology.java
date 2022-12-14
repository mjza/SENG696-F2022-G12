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

/**
 * The Class Ontology.
 */
public class Ontology {
    
    /** The Constant NEGOTIATION. */
    public final static String NEGOTIATION = "BIDING";
    
    /** The Constant PROPOSAL. */
    public final static String PROPOSAL = "PROPOSAL";
    
    /** The Constant REPORTING. */
    public final static String REPORTING = "REPORT";
    
    /** The Constant CLIENT_TO_PROVIDER. */
    public final static String CLIENT_TO_PROVIDER = "C2P";
    
    /** The Constant PROVIDER_TO_CLIENT. */
    public final static String PROVIDER_TO_CLIENT = "P2C";
    
    /** The Constant ACLMESSAGE_OFFER. */
    public final static int ACLMESSAGE_OFFER = 1000;
    
    /** The Constant ACLMESSAGE_ACCEPT. */
    public final static int ACLMESSAGE_ACCEPT = 2000;
    
    /** The Constant ACLMESSAGE_REFUSE. */
    public final static int ACLMESSAGE_REFUSE = 3000;
    
    /** The Constant ACLMESSAGE_CHAT. */
    public final static int ACLMESSAGE_CHAT = 4000;
    
    /** The Constant ACLMESSAGE_RATE. */
    public final static int ACLMESSAGE_RATE = 5000;
    
    /** The Constant ACLMESSAGE_PAYMENT. */
    public final static int ACLMESSAGE_PAYMENT = 6000;
    
    /** The Constant ACLMESSAGE_PROGRESS. */
    public final static int ACLMESSAGE_PROGRESS = 7000;
    
    /** The Constant ACLMESSAGE_DONE. */
    public final static int ACLMESSAGE_DONE = 8000;
}
