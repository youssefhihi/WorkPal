CREATE TYPE user_role AS ENUM ('admin', 'member', 'manager');
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password TEXT NOT NULL,
    role user_role NOT NULL
);
CREATE TABLE admin (
) INHERITS (users);

CREATE TABLE managers (
) INHERITS (users);

CREATE TABLE members (
	phoneNumber VARCHAR(255)
) INHERITS (users);


CREATE OR REPLACE FUNCTION enforce_unique_email()
RETURNS TRIGGER AS $$
BEGIN
    -- Check if the email already exists in either the parent or child tables
    IF EXISTS (SELECT 1 FROM users WHERE email = NEW.email) THEN
        RAISE EXCEPTION 'Email % already exists', NEW.email;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_unique_email
BEFORE INSERT ON users
FOR EACH ROW EXECUTE FUNCTION enforce_unique_email();

CREATE TRIGGER check_unique_email_admin
BEFORE INSERT ON admin
FOR EACH ROW EXECUTE FUNCTION enforce_unique_email();

CREATE TRIGGER check_unique_email_members
BEFORE INSERT ON members
FOR EACH ROW EXECUTE FUNCTION enforce_unique_email();

CREATE TRIGGER check_unique_email_managers
BEFORE INSERT ON managers
FOR EACH ROW EXECUTE FUNCTION enforce_unique_email();


CREATE TABLE types (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE spaces (
    id UUID DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    location VARCHAR(255),
    capacity INT CHECK (capacity > 0),
    type_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (type_id) REFERENCES types(id)
);
