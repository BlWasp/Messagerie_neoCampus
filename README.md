#neoCampus







exit status:
0 : Succes
1 : [FilDeDiscussion.java >  public Message ajouterMessage(Utilisateur u, String m) ]
	L'utilisateur u n'appartient pas � la conversation, il ne doit pas ecrire dedans.
	Il faut ajouter le membre pr�alablement � la file de discussion avec la commande file.ajouterMembres(u).
2 : 
3 : [Message.java > public void recu(Utilisateur u) ]
	Il y a 3 groupes par message:
		- ceux qui appartiennent � "en attente"
		- ceux qui appartiennent � "recu"
		- ceux qui appartiennent � "lu"
 	Chaque membre du groupe appartient EXCLUSIVEMENT et OBLIGATOIREMENT � un de ces 3 groupes.
	Par defaut, tout les membres sont dans le groupe "en attente".
	Pour mettre un utilisateur dans le groupe recu (cad que cet utilisateur � recu le message)  il faut utiliser la commande message.recu(Utilisateur u);
	Pour mettre un utilisateur dans le groupe lu (cad que cet utilisateur � lu le message)  il faut utiliser la commande message.lu(Utilisateur u);
	L'appartenance au groupe recu implique que le message est recu.
	L'appartenance au groupe lu implique que le message est RECU ET lu.
	Pour qu'un utilisateur soit ajout� dans un nouveau groupe il faut qu'il soit pr�sent dans le groupe pr�c�dent, de fa�ons � suivre le cheminement suivant:
	"en attente" -> "recu" -> "lu"