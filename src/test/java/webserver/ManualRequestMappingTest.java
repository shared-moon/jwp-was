package webserver;

import mvc.controller.Controller;
import controller.ForwardController;
import controller.StaticController;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ManualRequestMappingTest {
    @Test
    void forward_controller() {
        Controller controller = ManualRequestMapping.getController("/index.html");
        assertThat(controller instanceof ForwardController).isTrue();
    }

    @Test
    void static_controller() {
        Controller controller = ManualRequestMapping.getController("/my.js");
        assertThat(controller instanceof StaticController).isTrue();
    }
}
