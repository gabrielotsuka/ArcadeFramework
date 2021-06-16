package spriteframework.sprite;


import java.util.LinkedList;

public class BadnessBoxSprite extends BadSprite {
    private boolean destroyed = false;

    LinkedList<BadSprite> badnesses = new LinkedList<>();

    void add(BadSprite b) {
        badnesses.add(b);
    }

    public LinkedList<BadSprite> getBadnesses() {
        return badnesses;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}