INSERT INTO location (name, city, country)
SELECT 'Warsaw Branch', 'Warsaw', 'Poland'
    WHERE NOT EXISTS (SELECT 1 FROM location);

INSERT INTO location (name, city, country)
SELECT 'Default Branch', 'Warsaw', 'Poland'
WHERE NOT EXISTS (SELECT 1 FROM location);

ALTER TABLE car ADD COLUMN IF NOT EXISTS location_id BIGINT;

UPDATE car SET location_id = (SELECT id FROM location LIMIT 1)
WHERE location_id IS NULL;

ALTER TABLE car ALTER COLUMN location_id SET NOT NULL;

ALTER TABLE car ADD CONSTRAINT fk_car_location
    FOREIGN KEY (location_id) REFERENCES location(id);