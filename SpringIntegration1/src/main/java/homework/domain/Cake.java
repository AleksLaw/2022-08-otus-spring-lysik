package homework.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@NoArgsConstructor
@Setter
@Getter
public class Cake {
    private Pastry pastry;
    private Filling filling;

    public Cake(Pastry pastry) {
        this.pastry = pastry;
        this.filling = generateFilling();
    }

    @Getter
    public enum Pastry {
        FLOUR(200),
        PUFF(300),
        EXCLUSIVE(600);
        private final int call;

        Pastry(int call) {
            this.call = call;
        }
    }

    @Getter
    public enum Filling {
        MEAT(500),
        JAM(400),
        SUGAR(2000);
        private final int call;

        Filling(int call) {
            this.call = call;
        }
    }

    private Filling generateFilling() {
        Filling[] values = Filling.values();
        return values[new Random().nextInt(values.length)];
    }
}
