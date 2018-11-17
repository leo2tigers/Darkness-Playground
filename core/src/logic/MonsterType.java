package logic;

public enum MonsterType {
    //TODO MonsterType(?)
    OwO ("OwO", 1);

    final String TypeName;
    final int maxHealth;

    MonsterType(String typeName, int maxHealth) {
        this.TypeName = typeName;
        this.maxHealth = maxHealth;
    }
}
