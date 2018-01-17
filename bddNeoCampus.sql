------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------

CREATE TYPE Etat AS ENUM ('RECU','LU','EN_ATTENTE');
CREATE TYPE Type AS ENUM ('ETUDIANT','ENSEIGNANT','TECHNIQUE','ADMINISTRATIF');


------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE public.Utilisateur(
	nom         VARCHAR (2000)   ,
	prenom      VARCHAR (2000)   ,
	identifiant INT  NOT NULL ,
	mdp         VARCHAR (2000)   ,
	privilege   BOOL   ,
	typeU       TYPE ,
	CONSTRAINT prk_constraint_Utilisateur PRIMARY KEY (identifiant)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Message
------------------------------------------------------------
CREATE TABLE public.Message(
	message          VARCHAR (2000)   ,
	id               INT  NOT NULL ,
	id_FilDiscussion INT  NOT NULL ,
	identifiant      INT  NOT NULL ,
	CONSTRAINT prk_constraint_Message PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: FilDiscussion
------------------------------------------------------------
CREATE TABLE public.FilDiscussion(
	sujet          VARCHAR (2000)   ,
	id             INT  NOT NULL ,
	id_GroupeNomme INT   ,
	identifiant    INT  NOT NULL ,
	CONSTRAINT prk_constraint_FilDiscussion PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: GroupeNomme
------------------------------------------------------------
CREATE TABLE public.GroupeNomme(
	id  INT  NOT NULL ,
	nom VARCHAR (2000)   ,
	CONSTRAINT prk_constraint_GroupeNomme PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Situation
------------------------------------------------------------
CREATE TABLE public.Situation(
	etat ETAT ,
	CONSTRAINT prk_constraint_Situation PRIMARY KEY (etat)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: TypeUtilisateur
------------------------------------------------------------
CREATE TABLE public.TypeUtilisateur(
	typeU TYPE ,
	CONSTRAINT prk_constraint_TypeUtilisateur PRIMARY KEY (typeU)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Appartenir
------------------------------------------------------------
CREATE TABLE public.Appartenir(
	identifiant INT  NOT NULL ,
	id          INT  NOT NULL ,
	CONSTRAINT prk_constraint_Appartenir PRIMARY KEY (identifiant,id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Recevoir
------------------------------------------------------------
CREATE TABLE public.Recevoir(
	identifiant INT  NOT NULL ,
	id          INT  NOT NULL ,
	etat        ETAT ,
	CONSTRAINT prk_constraint_Recevoir PRIMARY KEY (identifiant,id,etat)
)WITHOUT OIDS;



ALTER TABLE public.Utilisateur ADD CONSTRAINT FK_Utilisateur_typeU FOREIGN KEY (typeU) REFERENCES public.TypeUtilisateur(typeU);
ALTER TABLE public.Message ADD CONSTRAINT FK_Message_id_FilDiscussion FOREIGN KEY (id_FilDiscussion) REFERENCES public.FilDiscussion(id);
ALTER TABLE public.Message ADD CONSTRAINT FK_Message_identifiant FOREIGN KEY (identifiant) REFERENCES public.Utilisateur(identifiant);
ALTER TABLE public.FilDiscussion ADD CONSTRAINT FK_FilDiscussion_id_GroupeNomme FOREIGN KEY (id_GroupeNomme) REFERENCES public.GroupeNomme(id);
ALTER TABLE public.FilDiscussion ADD CONSTRAINT FK_FilDiscussion_identifiant FOREIGN KEY (identifiant) REFERENCES public.Utilisateur(identifiant);
ALTER TABLE public.Appartenir ADD CONSTRAINT FK_Appartenir_identifiant FOREIGN KEY (identifiant) REFERENCES public.Utilisateur(identifiant);
ALTER TABLE public.Appartenir ADD CONSTRAINT FK_Appartenir_id FOREIGN KEY (id) REFERENCES public.GroupeNomme(id);
ALTER TABLE public.Recevoir ADD CONSTRAINT FK_Recevoir_identifiant FOREIGN KEY (identifiant) REFERENCES public.Utilisateur(identifiant);
ALTER TABLE public.Recevoir ADD CONSTRAINT FK_Recevoir_id FOREIGN KEY (id) REFERENCES public.Message(id);
ALTER TABLE public.Recevoir ADD CONSTRAINT FK_Recevoir_etat FOREIGN KEY (etat) REFERENCES public.Situation(etat);



--Ajout de l'utilisateur Admin principal
INSERT INTO public.Utilisateur VALUES ('admin','admin',0,'admin',1,ADMINISTRATIF);