# Online Store - Discount Calculator

# Tools used

Java 1.8,
Maven 3.6.1

# Build 
 
 To build the app, execute the below command on project root directory

	mvn clean package

#Execute
 To run the app, execute the below command

	java -jar ./target/discount-calculator-1.0.0.jar

#Test and generate coverage report

 Execute the below command to test and generate coverage report

	mvn org.jacoco:jacoco-maven-plugin:prepare-agent test

Test report can be found in "/target/site/jacoco-ut"


#Endpoint 
 
 The discount calculator URI is "http://localhost:8080/applydiscount"

#Sample Request

{
    "items": [
        {
            "category": "Electronics",
            "price": 100,
            "id": 1
        }
    ],
    "payee": {
        "userName": "sam",
        "type": "Normal",
        "registeredDate": "2017-01-10"
    },
    "totalAmount": 100,
    "discountAmount": 0,
    "finalAmount": 0
}

#Sample Response

{
    "items": [
        {
            "category": "Electronics",
            "price": 100,
            "id": 1
        }
    ],
    "payee": {
        "userName": "sam",
        "type": "Normal",
        "registeredDate": "2017-01-10T00:00:00.000+0000"
    },
    "totalAmount": 100,
    "discountAmount": 10,
    "finalAmount": 90
}





