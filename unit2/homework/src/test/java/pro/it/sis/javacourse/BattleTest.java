package pro.it.sis.javacourse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class BattleTest {

    @Test
    public void battleTest(){
        // Сценарий битвы
        List<Weapon> swords = new ArrayList<>();
        swords.add(new Weapon("YakutskNightSword", 100, 50, 0));
        swords.add(new Weapon("FlameAsphaltSword", 100, 0, 50));

        List<Target> targets = new ArrayList<>();
        targets.add(new Target("Shrek", 900, "ice"));
        targets.add(new Target("Prince", 700, "fire"));

        //Убиваем всех первым мечем
        for(Target t : targets){
            while (t.isAlive()){
                System.out.println(t.toString());
                t.receiveDamage(swords.get(0).hurt());
            }
        }

        //Удаляем всех убитых
        targets.removeIf(t->!t.isAlive());
        assertTrue(targets.isEmpty());
    }

}
