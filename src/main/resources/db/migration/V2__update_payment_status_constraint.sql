ALTER TABLE draft_rental DROP CONSTRAINT IF EXISTS draft_rental_payment_status_check;
ALTER TABLE draft_rental ADD CONSTRAINT draft_rental_payment_status_check
    CHECK (payment_status IN ('NOT_STARTED', 'PENDING', 'COMPLETED', 'FAILED', 'CANCELED_BY_USER', 'REFUNDED'));

ALTER TABLE rental DROP CONSTRAINT IF EXISTS rental_payment_status_check;
ALTER TABLE rental ADD CONSTRAINT rental_payment_status_check
    CHECK (payment_status IN ('NOT_STARTED', 'PENDING', 'COMPLETED', 'FAILED', 'CANCELED_BY_USER', 'REFUNDED'));