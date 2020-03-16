package pro.it.sis.javacourse;

public class WeaponFactory {

    public enum weaponTypes{
        YAKUTSK_NIGHT_SWORD,
        FLAME_ASPHALT_SWORD
    }

    public static Weapon createWeapon(weaponTypes type){
        Weapon toReturn = null;
        switch (type){
            case YAKUTSK_NIGHT_SWORD:
                toReturn = new Weapon(weaponTypes.YAKUTSK_NIGHT_SWORD, 100, 50, 0);
                break;
            case FLAME_ASPHALT_SWORD:
                toReturn = new Weapon(weaponTypes.FLAME_ASPHALT_SWORD, 100, 0 ,50);
                break;
            default:
                break;
        }
        return toReturn;
    }
}
