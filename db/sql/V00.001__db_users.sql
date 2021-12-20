begin transaction;

create user dbadmin encrypted password 'dbadmin';

create schema shop authorization dbadmin;

create user dbuser encrypted password 'dbuser';

grant usage on schema shop to dbuser;

commit;
