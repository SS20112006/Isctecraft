package jogo.gameobject.character;

import jogo.framework.math.Vec3;

public class Enemy extends Character {
    private int attackDamage;

    public Enemy(String name, Vec3 position, int health, int maxHealth, float speed, int attackDamage) {
        super(name, health, maxHealth, speed);
        this.setPosition(position);
        this.attackDamage = attackDamage;
    }

    public void attack(Character target) {
        System.out.println(this.getName() + " atacou " + target.getName() + " causando " + this.attackDamage + " de dano.");
        target.takeDamage(this.attackDamage);
    }
// implementar movimento AI

   

    public int getAttackDamage() {
        return attackDamage;
    }
}