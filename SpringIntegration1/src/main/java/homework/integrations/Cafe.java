package homework.integrations;

import homework.domain.Cake;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface Cafe {
    @Gateway(requestChannel = "ordersChannel", replyChannel = "caloriesChannel")
    Collection<Cake> process(Collection<Cake.Pastry> heads);
}
