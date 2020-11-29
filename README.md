Krav

Appen er basert på de følgene kravene: 
- Applikasjonen skal kunne hente nåværende kurs i form av XML fra Norges Bank sitt åpne API. (ved bruk av jsoup)
- I applikasjonen skal man ha mulighet til å regne om en gitt input i norske kroner til amerikanske dollar (NOK-USD) eller en gitt input i Amerikanske dollar til Norske kroner (USD-NOK).
- Appen skal ta utgangspunkt i biblioteker definert i tilsendt skjelett.
- Kodespråk brukt er hovedsakling Kotlin og XML, men også Java
Programvarekrav:
- Appen er skrevet i Android Studio, så det vil være et optimalt verktøy for å kjøre koden
- Appen skal kunne også kjøre på API som ble definert i skjelettet. (API 24 - API30)
- All kode, kommentering og tekst er skrevet på Engelsk

Testing

Appen testes kontinuerlig på forskjellige områder via Logging.
Feks. vil feil i Databasekommunikasjonen skrives ut i LogCat (På Android Studio) med tag "DatabaseClass"
Større feil, som f.eks. fraværende internett-tilkobling vil skrives ut til bruker ved hjelp av melding
Det er også en egen testklasse kalt "TestClass.kt", som enhetstester forskjellige varianter av konverteringsresultater
Det er også gjort en Espressotest i filen "Espresso_ConversionActivityTest.java", som tester at designelementer fungerer som de skal
Øvrige tester er gjort manuelt fra egen mobiltelefon (Samsung Galaxy S9, Android 10, API 29), 

Oppbygning

Appen består av en Aktivitetsklasse (ConversionActivity.kt), der generell aktivitet og brukerinteraksjon vil foregå.
De 4 resterende klassene (ConversionMathClass.kt, DatabaseClass.kt, InternetClass.kt, og Testclass.kt) inneholder funksjoner som aktivitetsklassen tar i bruk,
og er delt inn etter ansvarsområde for lettere oversikt og for oppnåelse av modularisering.

Kjøring

Kjøring av appen skjer ved nedlastning av zip-kode.
Deretter åpner man prosjektet i Android Studio, og deretter "run 'app'"
Appen er dessverre ikke testet på Emulator, da personlig pc ikke har nok kapasitet til å kjøre nyere
smarttelefon-programvare.

Bruk

Appen vil ved suksessful kjøring vise en aktivitet med diverse elementer
- Dersom testing er skrudd på (ved "runTests = true" i "ConversionActivity.kt"), 
  vil det dukke opp en melding som forklarer hvorvidt testingen var vellykket eller ikke i det appen starter opp.
- Første inputfelt skriver man inn ønsket beløp man vil konvertere
- De to neste er nedtrekksmenyer (spinnere) der man velger fra- og til-valutta (NOK eller USD)
- Under der igjen vil det være en stor grønn knapp med teksten "CONVERT". Ved trykk på denne vil informasjonen over benyttes til å regne ut riktig konverteringsresultat
- Riktig konvertering er representert i feltet nederst, under teksten "Result"
