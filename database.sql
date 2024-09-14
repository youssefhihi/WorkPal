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

CREATE TABLE space_service (
    space_id UUID NOT NULL,
    service_id UUID NOT NULL,
    PRIMARY KEY (space_id, service_id),
    FOREIGN KEY (space_id) REFERENCES spaces(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE

);
CREATE TABLE services (
	id UUID DEFAULT gen_random_uuid(),
	 name VARCHAR(255) UNIQUE NOT NULL,
    price INT,
	PRIMARY KEY (id)
);

CREATE TABLE subscriptions (
    id UUID PRIMARY KEY DEFAULT  gen_random_uuid(),
    duration INT NOT NULL,
    durationType VARCHAR(50) NOT NULL,
    member_id UUID NOT NULL,
    startDate VARCHAR(30) NOT NULL,
    space_id UUID NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (space_id) REFERENCES spaces(id) ON DELETE CASCADE
);
ALTER TABLE members
ADD CONSTRAINT pk_members_id PRIMARY KEY (id);

CREATE TABLE subscription_services (
    subscription_id UUID NOT NULL,
    service_id UUID NOT NULL,
    PRIMARY KEY (subscription_id, service_id),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions (id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE

);

ALTER TABLE subscriptions
ADD COLUMN accepted BOOLEAN DEFAULT FALSE;

ALTER TABLE spaces
ADD COLUMN manager_id UUID;

ALTER TABLE spaces
ADD CONSTRAINT fk_manager
FOREIGN KEY (manager_id) REFERENCES managers(id);

CREATE TABLE feedbacks (
    id UUID PRIMARY KEY DEFAULT  gen_random_uuid(),
    comment TEXT NOT NULL,
    member_id UUID NOT NULL,
    space_id UUID NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY (space_id) REFERENCES spaces(id) ON DELETE CASCADE
);

