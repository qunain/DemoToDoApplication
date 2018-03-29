#DROP KEYSPACE IF EXISTS emirates_todoapplication;

CREATE KEYSPACE  emirates_todoapplication WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

DROP TABLE IF EXISTS emirates_todoapplication.tasks;

CREATE TABLE IF NOT EXISTS emirates_todoapplication.tasks (
id uuid ,
task_name text,
task_status text,
primary key (id)
);


