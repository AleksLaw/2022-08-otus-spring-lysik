package otus.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SmokingAdminHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (new Random().nextBoolean()) {
            return Health
                    .down()
                    .status(Status.OUT_OF_SERVICE)
                    .withDetail("Причина", "Админ курит приходите потом!")
                    .build();
        } else {
            return Health
                    .up()
                    .build();
        }
    }
}
