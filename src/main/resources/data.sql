--gebruikers
INSERT INTO gebruikers (id, gebruikersnaam, voornaam, achternaam, email, telefoon, adres, rol)
VALUES (1, 'Administratie_admin', 'Administratie', 'Garage', 'administratie@garage.nl', '0621487536', 'Veldweg 23, Nederweert', 'ADMIN')
ON CONFLICT (id) DO NOTHING;
INSERT INTO gebruikers (id, gebruikersnaam, voornaam, achternaam, email, telefoon, adres, rol)
VALUES (2, 'Henk_monteur', 'Henk', 'Timmermans', 'henk@garage.nl', '0612345678', 'Kerkstraat 1, Nederweert', 'MONTEUR')
ON CONFLICT (id) DO NOTHING;
INSERT INTO gebruikers (id, gebruikersnaam, voornaam, achternaam, email, telefoon, adres, rol)
VALUES (3, 'Gerrie_monteur', 'Gerrie', 'Gerritsen', 'gerrie@garage.nl', '0612131415', 'Langstraat 15, Nederweert', 'MONTEUR')
ON CONFLICT (id) DO NOTHING;
INSERT INTO gebruikers (id, gebruikersnaam, voornaam, achternaam, email, telefoon, adres, rol)
VALUES (4, 'Klaas_klant', 'Klaas', 'Janssen', 'klaas@gmail.com', '0687654321', 'Dorpstraat 5, Horst', 'EIGENAAR')
ON CONFLICT (id) DO NOTHING;
INSERT INTO gebruikers (id, gebruikersnaam, voornaam, achternaam, email, telefoon, adres, rol)
VALUES (5, 'Wilma_klant', 'Wilma', 'van Dijk', 'wilma@hotmail.com', '0610121416', 'Veld 8, Heibloem', 'EIGENAAR')
ON CONFLICT (id) DO NOTHING;
--projecten
INSERT INTO projecten (id, projectnaam, merk, model, eigenaar_id)
VALUES (1, 'Restauratie Porsche 911', 'Porsche', '911 964 turbo s',  4)
ON CONFLICT (id) DO NOTHING;
INSERT INTO projecten (id, projectnaam, merk, model, eigenaar_id)
VALUES (2, 'Motorswap Jetta mk1', 'Volkswagen', 'Jetta mk1',  4)
ON CONFLICT (id) DO NOTHING;
INSERT INTO projecten (id, projectnaam, merk, model, eigenaar_id)
VALUES (3, 'Verlagen golf 1', 'Volkswagen', 'golf mk1 cabrio',  5)
ON CONFLICT (id) DO NOTHING;
INSERT INTO project_monteurs (project_id, monteur_id) VALUES (1, 2);
INSERT INTO project_monteurs (project_id, monteur_id) VALUES (1, 3);
INSERT INTO project_monteurs (project_id, monteur_id) VALUES (2, 3);
INSERT INTO project_monteurs (project_id, monteur_id) VALUES (3, 2);

--onderdelen
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (1, 'Deurgreepjes', 'RW-9922', 45.00, 'BESTELD', 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (2, 'Dakhemel bekleding', 'RW-9832', 200.00, 'BESTELD', 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (3, 'Stoel bekleding', 'RW-9432', 150.00, 'NOG_BESTELLEN', 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (4, 'zuiger set', 'EW-9832', 350.00, 'ONTVANGEN', 2)
ON CONFLICT (id) DO NOTHING;
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (5, 'onderblok', 'EW-9442', 150.00, 'ONTVANGEN', 2)
ON CONFLICT (id) DO NOTHING;
INSERT INTO onderdelen (id, onderdeelnaam, artikelnummer, prijs, bestelstatus, project_id)
VALUES (6, 'verlagingsveren', 'kW-1442', 1500.00, 'NOG_BESTELLEN', 3)
ON CONFLICT (id) DO NOTHING;
--logboek
INSERT INTO logboeken (id, beschrijving, uren, datum_tijd, monteur_id, project_id)
VALUES (1, 'Hemel bekleding gestript', 1.5, CURRENT_TIMESTAMP, 1, 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO logboeken (id, beschrijving, uren, datum_tijd, monteur_id, project_id)
VALUES (2, 'Stoel bekleding gestript', 3, CURRENT_TIMESTAMP, 2, 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO logboeken (id, beschrijving, uren, datum_tijd, monteur_id, project_id)
VALUES (3, 'Zuigers in onderblok geplaatst', 4, CURRENT_TIMESTAMP, 2, 2)
ON CONFLICT (id) DO NOTHING;
INSERT INTO logboeken (id, beschrijving, uren, datum_tijd, monteur_id, project_id)
VALUES (4, 'Ophanging verwijderd', 2, CURRENT_TIMESTAMP, 1, 3)
ON CONFLICT (id) DO NOTHING;
--documentatie
INSERT INTO documentatie (id, bestandsnaam, bestandtype, url, tekst_inhoud, project_id)
VALUES (1, 'porsche_bekleding.png', 'image/png', '/documentatie/download/porsche_bekleding.png', 'Foto van gekozen stof stoelen', 1)
ON CONFLICT (id) DO NOTHING;
INSERT INTO documentatie (id, bestandsnaam, bestandtype, url, tekst_inhoud, project_id)
VALUES (2, 'golf_schema.pdf', 'application/pdf', '/documentatie/download/golf_schema.pdf', 'schema verlagingsveren montage', 3)
ON CONFLICT (id) DO NOTHING;
--facturen
INSERT INTO facturen (id, factuur_datum, project_id, is_betaald, totaal_bedrag)
VALUES (1, '2026-02-10', 2, true, 0.0)
ON CONFLICT (id) DO NOTHING;
SELECT setval(pg_get_serial_sequence('gebruikers', 'id'), (SELECT MAX(id) FROM gebruikers));
SELECT setval(pg_get_serial_sequence('projecten', 'id'), (SELECT MAX(id) FROM projecten));
SELECT setval(pg_get_serial_sequence('onderdelen', 'id'), (SELECT MAX(id) FROM onderdelen));
SELECT setval(pg_get_serial_sequence('logboeken', 'id'), (SELECT MAX(id) FROM logboeken));
SELECT setval(pg_get_serial_sequence('documentatie', 'id'), (SELECT MAX(id) FROM documentatie));
SELECT setval(pg_get_serial_sequence('facturen', 'id'), (SELECT MAX(id) FROM facturen));