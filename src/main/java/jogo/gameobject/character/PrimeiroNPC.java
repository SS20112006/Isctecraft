package jogo.gameobject.character;


import jogo.framework.math.Vec3;

public class PrimeiroNPC extends Character{

    private boolean friendly;

    public PrimeiroNPC(String name, int health, boolean friendly, Vec3 position) {
        super(name,);
        this.friendly = friendly;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }
}
