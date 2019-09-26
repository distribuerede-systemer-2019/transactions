## Øvelsestime - MySQL og databaser

### 1. Opgave - Hent dette repository
1. Åbn din terminal og naviger hen til mappen hvor du ønsker dette projekt skal ligge (HINT: `cd` bruges til at skifte mappe)
2. Clone dette repository
3. Åbn IntelliJ  og importer projectet. Vælg `import project from existing model` og vælg `maven`
4. OBS: Hvis klassen `ResetDatabase` ikke kan køres, gå til `file` -> `project structure` -> `modules`. Klik på mappen `src` og marker den, og tryk `Sources`. Herefter OK, og du bør kunne køre filen.  

### 2. Opgave - Kør projektet

**OBS** - Før projektet kan køres, skal I have MySql installeret og kørende på jeres maskine (som vi satte op i første øvelsestime).

1. I mappen `/src` højreklik på ResetDatabase
2. Klik på `Run 'ResetDatabase.main()'`

Får I nogle fejl? Hvilke?

Hvis fejlen ligner det her, så gå videre til opg. 3:
```
java.sql.SQLNonTransientConnectionException: Cannot load connection class because of underlying exception: com.mysql.cj.core.exceptions.WrongArgumentException: Failed to parse the host:port pair 'null:null'.
```
### 3. Opgave - Environment variables
I `ResetDatabase`-klassen på linje 22, 23 og 24 kan I se at `System.getenv(...)` bliver kaldt og forventer
at finde en variabel. Da disse ikke er sat på forhånd I jeres projekt, skal disse konfigureres inden systemet
kan køre.

1. Klik på "ResetDatabase" konfigurationen ved den grønne play-knap øverst til højre
2. Klik "Edit Configurations ..."
3. Klik på "..." yderste til højre ved "Environment Variables"
4. Klik på "+" nederst til venstre
5. Opret og navngiv nu følgende variable og indsæt de værdier som passer til dit system: 
```
DATABASE_HOST (default er localhost)
DATABASE_NAME (her vælger du "dis")
DATABASE_PORT (default er 3306)
DATABASE_USER
DATABASE_PASSWORD
```
6. Tryk OK og OK
7. Kør programmet igen

Hvis alt virker som det skal, så printes en masse tekst i konsollen og du kan nu åbne din databse og se hvilke tabeller
samt værdier der er blevet insat. Læg mærke til at det her sagtens kan lade sig gøre at køre `INSERT` statements, hvis I 
vil have default indhold hver gang scriptet køres.

### 4. Opgave
Scriptet er lavet således at det sletter, opretter og kører `sql.sql` filen hver gang programmet køres. Du kan derfor
prøve at smide en masse indhold i databasen og så køre scriptet igen. Din database skulle nu gerne være rullet tilbage
som den så ud da du begyndte.

### 5. Forskellige SQL-queries
1. List alle courses. (Brug SELECT)
2. Prøv at tilføj en ny student. I kan få inspiration i `sql.sql` filen, hvor i kan se, hvordan `INSERT` kommandoen fungere.
3. Assign den nye studerende til et specifict course 
4. (SVÆR) List alle studerende som er assigned til faget Distribuerede Systemer. (Hint: I skal bruge en `INNER JOIN` kommando, som finder ud af, hvad to databaser har tilfældes ud fra nogle fælles felter (typisk bruger man foreign keys). I bunden af `sql.sql` står der en specific query. Denne kan bruges, hvis i tilføjer en betingelse. 
