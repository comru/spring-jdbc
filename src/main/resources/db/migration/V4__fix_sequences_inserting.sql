SELECT setval('owners_id_seq', (select max(id) from owners), true);
SELECT setval('pets_id_seq', (select max(id) from pets), true);
SELECT setval('types_id_seq', (select max(id) from types), true);