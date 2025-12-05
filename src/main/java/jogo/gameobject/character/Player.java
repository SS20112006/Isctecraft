package jogo.gameobject.character;

import jogo.framework.math.Vec3;

public class Player extends Character {
    private int experience;
    private int level;

    public Player(String name, Vec3 position, int maxHealth, float speed) {
        super(name, maxHealth, speed);
        this.setPosition(position);
        this.experience = 0;
        this.level = 1;
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        // Lógica para subir de nível pode ser adicionada aqui
        System.out.println(this.getName() + " ganhou " + amount + " de experiência. Total: " + this.experience);
    }

    @Override
    public void move(Vec3 direction) {
        // Lógica de movimento do jogador
        this.position.add(direction.x * speed, direction.y * speed, direction.z * speed);
        
    }

    // Getters adicionais para Player
    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }
}