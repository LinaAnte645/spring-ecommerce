package com.ecommerce.springecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*clase de configuracion
*nos va ayudar ha apuntar desde cualquier lugar a los recursos de imagenes*/
@Configuration
public class ResourceWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
}
