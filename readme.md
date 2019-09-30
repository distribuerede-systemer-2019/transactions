I dag handler øvelsen om transaction. De to opgaver indeholder kode baseret på sidste øvelse, hvor vi opretter forskellige objekter der fejler i systemet. Dette skal vi håndtere med transactions, så hvis vores SQL-statements fejler, skal koden køres tilbage før de fejlagtige statements.

**OPGAVE 1.**

**Opgave 1.1**

Start med at køre koden. I burde gerne få en SQL-exception der lyder "java.sql.SQLException: No value specified for parameter 1". Som i kan se i linje 98-100, burde prisen på "Fundamentals of Corporate Finance" være ændret fra 100 til 999, men dette er ikke tilfældet. Hvad skyldes dette?

**Opgave 1.2**

Academic Books ønsker ikke at sætte en bog til salg til en forkert pris. Derfor skal vi bruge transactions. Prøv at benytte dbCon.setAutoCommit(false) før din transaction blok og dbCon.commit() i slutningen og derefter dbCon.setAutoCommit(true). Desuden skal du bruge dbCon.rollback() i metodens catch, for at rulle resultatet af koden tilbage til stadiet før transaction.

**Opgave 1.3**

Prøv nu at fikse Parameter-fejlen. Dette burde giver den rigtige pris på "Fundamentals of Corporate Finance" på 999

**OPGAVE 2.**

**Opgave 2.1**

Lars er blevet tilmeldt til CBS, men CBS har indset de ikke har noget kodet der kan oprette studerende eller deres tilhørende linjer inde i deres system. Prøv at lave metoderne for SQL_INSERT_COURSE, SQL_INSERT_STUDENT og SQL_INSERT_STUDENTCOURSE i linje 125. Hvis i skal bruge hjælp er der udkommenterede eksempler gemt i linje 127.

**Opgave 2.2**

Det viser sig at der er sket en fejl og Lars slet ikke er tilmeldt noget kursus på CBS, men alligevel bliver han oprettet i systemet. Dette var aldrig sket med transaction. Tag din erfaring fra første opgave og aktivér transactions!

**Opgave 3.3** 

Lars burde faktisk være tilmeldt HA(psyk.). Sørg for at tilmeld Lars til "Personlighedspsykologi" så det står i jeres SQL-database.
