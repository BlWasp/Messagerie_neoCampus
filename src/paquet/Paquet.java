package paquet;

import java.io.Serializable;

public class Paquet implements Serializable {
    Action action;
    Object object;

    public enum Action{
        ADD,MAJ,SUPP
    }

    public Paquet(Action action, Object contenair) {
        this.action = action;
        this.object = contenair;
    }

    public Action getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }
}
