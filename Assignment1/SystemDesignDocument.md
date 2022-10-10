
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

The GAIA approach encourages developers to see creating software systems as an organizational design process with software agents serving as its building blocks. Therefore, in our analysis phase, first we focus on identifying roles and their properties as shown in the following figure.

<p align="center">
<img src="./images/image2.jpg" alt="System High-Level Design" width="400"/>
<br/><span>Role detection process</span>
</p>

### 3-2. System Architecture

### 3-3. Roles Identification

Here is a table that demonstrates the detected roles. 

| :point_right: |Role|Why?|By means of?|What? (Responsibility)| |How?|
|-|-|-|-|-|-|-|
|Row#|Role Name|Description|Permissions|Liveness Property|Safety Property|Protocols|
|1|Registration|Handles the process of sign up for Providers and Clients|Read and Write user data|Register = (Request.register, Client)|Create a profile in the system|RegisterUser|
|2|Authentication|handling the process of authentication to find if the user is logged in or not, also it can detect whether the user is provider, client or guest|read user data, authenticate user|ReqeustAccess = (Request. Access, Client)|Grant system access|AuthenticateUser|
|3|Project Change Handler|handling the process of changing in projects|read project change data, write project change data|ReqeustChange = (Request. Change, project)|Deliver the changed project|ChangeProject|
|4|Provider Search|handling the process of searching provider by different Criteria|read provider data|RequestQuotes = (Request. Quotes, ProviderList)|Deliver a list of providers|SearchProviders|
|5|Project Creation|handling the process of creating the project based on the client request|write project data|RequestProject = (Request. Project, Project)|Generating a project|CreateProjects|
|6|Plan Checker|1) handling the process of begin registered in one of plans. 2) It proposes different plan options|1) write user's plan 2) read: user's plan 3) modify: user 's plan|RequestPlan = (Request.Plan, Plan)|Prcoess the requested plan|CheckPlans|
|7|Bid Handler|handling the process of creating, accepting, or rejecting a bid|write bid data, read bid data|RequestBid = (Request.Bid, Bid)|Process with the bid|HandleBids|
|8|Message Handler|handling the process of sending messages between users based on different events|write message data, read message data|RequestMessage = (Request. Message, MessageList)|Deliver a list of messages|DeliverMessages|
|9|Contract Handler|handling the process of creating contracts|write contract data, read contract data|RequestContract = (Request. Contract, Contract)|creating a contract|CreateProjects|
|10|Payment Handler|handling the process of Payments|write payment data, read payment data|RequestPayment = (Request. Payment, Transaction)|Process with the payment|TransferMoney|
|11|Project Tracker|handling the process of tracking project progress, deadline and estimations|read project tracking data, write project tracking data|RequestTracking = (Reqeust. Tracking, Progress)|Deliver the progress of projects|TrackProjects|
|12|Feedback Handler|handling the process of handling comments and ratings of projects estimations|read feedback data, write feedback data|RequestFeedback = (Request. Feedback, Feedback)|Generate the feedback|DeliverFeedbacks|
|13|GUI|handling interactions between users and multiple systems| |ReqeustInteraction = (Request. Interaction,|Handle user interaction in the System| |


### 3-4. Agents Description

### 3-5. Agents Internal Architecture

### 3-6. Technology Overview