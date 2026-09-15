-- =====================================================================
-- KOFIA - Cooperative de transporteurs
-- Jeu de donnees de reference
--
-- CE SCRIPT VOUS EST FOURNI. Ne le modifiez pas.
-- Execution : psql -U postgres -d kofia -f db/data.sql
-- =====================================================================

DELETE FROM trip;
DELETE FROM vehicle;
DELETE FROM driver;

-- ---------------------------------------------------------------------
-- Chauffeurs
-- ---------------------------------------------------------------------
INSERT INTO driver (id, name, license_category, affiliation_date) VALUES
                                                                      ('d-001', 'Rakoto Jean', 'C', DATE '2022-01-15'),
                                                                      ('d-002', 'Hery Randria', 'C', DATE '2022-06-01'),
                                                                      ('d-003', 'Naina Rabe', 'D', DATE '2023-03-10'),
                                                                      ('d-004', 'Tojo Andria', 'B', DATE '2024-09-05'),
                                                                      ('d-005', 'Fara Rasoa', 'C', DATE '2025-02-20');

-- ---------------------------------------------------------------------
-- Vehicules
-- ---------------------------------------------------------------------
INSERT INTO vehicle (id, plate_number, type, capacity_tons) VALUES
                                                                ('v-001', 'TAA-1001', 'CAMION', 12.5),
                                                                ('v-002', 'TAA-1002', 'CAMION', 8.0),
                                                                ('v-003', 'TBB-2001', 'FOURGON', 3.5),
                                                                ('v-004', 'TCC-3001', 'BENNE', 20.0);

-- ---------------------------------------------------------------------
-- Courses
-- ---------------------------------------------------------------------
INSERT INTO trip (id, driver_id, vehicle_id, trip_date, departure_city,
                  arrival_city, distance_km, billed_amount, status) VALUES
                                                                        ('t-001', 'd-001', 'v-001', DATE '2026-04-02', 'Antananarivo', 'Toamasina', 350, 2800000, 'COMPLETED'),
                                                                        ('t-002', 'd-001', 'v-001', DATE '2026-04-07', 'Toamasina', 'Antananarivo', 350, 2800000, 'COMPLETED'),
                                                                        ('t-003', 'd-001', 'v-001', DATE '2026-04-14', 'Antananarivo', 'Antsirabe', 170, 1360000, 'COMPLETED'),
                                                                        ('t-004', 'd-001', 'v-001', DATE '2026-04-21', 'Antananarivo', 'Fianarantsoa', 410, 3280000, 'CANCELLED'),
                                                                        ('t-005', 'd-002', 'v-002', DATE '2026-04-03', 'Antananarivo', 'Mahajanga', 570, 4560000, 'COMPLETED'),
                                                                        ('t-006', 'd-002', 'v-002', DATE '2026-04-12', 'Mahajanga', 'Antananarivo', 570, 5130000, 'COMPLETED'),
                                                                        ('t-007', 'd-002', 'v-002', DATE '2026-04-25', 'Antananarivo', 'Antsirabe', 170, 1190000, 'COMPLETED'),
                                                                        ('t-008', 'd-003', 'v-004', DATE '2026-04-05', 'Antananarivo', 'Toamasina', 350, 3500000, 'COMPLETED'),
                                                                        ('t-009', 'd-003', 'v-004', DATE '2026-04-18', 'Toamasina', 'Antananarivo', 350, 3500000, 'COMPLETED'),
                                                                        ('t-010', 'd-004', 'v-003', DATE '2026-04-08', 'Antananarivo', 'Ambatolampy', 70, 420000, 'COMPLETED'),
                                                                        ('t-011', 'd-004', 'v-003', DATE '2026-04-15', 'Antananarivo', 'Arivonimamo', 45, 270000, 'COMPLETED'),
                                                                        ('t-012', 'd-004', 'v-003', DATE '2026-04-22', 'Antananarivo', 'Antsirabe', 170, 1020000, 'CANCELLED'),
                                                                        ('t-013', 'd-005', 'v-002', DATE '2026-05-04', 'Antananarivo', 'Toamasina', 350, 2800000, 'COMPLETED'),
                                                                        ('t-014', 'd-005', 'v-002', DATE '2026-05-11', 'Toamasina', 'Antananarivo', 350, 2800000, 'COMPLETED'),
                                                                        ('t-015', 'd-001', 'v-001', DATE '2026-05-05', 'Antananarivo', 'Mahajanga', 570, 4560000, 'COMPLETED'),
                                                                        ('t-016', 'd-003', 'v-004', DATE '2026-03-28', 'Antananarivo', 'Antsirabe', 170, 1700000, 'COMPLETED')


SELECT id, driver_id, vehicle_id, trip_date, distance_km, billed_amount, status
FROM trip
WHERE driver_id = 'd-001';