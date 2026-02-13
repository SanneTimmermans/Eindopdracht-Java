# Projectauto Planner – Web API

## Inhoudsopgave
1. [Inleiding](#1-inleiding)
2. [Beschrijving van web-API en functionaliteit](#2-beschrijving-van-web-api-en-functionaliteit)
3. [Overzicht van projectstructuur en gebruikte technieken](#3-overzicht-van-projectstructuur-en-gebruikte-technieken)
4. [Benodigdheden](#4-benodigdheden)
5. [Installatie instructies](#5-installatie-instructies)
6. [Uitleg over uitvoeren tests](#6-uitleg-over-uitvoeren-tests)
7. [Testgebruikers en user-rollen](#7-testgebruikers-en-user-rollen)
8. [Overig](#8-overig)

---

## 1. Inleiding
Projectauto Planner is een Web-API voor autogarages die zich bezighouden met voornamelijk restauratie en tuning. 
De API ondersteunt het beheren van projecten, onderdelen, logboeken, documentatie en facturatie, en biedt voertuigeigenaren 
inzage in de voortgang van hun voertuig. Het doel van de API is het verminderen van telefonische en fysieke statuschecks door realtime 
projectinformatie beschikbaar te stellen.

---

## 2. Beschrijving van web-API en functionaliteit
De functies van de API bestaan allemaal onder 6 entiteiten: gebruikers, projecten, onderdelen, logboeken, documentatie en tot slot facturen. Dit zorgt voor functionaliteiten zoals:
* **Gebruikersbeheer:** Aanmaken van gebruikers die aan een project gekoppeld kunnen worden.
* **Projectbeheer:** Aanmaken van projecten waar onderdelen, logboeken en documentatie onder hangen.
* **Onderdelen:** Beheer van onderdelen en de bestelstatus per project.
* **Logboeken:** Registratie van projectvoortgang en urenregistratie.
* **Documentatie:** Toevoegen van foto’s of documenten aan een project of onderdeel.
* **Facturatie:** Automatische prijsberekening op basis van uren en onderdelen.
* **Security:** Authenticatie en autorisatie op basis van rollen (ADMIN, EIGENAAR, MONTEUR).

---

## 3. Projectstructuur en gebruikte technieken
De applicatie is opgebouwd volgens een gelaagde architectuur:

```text
src/main/java
 ├── Controllers      → REST endpoints
 ├── Services         → Businesslogica
 ├── Repositories     → Data access (JPA)
 ├── DomainModels     → Entiteiten
 ├── DTO              → Request/Response objecten
 ├── Exceptions       → Foutafhandeling
 └── Security         → Beveiliging en autorisatie
```
### Gebruikte technieken
* **Java 21**
* **Maven** – Dependency management en build tool
* **Jakarta Bean Validation** – Controleren van inkomende data
* **Spring Boot** – Webframework voor REST API
* **Spring Data JPA** – Database-interactie
* **Spring Security** – Beveiliging en autorisatie
* **PostgreSQL** – Relationele database
* **Keycloak** – OAuth2 / JWT authenticatie

---

## 4. Benodigdheden
Om dit project lokaal te kunnen draaien, is het volgende vereist:
* **Java Development Kit (JDK) 21**
* **Maven**
* **PostgreSQL** (actief op poort `5432`)
* **Keycloak** (actief op poort `9090`)
* **Postman** (voor het testen van de endpoints)

---

## 5. Installatie instructies

### Keycloak Configuratie (Import)
1. Start Keycloak (./kc.bat start-dev --http-port 9090) en log in op de master realm.
2. Klik linksboven op de dropdown 'Master' en kies Create Realm.
3. Klik op Browse bij 'Resource file' en selecteer het meegeleverde bestand realm-export.json.
4. Klik op Create. Alle rollen en instellingen staan nu direct goed.   

### Database aanmaken:

1. Maak in PostgreSQL een database aan met de naam autoplannerdb
2. Configuratie: Open src/main/resources/application.properties en pas de database-gegevens aan:
    1. spring.datasource.username=JOUW_GEBRUIKERSNAAM
    2. spring.datasource.password=JOUW_WACHTWOORD
3. Bij het opstarten wordt de database automatisch gevuld met testdata uit data.sql.  

### API klaarzetten in de IDE:

1. Voer de volgende twee commando's uit in de terminal om alle bibliotheken te installeren
2. mvn clean install
3. mvn spring-boot:run

De API is nu bereikbaar op http://localhost:8080 via Postman. Bij nummer 7 staat uitleg over het token ophalen met de testgebruikers.

---

## 6. Tests uitvoeren

Om de gemaakte testen uit te voeren ga je naar de juiste testmap.  
Src/test/java/nl.projectautoplanner.projectautoplannerwebapi  
In deze map vind je de drie gemaakte tests terug en kunnen deze gerunt worden.

---

## 7. Gebruikers en autorisatieniveaus

Er zijn in keycloak drie gebruikers aangemaakt met de volgende authorizatie:

Eigenaar: is geauthorizeerd voor alle eigenaar functionaliteiten  
Monteur: is geauthorizeerd voor alle monteur functionaliteiten  
TestAccount: is geauthorizeerd voor alle rollen. Hiermee kan dus alles getest worden, ook de twee admin only functies.

Dit zijn de inloggegevens voor elk account (rol: geb.naam / ww)

Eigenaar: Eigenaar / Eigenaar1  
Monteur: Monteur / Monteur1  
TestAccount: TestAccount / Test123  

## 8. overig

[Download Postman collectie](docs/postman/ProjectautoPlanner.postman_collection.json)

[Download Postman auth](docs/postman/auth.postman_environment.json)

[Download Keycloak realm](docs/keycloak/realm-export.json)