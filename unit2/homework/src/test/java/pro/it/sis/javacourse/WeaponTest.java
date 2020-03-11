package pro.it.sis.javacourse;

import org.junit.Test;

import java.nio.file.Watchable;

import static org.junit.Assert.*;

public class WeaponTest {
    String name     = "Some sword";
    long someDamage = 30;
    @Test
    public void testWeaponName() {
        Weapon weapon = new Weapon(name, 100, 0 ,0);
        assertEquals(name, weapon.getName());
    }
    @Test
    public void testWeaponPhysDamage(){
        Weapon weapon = new Weapon(name, someDamage, 0 , 0);
        assertEquals(someDamage, weapon.getDamage().getPhys());
    }
    @Test
    public void testWeaponIceDamage(){
        Weapon weapon = new Weapon(name, 100, 0 , someDamage);
        assertEquals(someDamage, weapon.getDamage().getIce());
    }
    @Test
    public void testWeaponFireDamage(){
        Weapon weapon = new Weapon(name, 100, someDamage , 0);
        assertEquals(someDamage, weapon.getDamage().getFire());
    }


}