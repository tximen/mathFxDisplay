package com.txi.math.mathfxdisplay;

import com.jfx4test.framework.api.FxRobot;
import com.jfx4test.framework.junit.FxAssertions;
import com.jfx4test.framework.junit.FxmlController;
import com.txi.math.mathfxdisplay.config.ContextHolder;
import com.txi.math.mathfxdisplay.main.MainView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.jfx4test.framework.junit.ApplicationTest;
import org.springframework.context.ConfigurableApplicationContext;

import static com.jfx4test.framework.util.WaitForAsyncUtils.sleepSeconds;

@SpringBootTest
@ApplicationTest(value="fxml/mainView.fxml", stylesheet = "style/app-component.css")
public class MathFxDisplayApplicationTests  {

    @Autowired
    @FxmlController
    private MainView controller;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

	@Test
	void click_hamburger(FxRobot robot) {
        ContextHolder.getInstance().setApplicationContext(applicationContext);
        robot.clickById("zeroCrossingButton");
        FxAssertions.assertNotVisiblyById("navigationBox");
        robot.clickById("titleBurger");
        sleepSeconds(1);
        FxAssertions.assertVisiblyById("navigationBox");
        robot.clickById("zeroCrossingButton");
        sleepSeconds(1);
        FxAssertions.assertNotVisiblyById("navigationBox");
        robot.clickById("titleBurger");
        sleepSeconds(1);
        FxAssertions.assertVisiblyById("navigationBox");
	}

}
