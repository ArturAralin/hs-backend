CREATE TABLE "patients" (
  id SERIAL PRIMARY KEY,
  fio TEXT not null,
  gender TEXT NOT NULL,
  birthday DATE,
  address TEXT NOT NULL DEFAULT '',
  oms_policy TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ
);
