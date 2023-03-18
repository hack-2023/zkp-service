CREATE TABLE IF NOT EXISTS zkp_service.USER (
  id                  BIGSERIAL        PRIMARY KEY NOT NULL UNIQUE,
  email               TEXT             UNIQUE NOT NULL,
  somehash            TEXT,
  created_on timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);