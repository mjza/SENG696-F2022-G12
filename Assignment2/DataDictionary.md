
# Data Dictionary

## E-R Diagram:
![ER_Diagram](https://user-images.githubusercontent.com/29688107/201511677-cf42171f-78b0-47c6-ab37-dc8fae1ae5bb.png)

### User:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| User_ID | Unique ID of User |   Integer   |
| Name | User Name |   String   |
| Email |    User Email Address   |   String   |
| Password |    User Password   |   String   |
| Type | User Type |    String   

### Session:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| Session_ID |  Unique ID of Session |   Integer   |
| User_ID |  ID of User |   Integer   |
| Start |    Start Date   |   Date   |
| Long | Session duration |   Integer   

### Resume:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
| Resume_ID | Unique ID of Resume |   Integer   |
| File_ID | File ID |   Integer   |
| File_Path | Path of File |   String   |
| Owner_ID |    Owner's ID of Resume (User_ID)   |   Integer   |
| Keywords |    User Skill Keywords   |   Keyword   |
| Price | Hourly Wage |    Double  

### Bid:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Bid_ID | Unique ID of Bid |   Integer   |
| Client | ID of Client (User_ID) |   Integer   |
| Provider_ID |    ID of Provider (User_ID)   |   Integer   |
| Price |    Price   |   Double   |
| Confirmed_Provider |    Is Provider confirmed or Not?   |   Boolean   |
| Confirmed_Client | Is Client confirmed or Not?  |    Boolean 

### Contract:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Contract_ID | Unique ID of Contract |   Integer   |
| Bid_ID | ID of Bid |   Integer   |
| Start |    Start Date   |   Date   |
| End |    End Date   |   Date   |
| Confirmed_Provider |    Is Provider confirmed or Not?   |   Boolean   |
| Confirmed_Client | Is Client confirmed or Not?  |    Boolean 

### Project:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Project_ID | Unique ID of Project |   Integer   |
| Contract_ID | ID of Contract |   Integer   |
| Start |    Start Date   |   Date   |
| End |    End Date   |   Date   |
| Progress |    Percentage of Project Progress   |   Integer   

### ChatRoom:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  ChatRoom_ID | Unique ID of ChatRoom |   Integer   |
| Project_ID | ID of Project |   Integer   |
| Closed |    Is it closed or not?   |   Boolean 

### Message:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Message_ID | Unique ID of Message |   Integer   |
| ChatRoom_ID | ID of ChatRoom |   Integer   |
| Text | Text of Message |   String   |
| Date_Time |    Date   |   Date   |
| Sender |    Sender's ID of Message (User_ID)   |   Integer

### Payment:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Payment_ID | Unique ID of Payment |   Integer   |
| Project_ID | ID of Project |   Integer   |
| Value | Amount of Paymenmt |   Double  |
| Date |    Date of Payment   |   Date   

### Feedback:
| Field   |      Description      |  Type |
|----------|:-------------:|:------:|
|  Payment_ID | Unique ID of Payment |   Integer   |
| Project_ID | ID of Project |   Integer   |
| Value | Score of Feedback |   Double  |
| Date |    Date of Payment   |   Date  

