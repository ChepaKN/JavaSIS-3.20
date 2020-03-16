package pro.it.sis.javacourse;

//Композиция
public class Weapon  {

    private final String    name;
    private final Damage    damage;

    public Weapon(WeaponFactory.weaponTypes name, long phys, long fire, long ice) {
        this.name   = name.name();
        this.damage = new Damage(name.name(), phys, fire, ice);
    }

    public Weapon() {
        this.name   = "Безобидный перочинный нож";
        this.damage = new Damage("", 0,0,0);
    }

    //Элемент "Случайности"
    private long getRandDamage(){
        return (long)(Math.random()*10) - 5;    // +-5 единиц XP
    }

    //Дополнительный "Критический" физический урон
    private long getCriticalDamage(){
        long random1 = (long)(Math.random()*20);
        long random2 = (long)(Math.random()*20);
        if(random1 == random2){
            System.out.println(name + "\t Критический удар!");
            return damage.getPhys();
        }else{
            return 0;
        }
    }

    //нанести урон
    public Damage hurt(){
        long totalPhysDamage  = 0;
        if(damage.getPhys() > 0){
            totalPhysDamage = damage.getPhys() + getRandDamage() + getCriticalDamage();
        }
        return new Damage(name, totalPhysDamage , damage.getFire(), damage.getIce());
    }

    public String getName() {
        return name;
    }

    public Damage getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return name + "\t" + damage.toString();
    }


}
