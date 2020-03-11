package pro.it.sis.javacourse;

//контейнер для хранения и типизации урона
public class Damage {

    private final String armName;
    private final long phys;
    private final long fire;
    private final long ice;

    public Damage(String armName, long phys, long fire, long ice){
        this.armName = armName;
        this.phys   = phys;
        this.fire   = fire;
        this.ice    = ice;
    }

    public Damage(){
        this.armName = "";
        this.phys   = 0;
        this.fire   = 0;
        this.ice    = 0;
    }
    public long getPhys() {
        return phys;
    }

    public long getFire() {
        return fire;
    }

    public long getIce() {
        return ice;
    }

    @Override
    public String toString() {
        String string = "Arm Name:\t" + getArmName() + "\tPhysical: " + phys + "\tFire: " + fire + "\tIce: " + ice;
        return string;
    }

    public String getArmName() {
        return armName;
    }
}
