package paquet;

import java.io.Serializable;
import java.util.UUID;

public class Paquet implements Serializable {
    Action action;
    Object object;
    UUID uuid;

    public enum Action{
        ADD,MAJ,SUPP
    }

    public Paquet(Action action, Object contenair) {
        this.action = action;
        this.object = contenair;
        uuid = null;
    }

    public Paquet(Action action, Object object, UUID uuid) {
        this.action = action;
        this.object = object;
        this.uuid = uuid;
    }

    public Action getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }

    public UUID getUuid() {
        return uuid;
    }
}
