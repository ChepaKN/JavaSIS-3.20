package pro.it.sis.javacourse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TargetTest {
    @Test
    public void testWeaponName() {
        Target target =  TargetFactory.createTarget(TargetFactory.targetTypes.SHREK);
        assertEquals(TargetFactory.targetTypes.SHREK.toString(), target.getName());
    }
    @Test
    public void testImmunityType(){
        Target target =  TargetFactory.createTarget(TargetFactory.targetTypes.SHREK);
        assertEquals(TargetFactory.immunityTypes.ICE.toString(), target.getImmunity());
    }
    @Test
    public void testHP(){
        Target target =  TargetFactory.createTarget(TargetFactory.targetTypes.SHREK);
        assertEquals(900, target.getHealthPoint());
    }


}
