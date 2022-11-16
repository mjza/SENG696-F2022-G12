Create two run configurations with these arguments with 2 different names. 

> -agents buyer:examples.bookTrading.BookBuyerAgent("BBB","CCC")

> -container -host 10.13.120.105 -agents "seller:examples.bookTrading.BookSellerAgent"


How to run in cmd:

> javac -classpath ".\*" .\*.java

> java -cp ".\lib\JADE-bin-4.5.0\jade\lib\jade.jar;.\bin" jade.Boot -agents buyer:examples.bookTrading.BookBuyerAgent(BBB)

> java -cp ".\*" jade.Boot -agents buyer:examples.bookTrading.BookBuyerAgent(BBB)