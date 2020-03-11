package pro.it.sis.javacourse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TargetTest {
    String name = "Some target";
    long HP     = 500;

    @Test
    public void testWeaponName() {
        Target target = new Target(name, HP, "");
        assertEquals(name, target.getName());
    }
    @Test
    public void testImmunityType(){
        Target target = new Target(name, HP, "ice");
        assertEquals("ice", target.getImmunity());
    }
    @Test
    public void testHP(){
        Target target = new Target(name, HP, "");
        assertEquals(HP, target.getHealthPoint());
    }


}
