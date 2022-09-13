package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.Book;
import com.practice.proxy.CambioProxy;
import com.practice.repository.BookRepository;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private Environment env;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;
	
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
		
		var book = repository.findById(id).get();
		if(book == null) throw new RuntimeException("Book not found");
		
		/* HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate()
			.getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
			Cambio.class,
			params); 
		
		var cambio = response.getBody(); */
		
		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		book.setPrice(cambio.getConvertedValue());
		
		var port = env.getProperty("local.server.port");
		book.setEnvironment("Porta book: "+port + " | Porta cambio: "+ cambio.getEnviroment());
		book.setCurrency(currency);
		
		return book;
	}
	
}
