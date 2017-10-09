## 7. Øvelsestime - Development setup
Hackathon er lige ved at være om hjørnet, og i dag skal vi kigge på hvordan I kan få et godt udviklingsmiljø
op og køre, inden I går amok i koden.

Vi ser ofte at I alle sidder og udvikler på den samme database, hvilket kan ende i masse unødvendige konflikter.

Vi anbefaler derfor at I hver har jeres egen udviklingsdatabase lokalt på jeres computer. I skal blot sørge
for at I alle i gruppen har denne samme tabel-struktur, for at kunne arbejde sammen på projektet. Netop
dette skal vi kigge på hvordan man gør i dag!

#### 1. Opgave - Hent dette repository
1. Åbn din terminal og naviger hen til mappen hvor du ønsker dette projekt skal ligge (HINT: `cd` bruges til at skifte mappe)
2. `git clone https://github.com/Distribuerede-Systemer-2017/db-utility.git`
3. Åbn IntelliJ, lav et nyt projekt og placer projektet oven i dette repository
4. Højreklik på `pom.xml` og tryk "Add as Maven Project".
5. Du er færdig!

#### 2. Opgave - Kør projektet

**OBS** - Før projektet kan køres, skal I have MySql installeret og kørende på jeres maskine (som vi satte op i første øvelsestime).

1. I mappen `/src` højreklik på ResetDatabase
2. Klik på `Run'Server.main()'`

Får I nogle fejl? Hvilke?

Hvis fejlen ligner det her, så gå videre til opg. 3:
```
java.sql.SQLNonTransientConnectionException: Cannot load connection class because of underlying exception: com.mysql.cj.core.exceptions.WrongArgumentException: Failed to parse the host:port pair 'null:null'.
```
#### 3. Opgave - Environment variables
I `ResetDatabase`-klassen på linje 22, 23 og 24 kan I se at `System.getenv(...)` bliver kaldt og forventer
at finde en variable. Da disse ikke er sat på forhånd I jeres projekt, skal disse konfigureres inden systemet
kan køre.

1. Klik på "ResetDatabase" konfigurationen ved den grønne play-knap øverst til højre
2. Klik "Edit Configurations ..."
3. Klik på "..." yderste til højre ved "Environment Variables"
4. Klik på "+" nederst til venstre
5. Opret og navngiv nu følgende variable og indsæt de værdier som passer til dit system: 
```
DATABASE_HOST (højst sandsynligt localhost)
DATABASE_NAME (her vælger du)
DATABASE_PORT (højst sandsynligt 3306)
DATABASE_USER
DATABASE_PASSWORD
```
6. Tryk OK og OK
7. Kør programmet igen

Hvis alt virker som det skal, så printes en masse tekst i konsollen og du kan nu åbne din databse og se hvilke tabeller
samt værdier der er blevet insat. Læg mærke til at det her sagtens kan lade sig gøre at køre `INSERT` statements, hvis I 
vil have default indhold hver gang scriptet køres.

#### 4. Opgave
Scriptet er lavet således at det sletter, opretter og kører `sql.sql` filen hver gang programmet køres. Du kan derfor
prøve at smide en masse indhold i databasen og så køre scriptet igen. Din database skulle nu gerne være rullet tilbage
som den så ud da du begyndte.
