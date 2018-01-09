package utilisateurs;

import java.util.Comparator;

public class ComparatorUtilisateur implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Utilisateur)o1).getNom().compareTo(((Utilisateur)o2).getNom());
    }
}
