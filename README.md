# ReadingIsGood

It consists of five applications:
* **Gateway(Zuul)**: Zuul Server is a gateway application that handles all the requests and does the dynamic routing of microservice application. It authenticates the requests.
* **Discovery(Eureka)**: Eureka Server is an application that holds the information about all the service applications.

![eureka](https://user-images.githubusercontent.com/5354910/104977350-62e3b980-5a10-11eb-86e3-50b7b1fcf26e.PNG)


* **Auth Service**: It contains two endpoints:
  * **[POST]** /auth/register is used to register the customer and returns user id
  * **[POST]** /auth/login is used to login the customer and returns a JWT token.
  
   
 **Note:** You can use the following users to login.
| Username  | Password |
| ------------- | ------------- |
| test1  | 123456  |
| test2  | 123456  |
| test3  | 123456  |
  
  ![authlogin](https://user-images.githubusercontent.com/5354910/104977259-29ab4980-5a10-11eb-9f2d-4a1c54fd09fd.PNG)

  ![badcredentials](https://user-images.githubusercontent.com/5354910/104977303-447dbe00-5a10-11eb-9844-67502f248621.PNG)

* **Order Service**: It contains three endpoints:
  * **[GET]  /order** returns the customer's order details.
  * **[POST] /order** creates an order for the customer.
  * **[GET] /order/{transactionId}** returns the order detail. 
 **Note:** Transaction id is generated for each order and used to track of the transaction between Order and Stock Service.
 **Note:** Customer data is obtained from JWT token.
* **Stock Service**: It contains two endpoints:
  * **[GET] /stock** returns all stock data.
  * **[GET] /stock/{bookId}** returns the stock data of the book for given bookId.
  
 **Note:** Except the auth endpoints, all the endpoints is secure. You have to have a valid JWT token.
 **Note:** All the microservices uses H2 mem database.


 ## Creation Order
 **Note:** The Saga Pattern (choreography) is used to implement a transaction that spans multiple services.
 When OrderService gets an order creation request, it first checks the quantity of the book by requesting StockService. If there's not any problem with the quantity of the book in stock, it produces an OrderEvent with status **PENDING**. As soon as the stock service consumes this event and updates its stock. After that, it produces StockEvent with the status **RESERVED**. When the Order Service consumes this event, it updates the order's status as **COMPLETED**. 
 
In case of a failure scenario, the Stock Service produces and StockEvent with status **REVERSE_FAILED**, and the OrderService updates the order's status as **CANCELED**.

**Note:** Swagger documentations are available under the Zuul address.

![readingisgood-authapi](https://user-images.githubusercontent.com/5354910/104977158-f10b7000-5a0f-11eb-9c0b-b88f3bd1526b.PNG)
![readingisgood-orderapi](https://user-images.githubusercontent.com/5354910/104977189-fff22280-5a0f-11eb-93b7-b650456eb4ae.PNG)
![readingisgood-stockapi](https://user-images.githubusercontent.com/5354910/104977209-0a142100-5a10-11eb-84d9-6c53c5461f6f.PNG)


**Note:** You can find the Postman export in Postman folder.

**Note:** I couldn't upload docker images to DockerHub as it takes a while.
