CREATE TABLE IF NOT EXISTS localStorage(
    key VARCHAR(200) PRIMARY KEY NOT NULL,
    value VARCHAR(200) NOT NULL
)

CREATE TABLE IF NOT EXISTS logs(
    key INT PRIMARY KEY NOT NULL,
    description VARCHAR(200) NOT NULL,
    log_date VARCHAR(100)
)