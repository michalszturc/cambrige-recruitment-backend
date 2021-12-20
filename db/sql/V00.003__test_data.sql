begin transaction;

INSERT INTO shop.products ("name",min_quantity,max_quantity,unit_price,created_at) VALUES
	 ('Product X',1,10,10,'2021-12-19 14:41:31.862'),
	 ('Product Y',5,15,20,'2021-12-19 14:41:31.862');

INSERT INTO shop.promo_codes (code,created_at) VALUES
	 ('xxx','2021-12-19 14:43:27.189'),
	 ('12345678','2021-12-19 14:43:27.189'),
	 ('XOpZs','2021-12-19 14:43:27.189'),
	 ('ipOOzqw','2021-12-19 14:43:27.189'),
	 ('7888s','2021-12-19 14:43:27.189'),
	 ('XOpZsW','2021-12-19 14:43:27.189'),
	 ('1','2021-12-19 14:43:27.189'),
	 ('12','2021-12-19 14:43:27.189'),
	 ('123','2021-12-19 14:43:27.189');

commit;