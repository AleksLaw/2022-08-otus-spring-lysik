package homework.fabric;

import homework.domain.Cake;
import org.springframework.stereotype.Service;

@Service
public class CakeFabric {
    public Cake cook(Cake.Pastry pastry) throws Exception {
        System.err.println("Start making " + pastry);
        Thread.sleep(2000);
        Cake cake = new Cake(pastry);
        System.err.println("Cake " + pastry + " with " + cake.getFilling() + " finish");
        return cake;
    }
}
