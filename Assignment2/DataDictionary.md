
# Data Dictionary

## TAS: E-R Diagram
![Untitled Diagram drawio](https://user-images.githubusercontent.com/29688107/201510114-c69b884d-4202-40d8-a8d4-b6733a472066.png)

### Session:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| Session_ID |  Unique ID for Session |   Integer   |
| User_ID | Unique ID for User |   Integer   |
| Start |    Start Date   |   Date   |
| Long | Session duration |   Integer   

### User:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| User_ID | Unique ID for User |   Integer   |
| Name | User name |   String   |
| Email |    User email address   |   String   |
| Password |    User password   |   String   |
| Type | User type |    UserTypeEnum   

### Resume:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| Resume_ID | Unique ID for Resume |   Integer   |
| File_ID | File ID |   Integer   |
| Owner |    Owner of Resume   |   User   |
| Keywords |    ?   |   Keyword   |
| Price | Price |    Double  

### Bid:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Bid_ID | Unique ID for Bid |   Integer   |
| Client | Name of Client |   User   |
| Provider |    Name of Provider   |   User   |
| Price |    Price   |   Double   |
| Confirmed_Provider |    ?   |   Integer   |
| Confirmed_Client | ? |    Integer 

### Contract:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Contract_ID | Unique ID for Contract |   Integer   |
| Bid_ID | ID for Bid |   Integer   |
| Start |    Start Date   |   Date   |
| End |    End Date   |   Date   |
| Confirmed_Provider |    ?   |   Integer   |
| Confirmed_Client | ? |    Integer 

### Project:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Project_ID | Unique ID for Project |   Integer   |
| Contract_ID | ID for Contract |   Integer   |
| Start |    Start Date   |   Date   |
| End |    End Date   |   Date   |
| Progress |    ?   |   Integer   

### ChatRoom:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  ChatRoom_ID | Unique ID for ChatRoom |   Integer   |
| Project_ID | ID for Project |   Integer   |
| Closed |    Is it closed or not?   |   Boolean 

### Message:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Message_ID | Unique ID for Message |   Integer   |
| ChatRoom_ID | ID for ChatRoom |   Integer   |
| Text | Text of Message |   String   |
| Date_Time |    Date   |   Date   |
| Sender |    Sender of Message   |   User

### Payment:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Payment_ID | Unique ID for Payment |   Integer   |
| Project_ID | ID for Project |   Integer   |
| Value | Amount of Paymenmt |   Double  |
| Date |    Date of Payment   |   Date   

### Feedback:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Payment_ID | Unique ID for Payment |   Integer   |
| Project_ID | ID for Project |   Integer   |
| Value | ? |   Double  |
| Date |    Date of Payment   |   Date  

