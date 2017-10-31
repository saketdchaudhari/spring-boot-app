package springbootstarter.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements IGreetingService{

	private static final String GREET_MESSAGE = "Hello World!";
    
	@Override
	public String greetUser() {
		return GREET_MESSAGE;
	}
	
}
