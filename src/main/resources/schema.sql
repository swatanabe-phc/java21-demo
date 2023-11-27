CREATE TABLE IF NOT EXISTS party (
    id UUID PRIMARY KEY,
    name VARCHAR,
    representative_name VARCHAR,
    address VARCHAR,
    state VARCHAR(32) CHECK (state IN ('EDITING', 'REGISTERED', 'MANUAL_CHECKING', 'MANUAL_CHECKED', 'DECLINED', 'APPROVED'))
);

CREATE TABLE IF NOT EXISTS edit_history (
    id UUID PRIMARY KEY,
    party_id UUID REFERENCES party(id),
    party_name VARCHAR,
    representative_name VARCHAR,
    address VARCHAR,
    saved_time TIMESTAMP,
    user_id VARCHAR
);

CREATE TABLE IF NOT EXISTS register_history (
    id UUID PRIMARY KEY,
    party_id UUID REFERENCES party(id),
    party_name VARCHAR,
    representative_name VARCHAR,
    address VARCHAR,
    register_time TIMESTAMP,
    user_id VARCHAR,
    remarks VARCHAR
);
