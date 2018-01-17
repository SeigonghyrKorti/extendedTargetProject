package product;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * For this exercise, authentication has been delibrately ommited.
 * @author sei
 */

@RestController
public class ProductController {
    
    private final String extUri = "http://redsky.target.com/v2/pdp/tcin/";
    private final String extUriExcludes = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
    @Autowired
    HttpServletResponse response;
    
    
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Extended Target Product Controller.";
    }
    
    /**
     * Gets product details given the product id.  
     * @param id
     * @return
     * @throws Exception 
     */
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable long id) throws Exception {
        String productInfo = getProductFromExternalSource(id).orElse(null);
        if(productInfo == null){
            throw new EntityNotFoundException(String.format("Product id %d not found**", id));
        }
        JSONObject infoObj = new JSONObject(productInfo);
        String productName = infoObj.getJSONObject("product").getJSONObject("item").getJSONObject("product_description").getString("title");
        String productId = infoObj.getJSONObject("product").getJSONObject("available_to_promise_network").getString("product_id");
        Price price = getProductPricing(Long.toString(id)).orElse(null);
        return new ResponseEntity<>(new Product(Long.valueOf(productId),productName, price), HttpStatus.valueOf(response.getStatus()));
    }
    
    /**
     * Updates price of all products with the given id.
     * @param id
     * @param product
     * @return
     * @throws Exception 
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> putProduct(@PathVariable long id, @RequestBody Product product) throws Exception {
        if (product == null || product.getPrice() == null){
            //response.setStatus(HttpStatus.BAD_REQUEST.value());
            //return new ResponseEntity<>("Product or price cannot be null.", HttpStatus.valueOf(response.getStatus()));
            throw new ValidationException("Product or Price", String.format("Product id %d not found", id));
        }
        Price price = product.getPrice();
        MongoCollection<Document> collection = DbConnection.getInstance().getPricingCollection();
        collection.updateMany(eq("id", Long.toString(id)), new Document("$set", new Document("value", Double.toString(price.getValue()))) );
        Price retPrice = getProductPricing(Long.toString(id)).orElse(null);
        if (retPrice == null) {
            throw new EntityNotFoundException(String.format("Product id %d not found", id));
        }
        return new ResponseEntity<>( retPrice, HttpStatus.valueOf(response.getStatus()));
    }
    
    /**
     * Gets product pricing from a MongoDb datasoure
     * @param id
     * @return
     * @throws Exception 
     */
    private Optional<Price> getProductPricing(String id) throws Exception{
        Validate.validateNotNull(id, "Product ID");
        Validate.validateNotEmpty(id, "Product ID");
        DbConnection mongoDb = DbConnection.getInstance();
        MongoCollection<Document> pricingCollection = mongoDb.getPricingCollection();
        
        Optional<Document> opt = Optional.ofNullable(pricingCollection.find(eq("id", id)).first());
        Optional<Price> p;
        p = opt.map(doc -> new Price(Double.parseDouble(doc.getString("value")), CurrencyCode.valueOf(doc.getString("currency_code"))));
        return p;
    }
    
    /**
     * Calls an external web service for product data
     * @param id
     * @return A JSON representing the product data
     * @throws Exception 
     */
    private Optional<String> getProductFromExternalSource(long id) throws Exception {
        Optional responseBody;
        StringBuilder fullExternalUri = new StringBuilder();
        fullExternalUri.append(extUri).append(id).append(extUriExcludes);
        
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(fullExternalUri.toString());

            System.out.println("Executing request " + httpget.getRequestLine());
            
            // Create a custom response handler
            ResponseHandler<String> responseHandler = (final HttpResponse response) -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else if(status == 404){
                    return null;
                }
                else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            responseBody = Optional.ofNullable(httpclient.execute(httpget, responseHandler));
            //System.out.println("----------------------------------------");
            //responseBody.ifPresent(System.out::println);
        }
        return responseBody;
    }
}
