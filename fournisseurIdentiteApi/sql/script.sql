CREATE TABLE sexes(
   id_sexe SERIAL,
   sexe VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_sexe)
);

CREATE TABLE utilisateurs(
   id_utilisateurs SERIAL,
   email VARCHAR(100)  NOT NULL,
   nom VARCHAR(150)  NOT NULL,
   prenom VARCHAR(150)  NOT NULL,
   date_naissance DATE NOT NULL,
   mot_de_passe VARCHAR(250)  NOT NULL,
   id_sexe INTEGER NOT NULL,
   PRIMARY KEY(id_utilisateurs),
   UNIQUE(email),
   FOREIGN KEY(id_sexe) REFERENCES sexes(id_sexe)
);

CREATE TABLE tokens(
   id_token SERIAL,
   token VARCHAR(250)  NOT NULL,
   date_entree TIMESTAMP NOT NULL,
   date_sortie TIMESTAMP NOT NULL,
   id_utilisateurs INTEGER NOT NULL,
   PRIMARY KEY(id_token),
   UNIQUE(token),
   FOREIGN KEY(id_utilisateurs) REFERENCES utilisateurs(id_utilisateurs)
);

CREATE TABLE connexions(
   id_connexions SERIAL,
   date_tentative TIMESTAMP NOT NULL,
   reussie BOOLEAN,
   nombre_tentatives INTEGER NOT NULL,
   id_utilisateurs INTEGER NOT NULL,
   PRIMARY KEY(id_connexions),
   FOREIGN KEY(id_utilisateurs) REFERENCES utilisateurs(id_utilisateurs)
);
