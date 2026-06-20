package com.txi.math.mathfxdisplay;

import com.txi.math.mathfxdisplay.config.ContextHolder;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MathSpringBootApplication {

	 static void main(String[] arguments) {
		 ContextHolder.getInstance().setArguments(arguments);
		 Application.launch(MainApp.class, arguments);
	}

}
