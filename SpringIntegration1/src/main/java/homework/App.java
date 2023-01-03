package homework;

import homework.domain.Cake;
import homework.integrations.Cafe;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
public class App {

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        Cafe cafe = ctx.getBean(Cafe.class);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        while (true) {
            Thread.sleep(4000);
            forkJoinPool.execute(() -> {
                Collection<Cake.Pastry> items = generatePastries();
                String collect = items
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(","));
                System.err.println("New Order for kitchen: " + collect);
                Collection<Cake> cakes = cafe.process(items);
                System.err.println("Order: " + collect + "\nSum call = " + cakes.stream()
                        .map(d -> d.getFilling().getCall() + d.getPastry().getCall())
                        .flatMapToInt(IntStream::of)
                        .sum());
            });
        }
    }

    private static Cake.Pastry generatePastry() {
        Cake.Pastry[] values = Cake.Pastry.values();
        return values[new Random().nextInt(values.length)];
    }

    private static Collection<Cake.Pastry> generatePastries() {
        List<Cake.Pastry> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            items.add(generatePastry());
        }
        return items;
    }
}
