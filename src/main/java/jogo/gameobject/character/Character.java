package jogo.gameobject.character;

import jogo.gameobject.GameObject;
import jogo.framework.math.Vec3;

public abstract class Character extends GameObject {

    protected int health;
    protected int maxHealth;
    protected float speed;
    protected Character(String name, int health, int maxHealth, float speed) {
        super(name);
        this.health = maxHealth; // para começar com a vida cheia
        this.maxHealth = maxHealth;
        this.speed = speed;
    }

    // Example state hooks students can extend


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public void takeDamage(int amount) {
        this.health -= amount;
        if(this.health <= 0) {
            this.health = 0;
        }
    } // para ver se o personagem morreu

    public void move(Vec3 direction) {
        // Implementação vazia por defeito, ou lógica básica
    }
}
