-- test user
INSERT INTO by_user (id, name, password, valid, user_authority)
VALUES (1, 'tom', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1, 'ROLE_ADMIN');
INSERT INTO by_user (id, name, password, valid, user_authority)
VALUES (2, 'mary', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1, 'ROLE_SHOP');
INSERT INTO by_user (id, name, password, valid, user_authority)
VALUES (3, 'peter', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1, 'ROLE_ADMIN');

INSERT INTO by_floor (id, name) VALUES (1, '第1层');
INSERT INTO by_floor (id, name) VALUES (2, '第2层');
INSERT INTO by_floor (id, name) VALUES (3, '第3层');
INSERT INTO by_floor (id, name) VALUES (4, '第4层');
INSERT INTO by_floor (id, name) VALUES (5, '第5层');
INSERT INTO by_floor (id, name) VALUES (6, '第6层');

INSERT INTO by_shopping_mall (id, name) VALUES (1, 'nx');
INSERT INTO by_shopping_mall (id, name) VALUES (2, 'jq');

-- test menu_category
INSERT INTO by_category (id, name, type) VALUES (1, 'icon-two', 'm');
INSERT INTO by_category (id, name, type) VALUES (2, 'icon-three', 'm');
INSERT INTO by_category (id, name, type) VALUES (3, 'icon-four', 'm');
INSERT INTO by_category (id, name, type) VALUES (4, 'icon-five', 'm');
INSERT INTO by_category (id, name, type) VALUES (5, 'icon-six', 'm');
INSERT INTO by_category (id, name, type) VALUES (6, 'icon-seven', 'm');
INSERT INTO by_category (id, name, type) VALUES (7, 'icon-seven', 'm');
INSERT INTO by_category (id, name, type) VALUES (8, 'icon-eight', 'm');
INSERT INTO by_category (id, name, type) VALUES (9, '交易规则', 'r'); -- trading
INSERT INTO by_category (id, name, type) VALUES (10, '注册规则', 'r'); -- register
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (11, 'shop_category11', 's', NULL, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (12, 'shop_category12', 's', NULL, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (13, 'shop_category13', 's', NULL, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (14, 'shop_category14', 's', NULL, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (15, 'shop_category11-1', 's', 11, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (16, 'shop_category11-2', 's', 11, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (17, 'shop_category11-3', 's', 11, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (18, 'shop_category11-4', 's', 12, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (19, 'shop_category11-5', 's', 12, '2016-01-03', 1);
INSERT INTO by_category (id, name, type, parent_id, created_time, valid)
VALUES (20, 'shop_category11-6', 's', 12, '2016-01-03', 1);

-- test menu
INSERT INTO by_menu (id, name, category_id, href) VALUES (1, '会员卡管理', 1, '/admin/cards');
INSERT INTO by_menu (id, name, category_id, href) VALUES (2, '会员管理', 1, '/admin/members');
INSERT INTO by_menu (id, name, category_id, href) VALUES (3, '会员黑名单管理', 1, '/admin/blackList');
INSERT INTO by_menu (id, name, category_id, href) VALUES (4, '会员卡积分规则', 2, '/admin/cardRules');
INSERT INTO by_menu (id, name, category_id, href) VALUES (5, '店铺积分规则', 2, '/admin/shopRules');
INSERT INTO by_menu (id, name, category_id, href) VALUES (6, '人工积分', 2, '/admin/manual/add');
INSERT INTO by_menu (id, name, category_id, href) VALUES (7, '退货', 2, '/admin/manual/minus');
INSERT INTO by_menu (id, name, category_id, href) VALUES (8, '礼品券兑换', 2, '/admin/giftCouponMember');
INSERT INTO by_menu (id, name, category_id, href) VALUES (9, '停车券', 3, '/admin/parkingCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (10, '礼品券', 3, '/admin/giftCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (11, '店铺券', 3, '/admin/shopCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (12, '店铺管理', 4, '/admin/shops');
INSERT INTO by_menu (id, name, category_id, href) VALUES (13, '注册协议', 5, '/admin/helps/1');
INSERT INTO by_menu (id, name, category_id, href) VALUES (14, '会员卡使用帮助说明', 5, '/admin/helps/2');
INSERT INTO by_menu (id, name, category_id, href) VALUES (15, '会员卡积分规则说明', 5, '/admin/helps/3');
INSERT INTO by_menu (id, name, category_id, href) VALUES (16, '权限管理', 6, '/admin/authorities');
INSERT INTO by_menu (id, name, category_id, href) VALUES (17, '用户管理', 6, '/admin/users');
INSERT INTO by_menu (id, name, category_id, href) VALUES (18, '账户设置', 6, '/admin/password');
INSERT INTO by_menu (id, name, category_id, href) VALUES (19, '会员报表', 8, '/admin/memberReport');
INSERT INTO by_menu (id, name, category_id, href) VALUES (20, '交易报表', 8, '/admin/tradingReport');
INSERT INTO by_menu (id, name, category_id, href) VALUES (21, '停车报表', 8, '/admin/parkingCouponReport');
INSERT INTO by_menu (id, name, category_id, href) VALUES (22, '店铺券报表', 8, '/admin/shopCouponReport');
INSERT INTO by_menu (id, name, category_id, href) VALUES (23, '礼品券报表', 8, '/admin/giftCouponReport');
INSERT INTO by_menu (id, name, category_id, href) VALUES (24, '业态管理', 6, '/admin/shopCategories');
INSERT INTO by_menu (id, name, category_id, href) VALUES (25, '金桥滚动条管理', 6, '/admin/jq/scrollBar');
INSERT INTO by_menu (id, name, category_id, href) VALUES (26, '南翔滚动条管理', 6, '/admin/nx/scrollBar');
INSERT INTO by_menu (id, name, category_id, href) VALUES (27, '南翔活动管理', 6, '/admin/nx/activity');
INSERT INTO by_menu (id, name, category_id, href) VALUES (28, '金桥活动管理', 6, '/admin/jq/activity');
INSERT INTO by_menu (id, name, category_id, href) VALUES (29, '南翔电影海报管理', 6, '/admin/nx/films');
INSERT INTO by_menu (id, name, category_id, href) VALUES (30, '金桥电影海报管理', 6, '/admin/jq/films');

-- test authority
INSERT INTO by_authority (id, name, valid) VALUES (1, '管理员', 1);
INSERT INTO by_authority (id, name, valid) VALUES (2, '普通用户', 1);
INSERT INTO by_authority (id, name, valid) VALUES (3, '门店管理员', 1);
INSERT INTO by_authority (id, name, valid) VALUES (4, '测试角色1', 1);
INSERT INTO by_authority (id, name, valid) VALUES (5, '测试角色2', 1);

-- test user_auth
INSERT INTO by_user_auth (user_id, auth_id) VALUES (1, 1);
-- mary role shop
INSERT INTO by_user_auth (user_id, auth_id) VALUES (2, 2);
-- peter role 
INSERT INTO by_user_auth (user_id, auth_id) VALUES (3, 4);

-- test auth_menu
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 2);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 3);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 4);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 5);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 6);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 7);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 8);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 9);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 10);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 11);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 12);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 13);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 14);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 15);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 16);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 17);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 18);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 19);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 20);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 21);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 22);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 23);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 24);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 25);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 26);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 27);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 28);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 29);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 30);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 2);

INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (5, 6);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (4, 7);
-- test license
INSERT INTO by_license (id, name) VALUES (1, '沪A54321');
INSERT INTO by_license (id, name) VALUES (2, '沪A14321');

-- test card
INSERT INTO by_card (id, name, valid, init_score, img_href) VALUES (1, 'low1', 1, 100, '/img/1.png');
INSERT INTO by_card (id, name, valid, init_score, img_href) VALUES (3, 'low3', 1, 100, '/img/1.png');
INSERT INTO by_card (id, name, valid, init_score, img_href) VALUES (4, 'low4', 1, 100, '/img/1.png');
INSERT INTO by_card (id, name, valid, init_score, img_href) VALUES (5, 'low5', 1, 100, '/img/1.png');
INSERT INTO by_card (id, name, valid, init_score, img_href) VALUES (2, 'low2', 0, 100, '/img/1.png');

-- test member_detail
INSERT INTO by_member_detail (id, real_name, address, birthday, password) VALUES
  (1, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (2, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (3, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (4, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (5, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (6, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (7, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (8, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (9, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (10, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (11, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (12, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (13, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');
INSERT INTO by_member_detail (id, real_name, address, birthday) VALUES (14, '张三', '上海市长宁区万航渡路2170号A1室', '1990-01-01');

-- test member
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (1, '13611738422', 1, 150, 1, 10, '2012-12-12', 1);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (2, '13811738421', 1, 200, 0, 10, '2013-12-12', 2);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (3, '13811738423', 1, 200, 1, 10, '2014-12-12', 3);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (4, '13811738424', 1, 200, 1, 10, '2015-12-12', 4);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (5, '13811738425', 1, 200, 1, 10, '2016-03-28', 5);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (6, '13811738426', 1, 200, 1, 10, '2011-12-12', 6);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (7, '13811738427', 1, 200, 1, 10, '2002-12-12', 7);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (8, '13811738428', 1, 200, 1, 10, '2002-12-12', 8);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (9, '13811738429', 1, 200, 1, 10, '2012-12-12', 9);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (10, '13811738410', 1, 200, 1, 10, '2012-12-12', 10);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (11, '13811738412', 1, 200, 1, 10, '2012-12-12', 11);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (12, '13811738432', 1, 200, 1, 10, '2012-12-12', 12);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (13, '13811738442', 1, 200, 1, 10, '2012-12-12', 13);
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time, detail_id)
VALUES (14, '13811738452', 1, 200, 1, 10, '2012-12-12', 14);

-- test pay
INSERT INTO by_pay (id, member_id, created_time, type, license, amount, parkingPayType)
VALUES (1, 1, '2015-11-25 13:14:22', 'p', '沪A54321', 15, 0);

-- test category

-- test card rule
-- 注册用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (1, 2.0, 1, 10, 1, 100, 'rule1', 'c', '2015-12-01 00:00:00', '2015-12-30 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (2, 2.0, 1, 10, 1, 100, 'rule2', 'c', '2015-12-01', '2015-12-30 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (7, 2.0, 1, 10, 1, 100, 'rule7', 'c', '2016-12-01', '2016-12-30 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (30, 2.0, 5, 10, 1, 100, 'rule30', 'c', '2016-1-1 00:00:00', '2016-12-30 23:59:59');

-- 交易用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (3, 2.0, 1, 9, 1, 100, 'rule3', 'c', '2015-12-01', '2015-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (4, 2.0, 1, 9, 1, 100, 'rule4', 'c', '2015-12-01', '2015-12-7 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (5, 2.0, 1, 9, 1, 100, 'rule5', 'c', '2016-12-10', '2016-12-20 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (6, 2.0, 1, 9, 1, 100, 'rule6', 'c', NULL, NULL);
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (17, 2.0, 1, 9, 1, 100, 'rule17', 'c', '2015-12-01', '2016-1-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (8, 2.0, 1, 9, 1, 100, 'rule8', 'c', '2015-12-01', '2016-1-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (9, 2.0, 1, 9, 1, 100, 'rule9', 'c', '2015-12-01', '2016-2-21 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (10, 2.0, 1, 9, 1, 100, 'rule10', 'c', '2015-12-01', '2016-10-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (11, 2.0, 1, 9, 1, 100, 'rule11', 'c', '2016-10-01', '2016-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (12, 2.0, 1, 9, 1, 100, 'rule12', 'c', '2016-12-01', '2016-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (13, 2.0, 1, 9, 1, 100, 'rule13', 'c', '2016-12-01', '2016-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (14, 2.0, 1, 9, 1, 100, 'rule14', 'c', '2016-12-01', '2016-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (15, 2.0, 1, 9, 1, 100, 'rule15', 'c', '2016-12-01', '2016-12-31 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (16, 2.0, 1, 9, 1, 100, 'rule16', 'c', '2015-12-01', '2016-12-31 23:59:59');

-- test shop
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (1, 'shop1', 'abc1', 2, '2012-01-01', 11, 15);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (2, '光明', 'abc2', NULL, '2012-01-01', 12, 18);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (3, '哈根达斯', 'abc3', NULL, '2012-01-01', 11, 15);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (4, 'h&m', 'abc4', NULL, '2012-01-01', 12, 18);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (5, '索尼', 'abc5', NULL, '2012-01-01', 11, 15);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (6, '三星', 'abc6', NULL, '2012-01-01', 12, 18);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (7, 'java', 'abc7', NULL, '2012-01-01', 11, 15);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (8, 'scala', 'abc8', NULL, '2012-01-01', 12, 18);
INSERT INTO by_shop (id, name, shop_key, user_id, updated_time, first_category_id, second_category_id)
VALUES (9, 'csharp', 'abc9', NULL, '2012-01-01', 11, 15);

-- test shop_rule
INSERT INTO by_rule (id, rate, score, valid, beginTime, endTime, name, type)
VALUES (20, 1, 100, 1, '2016-1-6', '2016-3-18', 'shoprule20', 's');
INSERT INTO by_rule (id, rate, score, valid, beginTime, endTime, name, type)
VALUES (18, 1, 100, 1, '2016-1-6', '2016-3-18', 'shoprule18', 's');
INSERT INTO by_rule (id, rate, score, valid, beginTime, endTime, name, type)
VALUES (19, 1, 100, 1, '2016-1-6', '2016-3-18', 'shoprule19', 's');

-- test shop_menu
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 1);

-- test score add
INSERT INTO by_score_add_history (id, member_id, total, reason, created_time) VALUES (1, 1, 50, 'h', '2016-1-12');
INSERT INTO by_score_add_history (id, member_id, total, reason, created_time) VALUES (2, 1, 40, 'h', '2016-1-13');
INSERT INTO by_score_add_history (id, member_id, total, reason, created_time) VALUES (3, 1, 30, 'h', '2016-1-14');
INSERT INTO by_score_add_history (id, member_id, total, reason, created_time) VALUES (4, 1, 20, 'h', '2016-1-15');
INSERT INTO by_score_add_history (id, member_id, total, reason, created_time) VALUES (5, 1, 10, 'h', '2016-1-16');

-- test by_trading
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (1, 1, 'a', 100, 1, '2015-12-25 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (2, 1, 'b', 100, 2, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (3, 1, 'c', 100, 3, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (4, 1, 'd', 100, 4, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (5, 1, 'e', 100, 5, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (6, 1, 'f', 100, 6, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (7, 1, 'g', 100, 7, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (8, 1, 'h', 100, 8, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (9, 1, 'i', 100, 9, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (10, 1, 'j', 100, 1, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (11, 1, 'k', 100, 2, '2015-12-26 12:34:22');
INSERT INTO by_trading (id, member_id, code, amount, shop_id, created_time)
VALUES (12, 1, 'l', 100, 3, '2015-12-26 12:34:22');

-- test coupon
-- valid duplicate parking coupon id  1
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (1, 'p', '2015-12-1', '2016-12-30', 2, '2016-12-31', 1, 100, 1, 20, 'parkingCoupon1');
-- invalid parking coupon id is 2
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (2, 'p', '2014-12-1', '2016-12-30', 50, '2015-1-3', 0, 100, 0, 20, 'parkingCoupon2');
-- valid parking coupon id is
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (25, 'p', '2014-12-1', '2016-12-30', 50, '2015-1-3', 1, 100, 0, 20, 'parkingCoupon25');
-- valid duplicate gift coupon id  3
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (3, 'g', '2015-12-1', '2016-12-30', 1, '2016-1-2', 1, 100, 1, 20, 'giftCoupon3');
-- valid not duplicate gift coupon id 4
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (4, 'g', '2015-12-1', '2016-12-30', 5, '2016-1-4', 1, 100, 0, 20, 'giftCoupon4');
-- invalid giftCoupon
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (8, 'g', '2015-12-1', '2016-12-30', 100, '2016-1-2', 0, 100, 1, 20, 'giftCoupon8');
-- valid duplicate shop coupon id 5
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (5, 's', '2015-12-1', '2016-12-30', 1, '2016-1-5', 1, 100, 1, 20, 'shopCoupon5', 1);
-- valid not duplicate shop coupon id 6
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (6, 's', '2015-12-1', '2016-12-30', 5, '2016-1-5', 1, 100, 0, 20, 'shopCoupon6', 1);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (9, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon9');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (10, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon10');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (11, 's', '2015-12-1', '2016-12-30', 1000, '2016-1-5', 1, 100, 1, 20, 'shopCoupon11', 1);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (12, 's', '2015-12-1', '2016-12-30', 10, '2016-1-5', 1, 100, 1, 20, 'shopCoupon12', 1);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (13, 's', '2015-12-1', '2016-12-30', 10, '2016-1-5', 1, 100, 1, 20, 'shopCoupon13', 1);

INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (14, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon14');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (15, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon15');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (16, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon16');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (17, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon17');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (18, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon18');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (19, 'g', '2016-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon19');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (20, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon20');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (21, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon21');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (22, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon22');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (23, 'g', '2015-12-1', '2016-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon23');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (24, 'g', '2015-12-1', '2015-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'giftCoupon24');
INSERT INTO by_coupon (id, type, score, valid, total, duplicate, amount, name, shop_id)
VALUES (27, 's', 5, 1, 100, 0, 20, 'shopCoupon2016', 1);
INSERT INTO by_coupon (id, type, score, valid, total, duplicate, amount, name, shop_id)
VALUES (26, 'g', 5, 1, 100, 0, 20, 'giftCoupon2016', 1);

-- test giftCoupon

-- test shop coupon

-- test score_history
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (1, 1, '2012-12-1 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (2, 1, '2012-12-2 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (3, 1, '2012-12-4 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (4, 1, '2012-12-5 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (5, 1, '2012-12-6 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (6, 1, '2012-12-7 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (7, 1, '2012-12-8 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (8, 1, '2012-12-9 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (9, 1, '2012-12-10 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (10, 1, '2012-12-11 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (11, 1, '2012-12-6 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (12, 1, '2012-12-7 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (13, 1, '2012-12-8 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (14, 1, '2012-12-9 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (15, 1, '2012-12-6 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (16, 1, '2012-12-7 12:22:55', 10, 'hehe');

INSERT INTO by_sequence (id) VALUES (1001);

-- test by_member_license
INSERT INTO by_member_license (member_id, license_id) VALUES (1, 1);
INSERT INTO by_member_license (member_id, license_id) VALUES (1, 2);

-- test help
INSERT INTO by_content (id, summary, type) VALUES (1, 'reg help', 'h');
INSERT INTO by_member_help (content_id, title, id) VALUES (1, '注册协议', 1);
INSERT INTO by_content (id, summary, type) VALUES (2, 'card help', 'h');
INSERT INTO by_member_help (content_id, title, id) VALUES (2, '会员卡使用帮助', 2);
INSERT INTO by_content (id, summary, type) VALUES (3, 'rule help', 'h');
INSERT INTO by_member_help (content_id, title, id) VALUES (3, '会员卡积分规则', 3);

-- test shop rule
INSERT INTO by_shop_rule (shop_id, rule_id) VALUES (1, 7);
INSERT INTO by_shop_rule (shop_id, rule_id) VALUES (2, 7);
INSERT INTO by_shop_rule (shop_id, rule_id) VALUES (3, 7);

-- 停车券兑换历史
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2016-1-1 12:22:41', 2);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2016-1-2 11:22:54', 3);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2016-1-3 7:23:22', 4);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 8:20:23', 2);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2016-1-5 14:22:51', 1);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 15:31:31', 1);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 19:22:22', 6);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 18:15:22', 6);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 22:22:13', 2);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 10:45:34', 4);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 14:22:54', 1);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 16:17:19', 6);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 23:22:51', 5);
INSERT INTO by_parking_coupon_exchange_history (member_id, coupon_id, created_time, total)
VALUES (1, 1, '2015-12-4 16:22:31', 3);

-- parking coupon use history
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-2 12:22:40', 1);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-3 11:22:20', 2);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 13:22:34', 3);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 15:21:44', 1);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 12:22:44', 10);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 12:45:12', 11);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 15:55:21', 1);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 17:22:22', 12);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 15:22:22', 1);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 15:22:11', 12);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 18:23:22', 1);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 19:22:20', 10);
INSERT INTO by_parking_coupon_use_history (member_id, parking_coupon_id, created_time, total)
VALUES (1, 1, '2016-1-4 20:22:22', 19);

-- parking history
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-1 12:41:30', '2015-1-1 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-2 12:41:30', '2015-1-2 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-3 12:41:30', '2015-1-3 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-4 12:41:30', '2015-1-5 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-5 12:41:30', '2015-1-5 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-6 12:41:30', '2015-1-6 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-7 12:41:30', '2015-1-7 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-8 12:41:30', '2015-1-8 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-9 12:41:30', '2015-1-9 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-10 12:41:30', '2015-1-10 13:44:22');
INSERT INTO by_parking_history (license, member_id, start_time, end_time)
VALUES ('沪A123', 1, '2015-1-11 12:41:30', '2015-1-11 13:44:22');

-- parking coupon member
INSERT INTO by_parking_coupon_member (member_id, coupon_id, total) VALUES (1, 1, 2);
INSERT INTO by_parking_coupon_member (member_id, coupon_id, total) VALUES (1, 2, 2);

-- gift coupon member
INSERT INTO by_gift_coupon_member (id, member_id, coupon_id, exchanged_time, code) VALUES (1, 1, 4, '2016-2-11', '123');
INSERT INTO by_gift_coupon_member (id, member_id, coupon_id, exchanged_time, code) VALUES (2, 1, 8, '2016-2-11', '456');
INSERT INTO by_gift_coupon_member (id, member_id, coupon_id, exchanged_time, code) VALUES (3, 1, 4, '2016-2-11', '789');


INSERT INTO by_shop_coupon_member (id, member_id, coupon_id, exchanged_time, used_time, code)
VALUES (1, 1, 5, '2015-12-21', NULL, 'asdf1');
INSERT INTO by_shop_coupon_member (id, member_id, coupon_id, exchanged_time, used_time, code)
VALUES (2, 1, 5, '2015-12-21', NULL, 'asdf2');
INSERT INTO by_shop_coupon_member (id, member_id, coupon_id, exchanged_time, used_time, code)
VALUES (3, 1, 5, '2015-12-21', NULL, 'asdf3');

-- test report data
INSERT INTO by_batch_member_inc (number, created_time) VALUES (1, '2016-1-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (2, '2016-1-2');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (3, '2016-2-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (4, '2016-2-2');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (8, '2016-3-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (9, '2016-3-2');

INSERT INTO by_batch_member_inc (number, created_time) VALUES (1, '2017-1-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (2, '2017-1-2');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (3, '2017-2-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (4, '2017-2-2');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (8, '2017-3-1');
INSERT INTO by_batch_member_inc (number, created_time) VALUES (9, '2017-3-2');

-- test trading data
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (3, 4, '2016-1-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (6, 4, '2016-1-2');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (2, 4, '2016-2-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (5, 11, '2016-2-2');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (6, 5, '2016-3-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (4, 7, '2016-3-2');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (3, 4, '2017-1-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (6, 4, '2017-1-2');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (2, 4, '2017-2-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (5, 11, '2017-2-2');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (6, 5, '2017-3-1');
INSERT INTO by_batch_trading_inc (number, amount, created_time) VALUES (4, 7, '2017-3-2');

INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (7, 2, '2016-1-1');
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (6, 3, '2016-1-2');
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (5, 4, '2016-2-1');
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (4, 5, '2016-2-2');
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (3, 6, '2016-3-1');
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time) VALUES (2, 7, '2016-3-2');

INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (7, 2, {d '2017-01-01'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (6, 3, {d '2017-01-02'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (5, 4, {d '2017-02-01'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (4, 5, {d '2017-02-02'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (3, 6, {d '2017-03-01'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (2, 7, {d '2017-03-02'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (3, 9, {d '2017-04-01'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (2, 8, {d '2017-04-02'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (5, 10, {d '2017-05-01'});
INSERT INTO by_batch_parking_coupon_info_per_day (use_number, exchange_number, created_time)
VALUES (6, 11, {d '2017-05-02'});

INSERT INTO by_content (id, summary,type) VALUES (4, 'a','f');
INSERT INTO by_content (id, summary,type) VALUES (5, 'b','f');
INSERT INTO by_content (id, summary,type) VALUES (6, 'c','f');
INSERT INTO by_content (id, summary,type) VALUES (7, 'd','f');

INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (5, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu1.com', 1, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (6, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu1.com', 1, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (7, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu1.com', 1, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (8, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu1.com', 1, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (1, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu.com', 2, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (2, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu.com', 2, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (3, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu.com', 2, 's');
INSERT INTO by_adv (id, coverImg, href, mall_id, type) VALUES
  (4, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 'http://www.baidu.com', 2, 's');

INSERT INTO by_adv (id, coverImg, mall_id, type, content_id)
VALUES (9, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 1, 'f', 4);
INSERT INTO by_adv (id, coverImg, mall_id, type, content_id)
VALUES (10, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 1, 'f', 5);
INSERT INTO by_adv (id, coverImg, mall_id, type, content_id)
VALUES (11, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 1, 'f', 6);
INSERT INTO by_adv (id, coverImg, mall_id, type, content_id)
VALUES (12, 'http://cchdev.b0.upaiyun.com//2016/0/86b956fa-2f9f-4a42-bc2c-3cfba25d25fc.jpg', 1, 'f', 7);

INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (1, 'activity1', '2012-12-12 12:23:22', '2012-12-30 15:22:23', '2012-11-11 18:18:12', '2012-11-30 19:19:19', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (2, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (3, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (4, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (5, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (6, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (7, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (8, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (9, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (10, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (11, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (12, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (13, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
INSERT INTO by_activity (id, name, start_time, end_time, enroll_begin_time, enroll_end_time, content, mall_id)
VALUES (14, 'activity1', '2012-12-12', '2012-12-30', '2012-11-11', '2012-11-30', 'ahsdf', 1);
