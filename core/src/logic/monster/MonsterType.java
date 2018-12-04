package logic.monster;

public enum MonsterType {
    //TODO MonsterType(?)
    OwO ("OwO", 1);

    public final String TypeName;
    public final int maxHealth;

    MonsterType(String typeName, int maxHealth) {
        this.TypeName = typeName;
        this.maxHealth = maxHealth;
    }
}
