#!/bin/bash
# cURL Test-Script für Tag 3: JPA Relationships & Queries

echo "==================================="
echo "Tag 3 - JPA Relationships Tests"
echo "==================================="
echo ""

# 1. Person mit mehreren Adressen erstellen
echo "1. Person mit mehreren Adressen erstellen..."
curl -X POST http://localhost:8080/api/persons \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Max",
    "lastname": "Mustermann",
    "email": "max@example.com",
    "addresses": [
      {
        "street": "Hauptstraße 1",
        "city": "Berlin",
        "zipCode": "10115",
        "country": "Deutschland",
        "type": "HOME"
      },
      {
        "street": "Geschäftsweg 42",
        "city": "München",
        "zipCode": "80333",
        "country": "Deutschland",
        "type": "WORK"
      }
    ]
  }'
echo ""
echo ""

# 2. Alle Personen abrufen
echo "2. Alle Personen abrufen..."
curl http://localhost:8080/api/persons
echo ""
echo ""

# 3. Adresse zu Person hinzufügen
echo "3. Adresse zu Person 1 hinzufügen..."
curl -X POST http://localhost:8080/api/persons/1/addresses \
  -H "Content-Type: application/json" \
  -d '{
    "street": "Lieferadresse 7",
    "city": "Hamburg",
    "zipCode": "20095",
    "country": "Deutschland",
    "type": "SHIPPING"
  }'
echo ""
echo ""

# 4. Person nach Nachname suchen
echo "4. Person nach Nachname suchen (Mustermann)..."
curl "http://localhost:8080/api/persons/search/by-lastname?lastname=Mustermann"
echo ""
echo ""

# 5. Person nach Email suchen
echo "5. Person nach Email suchen..."
curl "http://localhost:8080/api/persons/search/by-email?email=max@example.com"
echo ""
echo ""

# 6. Personen mit Adresse in Stadt suchen
echo "6. Personen mit Adresse in Berlin suchen..."
curl "http://localhost:8080/api/persons/search/by-city?city=Berlin"
echo ""
echo ""

# 7. Personen mit HOME-Adresse suchen
echo "7. Personen mit HOME-Adresse suchen..."
curl "http://localhost:8080/api/persons/search/by-address-type?type=HOME"
echo ""
echo ""

# 8. Alle Personen MIT Adressen (Fetch Join)
echo "8. Alle Personen MIT Adressen laden (Fetch Join)..."
curl http://localhost:8080/api/persons/with-addresses
echo ""
echo ""

echo "==================================="
echo "Tests abgeschlossen!"
echo "==================================="
