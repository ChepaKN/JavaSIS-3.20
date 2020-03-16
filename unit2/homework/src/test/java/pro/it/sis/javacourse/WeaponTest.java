package pro.it.sis.javacourse;

import org.junit.Test;
import static org.junit.Assert.*;

public class WeaponTest {
    String name     = "FLAME_ASPHALT_SWORD";
    long physDamage = 100;
    long fireDamage = 50;
    long iceDamage  = 0;

    @Test
    public void testWeaponName() {
        Weapon weapon = WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD);
        assertEquals(name, weapon.getName());
    }
    @Test
    public void testWeaponPhysDamage(){
        Weapon weapon = WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD);
        assertEquals(physDamage, weapon.getDamage().getPhys());
    }
    @Test
    public void testWeaponIceDamage(){
        Weapon weapon = WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD);
        assertEquals(fireDamage, weapon.getDamage().getIce());
    }
    @Test
    public void testWeaponFireDamage(){
        Weapon weapon = WeaponFactory.createWeapon(WeaponFactory.weaponTypes.FLAME_ASPHALT_SWORD);
        assertEquals(iceDamage, weapon.getDamage().getFire());
    }
}