ALTER TABLE users
    ADD COLUMN display_name VARCHAR(120) NOT NULL DEFAULT 'New user',
    ADD COLUMN target_level VARCHAR(40),
    ADD COLUMN preferred_explanation_style VARCHAR(40);

ALTER TABLE users
    ALTER COLUMN display_name DROP DEFAULT;
