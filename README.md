mvn package && java -debug -jar target/target-myretail-extended-0.1.0.jar
http://localhost:8080/products/13860428

use myretail
db.createUser(
   {
     user: "sei",
     pwd: "password",
     roles: [ "readWrite", "dbAdmin" ]
   })


db.createCollection("pricing");

db.pricing.insert([{name:"sugar",value:"1.00",currency_code:"USD" id:"1"},
{name:"salt", value:"2.00", ":"USD" id:"2"},
{name:"oil", value:"3.00", currency_code:"USD" id:"3"}])

db.pricing.update({name : "The Big Lebowski (Blu-ray)"}, {name : "The Big Lebowski (Blu-ray)", value : "14.99", currency_code : "USD", id:"13860428"});

db.pricing.update({name : "oil"},{name : "oil", value : "3.00", currency_code : "USD", id : 003});

The application gets the product name from 
http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

The pricing information is taken from a no sql mongoDB database

These are the URL and Curl commands to test the application.

http://localhost:8080/products/13860428

curl -X PUT -H "Content-Type: application/json" -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray)","price":{"value":16.99,"currencyCode":"USD"}}' "http://localhost:8080/products/13860428"
