package com.txi.math.mathfxdisplay.zerocrossing;

import com.jfx4test.framework.api.FxRobot;
import com.jfx4test.framework.junit.ApplicationTest;
import com.jfx4test.framework.junit.FxmlController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jfx4test.framework.util.WaitForAsyncUtils.sleepSeconds;

@SpringBootTest
@ApplicationTest(value="fxml/NullStellenView.fxml")
public class NullStellenViewTest {


    @FxmlController
    public NullStellenView nullStellenView() {
        return new NullStellenView(nullStellenModel());
    }

    private NullStellenModel nullStellenModel() {
        return new NullStellenModel();
    }

    @Test
    void open_view(FxRobot robot) {
        sleepSeconds(10);
    }
}
