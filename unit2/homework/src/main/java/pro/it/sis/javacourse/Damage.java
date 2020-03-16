package pro.it.sis.javacourse;

//контейнер для хранения и типизации урона
public class Damage {

    private final String armName;
    private final long physDamage;
    private final long fireDamage;
    private final long iceDamage;

    public Damage(String armName, long phys, long fire, long ice){
        this.armName      = armName;
        this.physDamage   = phys;
        this.fireDamage   = fire;
        this.iceDamage    = ice;
    }

    public Damage(){
        this.armName        = "";
        this.physDamage     = 0;
        this.fireDamage     = 0;
        this.iceDamage      = 0;
    }
    public long getPhys() {
        return physDamage;
    }

    public long getFire() {
        return fireDamage;
    }

    public long getIce() {
        return iceDamage;
    }

    @Override
    public String toString() {
        return "Arm Name:\t" + getArmName() + "\tPhysical: " + physDamage + "\tFire: " + fireDamage + "\tIce: " + iceDamage;
    }

    public String getArmName() {
        return armName;
    }
}
