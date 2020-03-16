package pro.it.sis.javacourse;

public class TargetFactory {

    public enum targetTypes{
        SHREK,
        PRINCE
    }

    public enum immunityTypes{
        FIRE,
        ICE
    }

    public static Target createTarget(targetTypes type){
        Target toReturn = null;
        switch (type){
            case SHREK:
                toReturn = new Target(targetTypes.SHREK, 900, immunityTypes.ICE);
                break;
            case PRINCE:
                toReturn = new Target(targetTypes.PRINCE, 800, immunityTypes.FIRE);
                break;
            default:
                break;
        }
        return toReturn;
    }
}
