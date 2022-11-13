
# 6. Inter-Agents Messages

In this document you can find the list of IAMs. 

<hr/>

## Login
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<User>`</p><p>`<Email>String</Email>`</p><p>`<Password>String</Password>`</p><p>`</User>`</p>|Request Login.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Session>`</p><p>`<Session_ID>Integer</Session_ID>`</p><p>`<User_ID>Integer</User_ID>`</p><p>`<Date>Date</Date>`</p><p>`</Session>`</p>|Get login session information.|

<hr/>
<div style="page-break-before: always;"/>

## Get Bids (Client) 
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<Bid>`</p><p>`<Client_ID>Integer</Client_ID>`</p><p>`</Bid>`</p>|Get client’s bids.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Bids>`</p><p>`<Bid>`</p><p>`<Client_ID>Integer</Client_ID>`</p><p>`<Provider_ID>Integer</Provider_ID>`</p><p>`<Price>Double</Price>`</p><p>`<Confirmed_Client>Boolean</Confirmed_Client>`</p><p>`<Confirmed_Provider>Boolean</Confirmed_Provider>`</p><p>`</Bid>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Bids>`</p>|List of bids for this client.|

<hr/>
<div style="page-break-before: always;"/>

## Get Providers
### Input
|Parameter|Description|
| :-: | :-: |
|<p>`<User>`</p><p>`<Type>"Provider"</Type>`</p><p>`<Resume>`</p><p>`<keywords>String</keywords>`</p><p>`</Resume>`</p><p>`<Price>Double</Price>`</p><p>`</User>`</p>|Information of providers user wants.|

### Output
|Parameter|Description|
| :-: | :-: |
|<p>`<Users>`</p><p>`<User>`</p><p>`<Name>String</Name>`</p><p>`<Type>"Provider"</Type>`</p><p>`<Resume>`</p><p>`<File_Path>String</File_Path>`</p><p>`<keywords>String</keywords>`</p><p>`</Resume>`</p><p>`<Price>Double</Price>`</p><p>`</User>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Users>`</p>|List of providers based on user filters.|

## Place a Bid
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<Bid>`</p><p>`<Provider_ID>Integer</Provider_ID>`</p><p>`<Price>Double</Price>`</p><p>`</Bid>`</p>|Place a bid for a specific provider.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Bids>`</p><p>`<Bid>`</p><p>`<Client_ID>Integer</Client_ID>`</p><p>`<Provider_ID>Integer</Provider_ID>`</p><p>`<Price>Double</Price>`</p><p>`<Confirmed_Client>Boolean</Confirmed_Client>`</p><p>`<Confirmed_Provider>Boolean</Confirmed_Provider>`</p><p>`</Bid>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Bids>`</p>|Returns all the client’s current bids.|

<hr/>
<div style="page-break-before: always;"/>

## Accept/Reject Contract
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<Contract>`</p><p>`<Contract_ID>Integer</Contract_ID>`</p><p>`<Bid_ID>Integer</Bid_ID>`</p><p>`<Confirmed_Client>Boolean</Confirmed_Client>`</p><p>`</Contract>`</p>|Accept a contract. This can be called by both client and provider to their respective agents.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Contracts>`</p><p>`<Contract>`</p><p>`<Contract_ID>Integer</Contract_ID>`</p><p>`<Bid_ID>Integer</Bid_ID>`</p><p>`<Start>Date</Start>`</p><p>`<End>Date</End>`</p><p>`<Confirmed_Client>Boolean</Confirmed_Client>`</p><p>`<Confirmed_Provider>Boolean</Confirmed_Provider>`</p><p>`</Contract>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Contracts>`</p>|Get and updated list of contracts.|

<hr/>
<div style="page-break-before: always;"/>

## Get Projects
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<User>`</p><p>`<User_ID>Integer</User_ID>`</p><p>`</User>`</p>|Get the list of projects for this user.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Projects>`</p><p>`<Project>`</p><p>`<Project_ID>Integer</Project_ID>`</p><p>`<Contract_ID>Integer</Contract_ID>`</p><p>`<Start>Date</Start>`</p><p>`<End>Date</End>`</p><p>`<Progress>Double</Progress>`</p><p>`</Project>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Projects>`</p>|List of projects for this user.|

<hr/>
<div style="page-break-before: always;"/>

## Send Message
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<Message>`</p><p>`<Chat_ID>Integer</Chat_ID>`</p><p>`<Text>String</Text>`</p><p>`<Sender_ID>Integer</Sender_ID>`</p><p>`</Message>`</p>|Send a message to a chat.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Messages>`</p><p>`<Message>`</p><p>`<Chat_ID>Integer</Chat_ID>`</p><p>`<Text>String</Text>`</p><p>`<Date_Time>Date</Date_Time>`</p><p>`<Sender_ID>Integer</Sender_ID>`</p><p>`</Message>`</p><p>`.`</p><p>`n`</p><p>`.`</p><p>`</Messages>`</p>|List of updated messages for each chat.|

<hr/>
<div style="page-break-before: always;"/>

## Update Profile
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<User>`</p><p>`<Name>String</Name>`</p><p>`<Type>"Provider"</Type>`</p><p>`<Resume>`</p><p>`<File_Path>String</File_Path>`</p><p>`<keywords>String</keywords>`</p><p>`</Resume>`</p><p>`<Price>Double</Price>`</p><p>`<Email>String</Email>`</p><p>`<Phone_Number>String</Phone_Number>`</p><p>`<Password>String</Password>`</p><p>`</User>`</p>||

<div style="page-break-before: always;"/>

### Output
|Parameter|Description|
| :-: | :-: |
|<p>`<User>`</p><p>`<Name>String</Name>`</p><p>`<Type>"Provider"</Type>`</p><p>`<Resume>`</p><p>`<File_Path>String</File_Path>`</p><p>`<keywords>String</keywords>`</p><p>`</Resume>`</p><p>`<Price>Double</Price>`</p><p>`<Email>String</Email>`</p><p>`<Phone_Number>String</Phone_Number>`</p><p>`</User>`</p>||

<hr/>
<div style="page-break-before: always;"/>

## Accept/Reject Bid
### Input

|Parameter|Description|
| :-: | :-: |
|<p>`<Bid>`</p><p>`<Bid_ID>Integer</Bid_ID>`</p><p>`<Confirmed_Provider>Boolean</Confirmed_Provider>`</p><p>`</Bid>`</p>|Accept or reject a bid.|

### Output

|Parameter|Description|
| :-: | :-: |
|<p>`<Bid>`</p><p>`<Client_ID>Integer</Client_ID>`</p><p>`<Provider_ID>Integer</Provider_ID>`</p><p>`<Price>Double</Price>`</p><p>`<Confirmed_Client>Boolean</Confirmed_Client>`</p><p>`<Confirmed_Provider>Boolean</Confirmed_Provider>`</p><p>`</Bid>`</p>|Return the updated bid information.|