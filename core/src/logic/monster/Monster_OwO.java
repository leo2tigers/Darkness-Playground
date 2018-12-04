package logic.monster;

import logic.Meth;

import java.util.ArrayList;

public class Monster_OwO extends Monster {

    private static int idcount = 0;
    private int id;
    
    public Monster_OwO(String name, int maxHealth, double positionX, double positionY) {
        super(name, maxHealth, positionX, positionY);
    }

    public Monster_OwO(double positionX, double positionY) {
        super("OwO-"+ Meth.zero_string(++idcount), 1, positionX, positionY);
        id = idcount;
    }

    public static void main(String[] args) {
        ArrayList<Monster_OwO> monsters = new ArrayList<Monster_OwO>();

        for (int i = 0; i < 3; i ++) {
            monsters.add(new Monster_OwO(0, 0));
            System.out.println(monsters.get(i));
        }
    }
    
    @Override
    public String toString() {
    	return "#" + id + " " + super.toString();
    }
}
