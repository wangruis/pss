alter table user add column id_card varchar(100) DEFAULT NULL;

alter table goods_stock_detailed add column create_by varchar(50) DEFAULT NULL;

INSERT INTO `role`(`name`, `nameZh`) VALUES ('ROLE_student', '学生销售');