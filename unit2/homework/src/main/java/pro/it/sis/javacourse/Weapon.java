package pro.it.sis.javacourse;

//Композиция
public class Weapon  {

    private final String    name;
    private final Damage    damage;
    private boolean         readyToWar;

    public Weapon(String name, long phys, long fire, long ice) {
        this.name   = name;
        this.damage = new Damage(name, phys, fire, ice);
        readyToWar = true;
    }

    public Weapon() {
        this.name   = "Безобидный перочинынй нож";
        this.damage = new Damage("", 0,0,0);
        readyToWar = false;
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
        if(isReadyToWar()){
            totalPhysDamage = damage.getPhys() + getRandDamage() + getCriticalDamage();
        }
        //иначе урон 0
        return new Damage(name, totalPhysDamage , damage.getFire(), damage.getIce());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "\t" + damage.toString();
    }

    public boolean isReadyToWar() {
        return readyToWar;
    }

    public Damage getDamage() {
        return damage;
    }
}
