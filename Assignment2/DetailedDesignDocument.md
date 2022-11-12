
# Detailed Design Document

## Actors

Our B2B Matchmaking System contains four main actors which are listed below:
1. Admin
2. Provider
3. Guest
4. Customer

## 4-1. Use cases

In the following you can find the role of each actor in more detail and the related use case diagram:

### 1. Admin

The admin agent enables superusers to moderate and manage the system with unlimited access. A simple example is suspending a user or deactivating the system for a few hours to apply some updates. Also, we assume that the duty of the accountant user is done by the admin user. Performing a project can charge a provider's bank account by the admin. 

<p align="center">
<img src="./images/image1.jpg" alt="Admin use case diagram" width="600"/>
<br/><span>Admin use case diagram</span>
</p>

### 2. Provider

The provider agent provides services associated with registering providers in the system. Providers can submit some keywords and upload a resume file related to the services they can offer to consumers. The provider will receive bids for their services from a number of customers. The provider can either accept or reject the bid request here. In addition, providers can chat with customers and keep them up to date on project progress.

<p align="center">
<img src="./images/image2.jpg" alt="Provider use case diagram" width="600"/>
<br/><span>Provider use case diagram</span>
</p>

### 3. Guest
Guest users can only search for providers based on keywords. Therefore, the guest agent provides limited functionalities. For instance, searching the database of providers and gaining access to limited information about them, such as keywords, resumes, and compensation. Guests cannot place a bid or access the contact information of providers. 

<p align="center">
<img src="./images/image3.jpg" alt="Guest use case diagram" width="600"/>
<br/><span>Guest use case diagram</span>
</p>

### 4. Customer
A customer can search the providers database and then place a bid on a provider. A contract must be signed by both parties after the bid has been accepted by the provider. Customers can check the progress of their projects and chat with related providers. The customer service agent must provide services to fulfill customers' needs.

<p align="center">
<img src="./images/image4.jpg" alt="Customer use case diagram" width="600"/>
<br/><span>Customer use case diagram</span>
</p>