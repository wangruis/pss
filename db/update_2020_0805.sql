DROP TABLE IF EXISTS `temp_comp_a`;
CREATE TABLE `temp_comp_a` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` bigint(20) DEFAULT NULL,
  `order_score` decimal(12,2) DEFAULT NULL,
  `stu_card_num` bigint(20) DEFAULT NULL,
  `goods_id` varchar(30) DEFAULT NULL,
  `goods_num` int(11) DEFAULT NULL,
  `goods_score` decimal(12,2) DEFAULT NULL,
  `goods_total_score` decimal(12,2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4096 DEFAULT CHARSET=utf8;

delimiter $$
DROP PROCEDURE IF EXISTS estnum;
create procedure estnum()
  begin
		-- 清空临时表
		truncate table temp_comp_a;
		-- 提取数据写入临时表  --12个月内的销售数据
		insert into temp_comp_a(order_no,create_date,order_score,stu_card_num,
														goods_id,goods_num,goods_score,goods_total_score)
		select a.id as order_no,date(a.create_date),a.score as order_score,
					 a.student_card_number as stu_card_num,
					 d.`code` as goods_id,d.number as goods_num,d.score as goods_score,
					 d.number * d.score  as goods_total_score
		from consume_history a
		join consume_goods b  on a.id = b.consume_his_id
		join goods_stock_detailed d on  b.goods_id = d.id
		and a.create_date>= DATE_ADD(now(),INTERVAL -12 MONTH)  and d.`option` = 'out_stock' ;
	end$$
delimiter ;

DROP event if exists estnum_event;
CREATE EVENT IF NOT EXISTS estnum_event
    ON SCHEDULE EVERY 1 DAY STARTS DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 1 HOUR)
    ON COMPLETION PRESERVE ENABLE
    DO call estnum();
