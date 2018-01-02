#neoCampus







exit status:
0 : Succes
1 : [FilDeDiscussion.java >  public Message ajouterMessage(Utilisateur u, String m) ]
	L'utilisateur u n'appartient pas à la conversation, il ne doit pas ecrire dedans.
	Il faut ajouter le membre préalablement à la file de discussion avec la commande file.ajouterMembres(u).
2 : 
3 : [Message.java > public void recu(Utilisateur u) ]
	Il y a 3 groupes par message:
		- ceux qui appartiennent à "en attente"
		- ceux qui appartiennent à "recu"
		- ceux qui appartiennent à "lu"
 	Chaque membre du groupe appartient EXCLUSIVEMENT et OBLIGATOIREMENT à un de ces 3 groupes.
	Par defaut, tout les membres sont dans le groupe "en attente".
	Pour mettre un utilisateur dans le groupe recu (cad que cet utilisateur à recu le message)  il faut utiliser la commande message.recu(Utilisateur u);
	Pour mettre un utilisateur dans le groupe lu (cad que cet utilisateur à lu le message)  il faut utiliser la commande message.lu(Utilisateur u);
	L'appartenance au groupe recu implique que le message est recu.
	L'appartenance au groupe lu implique que le message est RECU ET lu.
	Pour qu'un utilisateur soit ajouté dans un nouveau groupe il faut qu'il soit présent dans le groupe précédent, de façons à suivre le cheminement suivant:
	"en attente" -> "recu" -> "lu"