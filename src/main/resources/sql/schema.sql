DROP TABLE IF EXISTS trip;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS driver;

CREATE TABLE driver (
                        id                VARCHAR(20)  PRIMARY KEY,
                        name              VARCHAR(100) NOT NULL,
                        license_category  VARCHAR(1)   NOT NULL
                            CHECK (license_category IN ('B', 'C', 'D')),
                        affiliation_date  DATE         NOT NULL
);

CREATE TABLE vehicle (
                         id             VARCHAR(20)   PRIMARY KEY,
                         plate_number   VARCHAR(20)   NOT NULL UNIQUE,
                         type           VARCHAR(10)   NOT NULL
                             CHECK (type IN ('FOURGON', 'CAMION', 'BENNE')),
                         capacity_tons  NUMERIC(5, 2) NOT NULL
);

CREATE TABLE trip (
                      id              VARCHAR(20)  PRIMARY KEY,
                      driver_id       VARCHAR(20)  NOT NULL REFERENCES driver (id),
                      vehicle_id      VARCHAR(20)  NOT NULL REFERENCES vehicle (id),
                      trip_date       DATE         NOT NULL,
                      departure_city  VARCHAR(100) NOT NULL,
                      arrival_city    VARCHAR(100) NOT NULL,
                      distance_km     INTEGER      NOT NULL CHECK (distance_km > 0),
                      billed_amount   BIGINT       NOT NULL CHECK (billed_amount >= 0),
                      status          VARCHAR(10)  NOT NULL
                          CHECK (status IN ('COMPLETED', 'CANCELLED'))
);

CREATE INDEX idx_trip_driver ON trip (driver_id);
CREATE INDEX idx_trip_date   ON trip (trip_date);
