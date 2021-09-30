package Dunice.step4;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Step4Application {
			@GetMapping("/greeting")
	public String greeting(
			){
				try {
					//model.addAttribute("name", name);
					return "Hello World";
				}
				catch (Exception e) {
				return e.getMessage();
				}
				}
			}


