import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class RestClient {
	public static void main(String[] args) throws IOException {
		// usingJavaPlain();
		// usingRestTemplate();
		// usingHeaders();
		// crudHttpOperations();
		// optimisticLazy();
		hateos();
		
	}
	
	public static void hateos () {
		RestTemplate template = new RestTemplate();
		//template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		template.setErrorHandler(new CustomExceptionHandler(template));

		Customer customer =  new Customer();
		customer.setCompanyName("Google INC");
		customer.setNotes("Something");
		
		URI finalLocation = template.postForLocation("http://localhost:8088/CRMSystemRest1-1.0/customers/", customer);
		
		// Rest Code
		Customer getCustomer = template.getForObject(finalLocation, Customer.class);
		System.out.println(getCustomer);
		
		
	}
	
	public static void optimisticLazy() {
		RestTemplate template = new RestTemplate();
		//template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		template.setErrorHandler(new CustomExceptionHandler(template));

		Customer customer = template.getForObject("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939", 
				Customer.class);
		

		System.out.println("Found the customer with id " + customer.getCustomerId() + " and the company name is " + customer.getCompanyName());

		// THINKING TIME!!!!!!!!		
		// this client thought they were changing the name from Apple to VMWare
		// actually, they changing SpringSource to VMWare.

		customer.setCompanyName("VMWare");
		
		template.put("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939", customer);
		
		customer = template.getForObject("http://localhost:8088/CRMSystemRest1-1.0/CS03939", 
				Customer.class);		
		
		System.out.println("To confirm, the customers company is now " + customer.getCompanyName());
		
	}

	public static void crudHttpOperations() {

		System.out.println(" USing  usingRestTemplate ");
		RestTemplate template = new RestTemplate();
		template.setErrorHandler(new CustomExceptionHandler(template));

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptable = new ArrayList<MediaType>();
		acceptable.add(MediaType.APPLICATION_JSON);
		acceptable.add(MediaType.APPLICATION_XML);
		acceptable.add(MediaType.IMAGE_JPEG);
		headers.setAccept(acceptable);

		
		Customer customer = new Customer();
		customer.setCompanyName("Company IBM ");
		customer.setNotes("Adding new Notes");
		
		ResponseEntity<Customer> customerEntity  = template.postForEntity("http://localhost:8088/CRMSystemRest1-1.0/customers", 
				customer, Customer.class);
		
		customer = customerEntity.getBody();
		System.out.println(" Status Response is "+customerEntity.getStatusCode());
		System.out.println(" the new Customer Was created with id "+customer.getCustomerId());
		
		
		// update the customer
		System.out.println(" Updating the customer ");
		
		customer.setCompanyName("Red hat");
		
		template.put("http://localhost:8088/CRMSystemRest1-1.0/customer/" + customer.getCustomerId(), customer);
		
		HttpEntity requestEntity = new HttpEntity(headers);
		
		HttpEntity<CustomerCollectionRepresentation> response2 = template.exchange(
				"http://localhost:8088/CRMSystemRest1-1.0/customers", HttpMethod.GET, 
				requestEntity, CustomerCollectionRepresentation.class);

		
		CustomerCollectionRepresentation results = response2.getBody();

		System.out.println(results.toString());

	}

	public static void usingJavaPlain() throws IOException {
		URL url = new URL("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939");
		InputStream is = url.openStream();
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader bf = new BufferedReader(reader);
		System.out.println(" Using java plan " + bf.readLine());
		System.out.println();
	}

	public static void usingRestTemplate() {
		System.out.println(" USing  usingRestTemplate ");
		RestTemplate template = new RestTemplate();
		Customer response = template.getForObject("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939",
				Customer.class);
		System.out.println(response.toString());
	}

	public static void usingHeaders() {
		System.out.println(" USing  usingRestTemplate ");
		RestTemplate template = new RestTemplate();
		template.setErrorHandler(new CustomExceptionHandler(template));

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptable = new ArrayList<MediaType>();
		acceptable.add(MediaType.APPLICATION_JSON);
		acceptable.add(MediaType.APPLICATION_XML);
		// acceptable.add(MediaType.IMAGE_JPEG);

		HttpEntity requestEntity = new HttpEntity(headers);

		try {
			HttpEntity resp = template.exchange("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939",
					HttpMethod.GET, requestEntity, String.class);
			System.out.println("Successfully found customer " + resp.getBody());
			System.out.println(resp.getBody());

			Customer response = template.getForObject("http://localhost:8088/CRMSystemRest1-1.0/customers/CS03939",
					Customer.class);

			HttpEntity<CustomerCollectionRepresentation> response2 = template.exchange(
					"http://localhost:8088/CRMSystemRest1-1.0/customers", HttpMethod.GET, requestEntity,
					CustomerCollectionRepresentation.class);

			CustomerCollectionRepresentation results = response2.getBody();

			System.out.println(results.toString());

			// System.out.println(response.toString());
		} catch (ResourceNotFoundException e) { // definitely a 404 {
			System.out.println("Sorry, the customer was not found ");
			System.out.println("The message returned back was " + e.getErrorObject().getMessage());
		}

	}
}
