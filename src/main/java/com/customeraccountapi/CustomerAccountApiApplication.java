package com.customeraccountapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Customer Account API",
				version = "1.0",
				description = """
                        REST API for account and transaction management.

                        Features:
                        - Account creation
                        - Account retrieval
                        - Financial transaction registration
                        - Automatic amount normalization according to operation type
                        """,
				contact = @Contact(
						name = "Maria Eduarda",
						email = "mariaeduardaalvesdapaixao0807@gmail.com"
				),
				license = @License(
						name = "MIT"
				)
		)
)public class CustomerAccountApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAccountApiApplication.class, args);
	}

}
