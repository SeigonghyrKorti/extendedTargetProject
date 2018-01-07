/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

/**
 *
 * @author sei
 */
import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;
 /*
 * @author sei
 */
@Component
public class ServerConfig implements EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8080);
	}

}