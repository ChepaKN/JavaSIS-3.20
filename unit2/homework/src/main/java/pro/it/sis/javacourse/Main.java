package pro.it.sis.javacourse;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args) {

        // Сценарий битвы
        List<Weapon> swords = new ArrayList<>();
        swords.add(new Weapon("YakutskNightSword", 100, 50, 0));
        swords.add(new Weapon("FlameAsphaltSword", 100, 0, 50));

        List<Target> targets = new ArrayList<>();
        targets.add(new Target("Shrek", 900, "ice"));
        targets.add(new Target("Prince", 700, ""));

        for(Weapon w : swords) {
            for (Target t : targets) {
                while (t.isAlive()) {
                    System.out.println(t.toString());
                    t.receiveDamage(swords.get(0).hurt());
                }t.raise(1000);
            }
        }
    }
}
