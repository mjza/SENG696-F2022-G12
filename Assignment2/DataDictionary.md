
# Data Dictionary

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




