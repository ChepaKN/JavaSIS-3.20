package pro.it.sis.javacourse;

public class Target{

    private boolean         alive;
    private long            healthPoint;
    private final String    immunity;
    private final String    name;

    public Target(){
        this.name           = "Цель - пустышка";
        this.alive          = false;
        this.healthPoint    = 0;
        this.immunity       = "";
    }
    public Target(String name, long healthPoint, String immunity){
        this.name           = name;
        this.healthPoint    = healthPoint;
        this.immunity       = immunity;

        if(healthPoint > 0) this.alive = true;
        else this.alive     = false;
    }

    public long getHealthPoint(){
        return healthPoint;
    }

    public String getImmunity(){
        return immunity;
    }

    public String getName(){
        return  name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void raise(long XP){
        healthPoint = XP;
        alive       = true;
    }

    public void receiveDamage(Damage damage){

        if(!isAlive()){
            System.out.println(this.name + ":" + "\tТо что мертво, умереть не может");
            return;
        }

        //Урон с учетом имунитета
        long totalDamage = 0;
        switch (immunity){
            case("fire"):
                totalDamage = damage.getPhys() + damage.getIce();
                break;
            case("ice"):
                totalDamage = damage.getPhys() + damage.getFire();
                break;
            default:
                totalDamage = damage.getPhys() + damage.getFire() + damage.getIce();
                break;
        }

        healthPoint -= totalDamage;
        if(healthPoint <= 0) {
            System.out.println(this.name + ":"  + "\tУбит оружием: \t" + damage.getArmName());
            alive = false;
        }
    }

    @Override
    public String toString() {
        String string = "name: " + name + ";\talive: " + isAlive() + ";\tXP:" + healthPoint;
        return string;
    }
}

