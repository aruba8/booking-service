CREATE TABLE "landlord" (
    "id" serial,
    "first_name" varchar(100) NOT NULL,
    "middle_name" varchar(100),
    "last_name" varchar(100) NOT NULL,
    "phone_number" varchar(100),
    "email" varchar(100) NOT NULL,
    CONSTRAINT landlord_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "apartment" (
    "id" serial,
    "address" varchar(200) NOT NULL,
    "price" numeric(25,2) NOT NULL,
    "landlord_id" bigint NOT NULL,
    CONSTRAINT apartment_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "transaction" (
    "id" serial,
    "sum" numeric(25,2) NOT NULL,
    "apartment_id" bigint NOT NULL,
    "tenant_id" bigint NOT NULL,
    "booking_period_id" bigint NOT NULL,
    "transaction_time" TIMESTAMP NOT NULL,
    CONSTRAINT transaction_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "tenant" (
    "id" serial,
    "first_name" varchar(100) NOT NULL,
    "last_name" varchar(100) NOT NULL,
    "middle_name" varchar(100),
    "phone_number" varchar(100),
    "email" varchar(100) NOT NULL,
    CONSTRAINT tenant_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);



CREATE TABLE "booking_period" (
    "id" serial,
    "from_date" DATE NOT NULL,
    "to_date" DATE NOT NULL,
    "apartment_id" bigint NOT NULL,
    CONSTRAINT booking_period_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);




ALTER TABLE "apartment" ADD CONSTRAINT "apartment_fk0" FOREIGN KEY ("landlord_id") REFERENCES "landlord"("id");
ALTER TABLE "transaction" ADD CONSTRAINT "transaction_fk0" FOREIGN KEY ("apartment_id") REFERENCES "apartment"("id");
ALTER TABLE "transaction" ADD CONSTRAINT "transaction_fk1" FOREIGN KEY ("tenant_id") REFERENCES "tenant"("id");
ALTER TABLE "transaction" ADD CONSTRAINT "transaction_fk2" FOREIGN KEY ("booking_period_id") REFERENCES "booking_period"("id");
ALTER TABLE "booking_period" ADD CONSTRAINT "booking_period_fk0" FOREIGN KEY ("apartment_id") REFERENCES "apartment"("id");
