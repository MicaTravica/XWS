Predmetni projekat tima 1 iz premdeta XML i veb servisi.

Tim:
 - Milica Travica SW-26/2016
 - Dušan Bućan SW-30/2016

Uputstvo za pokretanje:

  - Pokretanje frontEnd aplikacije
      - potrebno je je ispratiti sledeće korake
        - pozicionirati se u xmlScientifcPublicationEditorFront
        - izvršiti komande sledećim redosledom:
          - npm install
          - ng s

  - Pokretanje BackEnd apllikacije:
    - potrebno je ispratiti sledeće korake
        - pozicionirati se u direktorijum xmlScientifcPublicationEditor
        - pokretnuti skriptu "installDep.sh" koja se nalazi unutar direktorijuma jars
        - pokrenuti jena fuseki server na portu 3030, </br>
            kreirati PersonDataset ukoliko ne postoji
        - pokrenuti ApacheTomEE server na portu 8080
        - startovati aplikaciju na portu 8081
        
  - Demo: https://drive.google.com/open?id=15HYkSAuIvPgv4aBtN3ZBaJBW4na8fVq_
