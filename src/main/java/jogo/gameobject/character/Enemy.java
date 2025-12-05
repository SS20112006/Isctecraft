package jogo.gameobject.character;

import jogo.framework.math.Vec3;

public class Enemy extends Character {
    private int attackDamage;

    public Enemy(String name, Vec3 position, int maxHealth, float speed, int attackDamage) {
        super(name, maxHealth, speed);
        this.setPosition(position);
        this.attackDamage = attackDamage;
    }

    public void attack(Character target) {
        System.out.println(this.getName() + " atacou " + target.getName() + " causando " + this.attackDamage + " de dano.");
        target.takeDamage(this.attackDamage);
    }

    @Override
    public void move(Vec3 direction) {
        // Lógica de movimento do inimigo (pode ser mais complexa, como seguir o jogador)
        this.position.add(direction.x * speed, direction.y * speed, direction.z * speed);
        System.out.println(this.getName() + " moveu-se para " + this.position.toString());
    }

    public int getAttackDamage() {
        return attackDamage;
    }
}