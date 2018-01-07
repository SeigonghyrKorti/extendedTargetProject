package product;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    
    private final String PRODUCT_URI = "/products/13860428";
    private final String EXPECTED_PRODUCT_JSON = "{\"id\":0,\"name\":\"The Big Lebowski (Blu-ray)\",\"price\":{\"value\":14.99,\"currencyCode\":\"USD\"}}";

    @Test
    public void testRoot() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Target Product Controller.")));
    }
    
    @Test
    public void testGetValidProductId() throws Exception {       
        mvc.perform(MockMvcRequestBuilders.get(PRODUCT_URI).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testGetInvalidProductId() throws Exception {       
        mvc.perform(MockMvcRequestBuilders.get("/products/1386042").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}