package pro.it.sis.javacourse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class BattleTest {

    @Test
    public void battleTest(){
        // Сценарий битвы
        List<Target> targets = new ArrayList<>();
        targets.add(TargetFactory.createTarget(TargetFactory.targetTypes.SHREK));
        targets.add(TargetFactory.createTarget(TargetFactory.targetTypes.PRINCE));

        List<Weapon> swords = new ArrayList<>();
        swords.add(WeaponFactory.createWeapon(WeaponFactory.weaponTypes.YAKUTSK_NIGHT_SWORD));
        swords.add(WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD));

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
