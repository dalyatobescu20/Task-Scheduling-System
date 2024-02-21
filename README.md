
//Tobescu Dalya-Alexandra 331CB//
//Tema 2 APD//

Pasii implementarii mele pentru fiecare clasa:

1. Clasa MyDispatcher:
    
    -->am creat o coada cu prioritate de tipul Task pentru fiecare Host
    -->in functie de fiecare tip de algortim de scheduling, am adaugat 
       task-urile in vectorul de task-uri al fiecarui host:
        - pentru RoundRobin, am adaugat task-urile in ordinea in care au fost citite,
          folosind formula (lastAssignedIndex + 1) % hosts.size();
        - pentru ShortestQueue, am adaugat task-urile in host-ul cu coada de task-uri
          cea mai mica, folosind functia getQueueSize();
        -pentru SizeIntervalTaskAssigment, am adaugat in host-ul corespunzator
         tipului de task (small, medium, long).
        -pentru LeastWorkLeft, am adaugat task-urile in host-ul cu cea mai mica
          munca ramasa de executat, folosind functia getWorkLeft();

2. Clasa MyHost:
    -->am creat o PriorityBlockingQueue de tipul Task pentru a retine task-urile
       ce trebuie executate
    
    -->metoda run():
       -->cat timp sunt task-uri de executat si cat timp sunt task-uri in coada
          de task-uri, se extrage un task din coada si se executa. Daca avem mai
          multe task-uri in coada, verificam daca gasim un task cu prioritatea
          mai mare decat cea a task-ului curent si task-ul curent este preemtabil,
          iar daca gasim, il executam pe acela si il adaugam inapoi in coada pe 
          task-ul curent.
    -->metoda addTask():
       -->adauga un task in coada de task-uri a host-ului
    -->metoda getQueueSize():
          -->returneaza dimensiunea cozii de task-uri a host-ului(adaug + 1 pentru
             task-ul curent)
    -->metoda getWorkLeft():
            -->returneaza munca ramasa de executat a host-ului apeland pentru fiecare
               task metoda getWorkLeft(adaug + 1 pentru task-ul curent)
    -->metoda executeTask():
            -->executa un task si scade munca ramasa de executat a host-ului
            -->simulez executia unui task prin apelarea metodei sleep() cu 1000 de milisecunde
            -->daca munca ramasa de executat a host-ului este 0, atunci apelez metoda finish();
    -->metoda shutDown():
       -->seteaza variabila de tip boolean isRunning pe false pentru a opri executia
          host-ului