package pro.it.sis.javacourse;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args)  {
        try {
            // write your code here
            List<Target> targets = new ArrayList<>();
            targets.add(TargetFactory.createTarget(TargetFactory.targetTypes.SHREK));
            targets.add(TargetFactory.createTarget(TargetFactory.targetTypes.PRINCE));

            List<Weapon> swords = new ArrayList<>();
            swords.add(WeaponFactory.createWeapon(WeaponFactory.weaponTypes.YAKUTSK_NIGHT_SWORD));
            swords.add(WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD));

            for (Weapon s : swords) {
                for (Target t : targets) {
                    while (t.isAlive()) {
                        System.out.println(t.toString());
                        t.receiveDamage(s.hurt());
                    }
                    t.raise(1000);
                }
            }
        }catch (Exception NullPointerException){
            NullPointerException.printStackTrace();
            System.out.println("Несуществующий тип Оружия или Цели!");
        }
    }
}
