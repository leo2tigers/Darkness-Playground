package logic;

public enum MonsterType {
    OwO ("OwO", 1);

    final String name;
    final int maxHealth;

    MonsterType(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
    }
}
