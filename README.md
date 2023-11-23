# IMX Fieldlab WOZ

Demo URL: https://imx.apps.digilab.network/fieldlab-woz

## Voorbeeld queries

Object query:

```graphql
query ObjectQuery {
  beschikking(identificatie: "2ab27315-8b66-4696-84fa-1a86f9617466") {
    identificatie
    steltVast {
      vastgesteldeWaarde
      isVoor {
        wozObjectnummer
        typeWozObject
        ontleentAanduidingAan {
          identificatie
          gebruiksdoel
          oppervlakte
          hoofdAdres {
            huisnummer
            huisnummertoevoeging
            postcode
            straatnaam
            omschrijving
            gemeente {
              code
              naam
            }
          }
        }
      }
    }
    isVoor {
      bsn
      identificatie
      naamAanschrijving
      verblijftIn {
        identificatie
        hoofdAdres {
          identificatie
          omschrijving
        }
      }
    }
  }
}
```


Batch query:

```graphql
query BatchQuery($keys: [BeschikkingKey!]!) {
  beschikkingBatch(keys: $keys) {
    identificatie
    steltVast {
      vastgesteldeWaarde
      isVoor {
        wozObjectnummer
        typeWozObject
        ontleentAanduidingAan {
          identificatie
          gebruiksdoel
          oppervlakte
          hoofdAdres {
            huisnummer
            huisnummertoevoeging
            postcode
            straatnaam
            omschrijving
            gemeente {
              code
              naam
            }
          }
        }
      }
    }
    isVoor {
      bsn
      identificatie
      naamAanschrijving
      verblijftIn {
        identificatie
        hoofdAdres {
          identificatie
          omschrijving
        }
      }
    }
  }
}
```

Variables:

```json
{
  "keys": [
    {
      "identificatie": "2ab27315-8b66-4696-84fa-1a86f9617466"
    },
    {
      "identificatie": "8f36059b-5d57-42ec-9107-033b619a05fb"
    },
    {
      "identificatie": "e43d8d6e-e907-4896-8398-d1544294b890"
    },
    {
      "identificatie": "925bed07-3e61-4938-b2f2-2f763763c7c1"
    },
    {
      "identificatie": "f12f5717-a95e-4db5-991d-40d56678d9b5"
    },
    {
      "identificatie": "23f3b88d-c20c-42c1-a7ca-1a17a8946ef4"
    },
    {
      "identificatie": "8cf2aba3-1376-4c81-a228-ab48794a7e85"
    },
    {
      "identificatie": "4c8c2d2f-916e-46db-8e46-df35378f1cd6"
    },
    {
      "identificatie": "d92445de-2747-4c45-bd4f-545ff800f99b"
    },
    {
      "identificatie": "74d00324-ba42-4fed-b2d6-cb14106a49cd"
    }
  ]
}
```