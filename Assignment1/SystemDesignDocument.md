
# I.3. System Design Document

#### Table Of Contents :point_down:

[3. System Design Document](#3-system-design-document)
   * [3-1. Goal Hierarchy](#3-1-goal-hierarchy)
   * [3-2. System Architecture](#3-2-system-architecture)
   * [3-3. Roles Identification](#3-3-roles-identification)
   * [3-4. Agents Description](#3-4-agents-description)
   * [3-5. Agents Internal Architecture](#3-5-agents-internal-architecture)
   * [3-6. Technology Overview](#3-6-technology-overview)

<hr/>

## 3. System Design Document

In the **system design document**, we are going to speak about the six pieces of information by following an agent-based development methodology (i.e., GAIA), and create the analysis and design documents specified by the GAIA methodology.This section includes: [Goal Hierarchy](#3-1-goal-hierarchy), [Agent System Architecture](#3-2-system-architecture), [Role Identification](#3-3-roles-identification), [Agent Description](#3-4-agents-description),  [Agent Internal Architecture](#3-5-agents-internal-architecture), and finally [Technology Overview](#3-6-technology-overview).

### 3-1. Goal Hierarchy

Using GAIA, we think of each agent as having the resources of a computational
process. It is presumable that the objective is to create a system that maximizes a particular global quality metric. From the perspective of the system's constituent parts, nevertheless, this structure might not be ideal.

The GAIA approach encourages developers to see creating software systems as an organizational design process with software agents serving as its building blocks. Therefore, in our analysis phase, we planned to extract 5 models from the requirements.

<p align="center">
<img src="./images/image3.jpg" alt="Goal Hierarchy" width="600"/>
<br/><span>Goal Hierarchy</span>
</p>

### 3-2. System Architecture

Here is a high-level design of our system. 

<p align="center">
<img src="./images/image4.jpg" alt="System Architecture" width="600"/>
<br/><span>System Architecture</span>
</p>

If we want to go in more details, we would like to demonstrate our database structure first:

<p align="center">
<img src="./images/image5.jpg" alt="DB Architecture" width="600"/>
<br/><span>Database Architecture</span>
</p>

The user starts the journey by the login page. If he or she wants, they can just login as a guest and access the system with limited search functionality. 
Otherwise, if they don't have an account, they can move to register page and register as a **Provider** or **Client** by providing required information. 

After a successful login, user will be redirected to his or her dashboard. 

In the dashboard 

1. There is a list of current projects for monitoring, reporting, payment, and or requesting/confirming a change.

2. There is a list of past projects for commenting or rating the other party.

3. There is a pending list that shows list of projects that needs some sort of actions. For example, accepting or rejecting a bid, or confirming a contract. After any action, the list of projects will be updated.

Also, there is an option for **Clients** only to bid after selecting a **Provider** from the search list.

Finally, user can logout of the system for safety.  

### 3-3. Roles Identification

One of the main steps in GAIA methodology is to identifying roles based on the following chart. 

<p align="center">
<img src="./images/image2.jpg" alt="Role detection process" width="400"/>
<br/><span>Role detection process</span>
</p>

Therefore, here is a table that demonstrates the detected roles. 

| :point_right: |Role|By means of?|What?|What?|How?|
|-|-|-|-|-|-|
|Row#|Role Name|Permissions|Liveness Property|Safety Property|Protocols|
|1|Sign Up|Read and Write users data|Handles the process of sign up for Providers and Clients|Checks validity of user data.|Registration|
|2|Sign In|Read users data, Authenticate user, Create Session|Handles the process of authentication. If user exists then creates a session. Also, create guest session for Guests.|Checks for active users, and apply SQL injection guards|Authenticator|
|3|Search Engine|Read providers data|Apply a query on Keywords column of providers table|Deliver a list of providers based on the data that user allowed to access.|SearchEngine|
|4|Bid Handler|Read and write on bids data|Handles the process of creating, accepting, or rejecting a bid|Checks if Clients have any waiting bid or not. Only one bid per Provider is allowed. |Bid|
|5|Contract Creation|Read and Write contracts data|Handles the process of creating contracts and sends the contract to both sides after Provider accepts the bid.|Checks if there is no contracts waiting for acceptance for these 2 parties. |CreateContract|
|6|Project Creation|Read and Write projects data, Read contracts data|Handles the process of creating the project based on the Client request after accepting contracts by both side.|Checks both Provider and Client have been accepted the contract and there is no project in database. |CreateProject|
|7|Payment Handler|Reads and write payment data|Handles the process of Payments|Checks if payments has not yet been done, Checks if payment is equal to what we have in Contract.|TransferMoney|
|8|Project Tracker|Reads and write projects progress data|Handles the process of tracking project progress, deadline and estimations|Checks if project is still active.|TrackProject|
|9|Project Change Handler|Read and write projects data|Handles the process of changing a project, upon the Client request. Delivers the changed requirement/contract to Provider.|Checks if there is no change request in database. |ChangeProject|
|10|Message Handler|Read and write messages data|Handles the process of sending messages between Provider and Client in a specific chatroom|Checks if user belongs to a chatroom|Message|
|11|Feedback Handler|Read and write feedback data|Handles the comments and ratings of projects|Checks if user has worked with feedback receiver via a contract in the past. Checks if user has not yet deliver a feedback related to an experience.|Feedback|

### 3-4. Agents Description

We detected and designed 8 agents for this system as following:

1. **Access Agent**: This agent is responsible for registration, login and logout of users. If user provides correct credentials it generates a session in the database. 
2. **Search Agent**: This agents is responsible for applying suitable query on the database, list providers based on the ordering rules which mentioned in the requirements.
3. **Presenter Agent**: This agent is responsible to show search results and other data related to each user in the GUI. We can assume this agent as the UI manager. 
4. **Booking Agent**: This agent manage bids and contracts. It means this agent handles transactions tills forming a project.
5. **Transaction Agent**: This agent is responsible to convert a project state from *pending* to *active* upon receiving money from the **Client**. Also, as soon as a change to the project confirmed, it changes the state to *pending* again. It also calculates and conveys the portion of the **Provider** from the earnings. A project's state cannot change after it is been flagged as *completed*.  
6. **Chatroom Agent**: This agent conveys messages between two parties of a project and shows a history of old messages.
7. **Monitoring Agent**: This agent is responsible to records the amount of progress, and estimate the delivery time based on the current pace.  
8. **Feedback Agent**: Finally, this agent is responsible to moderates rating and comments after a project is in *completed* state.

Here is a figure that illustrates our agent model. 

<p align="center">
<img src="./images/image6.jpg" alt="Agent Model" width="600"/>
<br/><span>Agent Model</span>
</p>

### 3-5. Agents Internal Architecture

Here is a figure that illustrates the internal communication between the agents:

<p align="center">
<img src="./images/image7.jpg" alt="Agent internal architecture" width="600"/>
<br/><span>Agents Internal Architecture</span>
</p>

### 3-6. Technology Overview

1. Regarding the technology we are going to use JAVA and JADE framework for implementation. 
2. For persisting the information, we will use one of the JAVA internal databases, however, it is not yet clear which one at the moment. We need to clarify it later during the implementation with respect to compatibility with JADE. What we can clearly stated now is, we are going to use one of the following databases: 
   - [H2](http://www.h2database.com/html/main.html)
   - [HyperSQL](http://hsqldb.org/) 
   - [Apache Derby](http://db.apache.org/derby/)
   - [Berkley DB](https://www.oracle.com/database/technologies/related/berkeleydb.html)
   - [Java DB](https://www.oracle.com/technetwork/java/javadb/overview/index.html)
   - [ObjectDB](http://www.objectdb.com/)
3. Regarding IDE we are going to use Eclipse version 2022-06.
4. For archiving the code, we use [this GitHub](https://github.com/mjza/SENG696-F2022-G12/tree/main/Assignment3) repository.   