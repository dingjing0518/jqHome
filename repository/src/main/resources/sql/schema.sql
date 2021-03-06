CREATE TABLE by_shopping_mall (
  id   INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_content (
  id      INT NOT NULL AUTO_INCREMENT,
  summary VARCHAR(5000),
  type    CHAR(1),
  PRIMARY KEY (id)
);

CREATE TABLE by_adv (
  id               INT       NOT NULL AUTO_INCREMENT,
  coverImg         CHAR(77),
  href             VARCHAR(70),
  type             CHAR(1),
  created_by       VARCHAR(20),
  created_time     TIMESTAMP NULL,
  updated_by       VARCHAR(20),
  updated_time     TIMESTAMP NULL,
  mall_id          INT,
  content_id       INT,
  description      VARCHAR(200),
  is_show_on_index SMALLINT,
  PRIMARY KEY (id),
  FOREIGN KEY (mall_id) REFERENCES by_shopping_mall (id),
  FOREIGN KEY (content_id) REFERENCES by_content (id)
);

CREATE TABLE by_category (
  id           INT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(20),
  type         CHAR(1),
  parent_id    INT,
  valid        SMALLINT,
  created_by   VARCHAR(20),
  created_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  updated_time TIMESTAMP NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (parent_id) REFERENCES by_category (id)
);


CREATE TABLE by_floor (
  id   INT NOT NULL AUTO_INCREMENT,
  name CHAR(3),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_menu (
  id          INT NOT NULL AUTO_INCREMENT,
  name        VARCHAR(20),
  category_id INT,
  href        VARCHAR(50),
  FOREIGN KEY (category_id) REFERENCES by_category (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_license (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_user (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  name           VARCHAR(20),
  password       CHAR(64),
  valid          SMALLINT           DEFAULT 1,
  user_authority VARCHAR(10),
  created_time   TIMESTAMP NULL,
  updated_time   TIMESTAMP NULL,
  updated_by     VARCHAR(20),
  created_by     VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_shop (
  id                 INT       NOT NULL AUTO_INCREMENT,
  name               VARCHAR(225),
  user_id            BIGINT,
  created_by         VARCHAR(20),
  updated_by         VARCHAR(20),
  created_time       TIMESTAMP NULL,
  updated_time       TIMESTAMP NULL,
  shop_key           VARCHAR(225),
  img_href           VARCHAR(77),
  first_category_id  INT,
  second_category_id INT,
  floor_id           INT,
  address            VARCHAR(10),
  business_hour 	 VARCHAR(50),
  phone              VARCHAR(12),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  FOREIGN KEY (first_category_id) REFERENCES by_category (id),
  FOREIGN KEY (second_category_id) REFERENCES by_category (id),
  FOREIGN KEY (floor_id) REFERENCES by_floor (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_authority (
  id           INT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(30),
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  valid        SMALLINT           DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_user_auth (
  user_id BIGINT NOT NULL,
  auth_id INT    NOT NULL,
  PRIMARY KEY (user_id, auth_id),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  FOREIGN KEY (auth_id) REFERENCES by_authority (id)
);

CREATE TABLE IF NOT EXISTS by_auth_menu (
  auth_id INT,
  menu_id INT,
  FOREIGN KEY (auth_id) REFERENCES by_authority (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (auth_id, menu_id)
);


CREATE TABLE IF NOT EXISTS by_card (
  id           INT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(50),
  valid        SMALLINT           DEFAULT 1,
  img_href     VARCHAR(77),
  init_score   INT                DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  summary      VARCHAR(500),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_card_aud (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  name         VARCHAR(50),
  valid        SMALLINT,
  img_href     CHAR(77),
  init_score   INT                DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  summary      VARCHAR(500),
  REV          INTEGER   NOT NULL,
  REVTYPE      TINYINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE IF NOT EXISTS by_rule_aud (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  rate         DOUBLE,
  score        INT                DEFAULT 0,
  card_id      INT,
  summary      VARCHAR(50),
  valid        SMALLINT           DEFAULT 1,
  beginTime    TIMESTAMP NULL,
  endTime      TIMESTAMP NULL,
  category_id  INT,
  name         VARCHAR(20),
  type         CHAR(1),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (category_id) REFERENCES by_category (id),
  REV          INTEGER   NOT NULL,
  REVTYPE      TINYINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE IF NOT EXISTS by_rule (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  rate         DOUBLE,
  score        INT             DEFAULT 0,
  card_id      INT,
  summary      VARCHAR(50),
  valid        SMALLINT        DEFAULT 1,
  beginTime    TIMESTAMP,
  endTime      TIMESTAMP,
  category_id  INT,
  name         VARCHAR(20),
  type         CHAR(1),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (category_id) REFERENCES by_category (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_shop_rule (
  shop_id INT,
  rule_id BIGINT,
  PRIMARY KEY (shop_id, rule_id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (rule_id) REFERENCES by_rule (id)
);

CREATE TABLE IF NOT EXISTS by_member_detail (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_member (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         CHAR(11) UNIQUE,
  card_id      INT,
  score        INT,
  sumScore     INT,
  created_time TIMESTAMP,
  valid        SMALLINT        DEFAULT 1,
  invalid_time TIMESTAMP,
  invalid_by   VARCHAR(20),
  updated_time TIMESTAMP,
  detail_id    BIGINT,
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (detail_id) REFERENCES by_member_detail (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_activity (
  id                INT NOT NULL AUTO_INCREMENT,
  name              VARCHAR(20),
  start_time        TIMESTAMP,
  end_time          TIMESTAMP,
  enroll_begin_time TIMESTAMP,
  enroll_end_time   TIMESTAMP,
  content_id        INT,
  mall_id           INT,
  cover_img 		CHAR(77),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  is_show_on_index smallint,
  PRIMARY KEY (id),
  FOREIGN KEY (mall_id) REFERENCES by_shopping_mall (id),
  FOREIGN KEY (content_id) REFERENCES by_content (id)
);

CREATE TABLE by_activity_member (
  activity_id INT,
  member_id   BIGINT,
  PRIMARY KEY (activity_id, member_id),
  FOREIGN KEY (activity_id) REFERENCES by_activity (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id)
);

CREATE TABLE IF NOT EXISTS by_member_detail_aud (
  id           BIGINT  NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP,
  detail_id    BIGINT,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  REV          INTEGER NOT NULL,
  REVTYPE      TINYINT,
  FOREIGN KEY (detail_id) REFERENCES by_member (id),
  PRIMARY KEY (id, REV)
);

CREATE TABLE IF NOT EXISTS by_member_license (
  member_id  BIGINT NOT NULL,
  license_id BIGINT NOT NULL,
  PRIMARY KEY (member_id, license_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (license_id) REFERENCES by_license (id)
);

CREATE TABLE IF NOT EXISTS by_coupon (
  id              INT NOT NULL AUTO_INCREMENT,
  type            CHAR(1),
  name            VARCHAR(50),
  begin_time      TIMESTAMP,
  end_time        TIMESTAMP,
  created_time    TIMESTAMP,
  created_by      VARCHAR(20),
  updated_time    TIMESTAMP,
  updated_by      VARCHAR(20),
  score           INT          DEFAULT 0,
  coupon_end_time TIMESTAMP,
  valid           SMALLINT     DEFAULT 1,
  total           SMALLINT     DEFAULT 0,
  duplicate       SMALLINT     DEFAULT 1,
  amount          DOUBLE       DEFAULT 0,
  cover_img       CHAR(77),
  content_img     CHAR(77),
  logo            CHAR(77),
  summary         VARCHAR(255),
  shop_id         INT,
  sortOrder       SMALLINT     DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id)
);

CREATE TABLE IF NOT EXISTS by_gift_coupon_member (
  id             BIGINT AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      INT,
  code           CHAR(17),
  state          SMALLINT,
  exchanged_time TIMESTAMP,
  used_time      TIMESTAMP NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE IF NOT EXISTS by_parking_coupon_exchange_history (
  id           INT AUTO_INCREMENT,
  member_id    BIGINT,
  coupon_id    INT,
  created_time TIMESTAMP,
  created_by   VARCHAR(20),
  total        INT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE IF NOT EXISTS by_parking_coupon_use_history (
  id                INT AUTO_INCREMENT,
  member_id         BIGINT,
  parking_coupon_id INT,
  created_time      TIMESTAMP,
  total             INT,
  license           VARCHAR(10),
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE IF NOT EXISTS by_score_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  deposit      INT,
  reason       VARCHAR(20),
  type         SMALLINT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id)
);

CREATE TABLE IF NOT EXISTS REVINFO (
  REV      BIGINT NOT NULL AUTO_INCREMENT,
  REVTSTMP BIGINT,
  PRIMARY KEY (REV)
);

CREATE TABLE IF NOT EXISTS by_shop_menu (
  shop_id INT,
  menu_id INT,
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (shop_id, menu_id)
);

CREATE TABLE IF NOT EXISTS by_score_add_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  total        INT,
  reason       VARCHAR(20),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_parking_history (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  member_id  BIGINT,
  start_time TIMESTAMP,
  end_time   TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_parking_withnomember_history (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  start_time TIMESTAMP,
  end_time   TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_pay (
  id             BIGINT NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  created_time   TIMESTAMP,
  type           CHAR(1),
  license        VARCHAR(20),
  amount         INT,
  parkingPayType SMALLINT        DEFAULT 0,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_consumption_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  amount       DOUBLE,
  shop         VARCHAR(100),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_trading (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  shop_id      INT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  amount       DOUBLE,
  code         CHAR(10),
  score        INT,
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_sequence (
  id BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS by_shop_coupon_member (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      INT,
  exchanged_time TIMESTAMP,
  used_time      TIMESTAMP NULL,
  code           CHAR(17),
  trading_id     BIGINT,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id),
  FOREIGN KEY (trading_id) REFERENCES by_trading (id),
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS by_member_help (
  id           INT NOT NULL AUTO_INCREMENT,
  content_id   INT,
  title        VARCHAR(50),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  PRIMARY KEY (id),
  FOREIGN KEY (content_id) REFERENCES by_content (id)
);

CREATE TABLE IF NOT EXISTS by_parking_coupon_count (
  id        INT NOT NULL AUTO_INCREMENT,
  total     INT,
  member_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id)
);

CREATE TABLE IF NOT EXISTS by_parking_coupon_member (
  member_id BIGINT,
  coupon_id INT,
  total     INT,
  PRIMARY KEY (member_id, coupon_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_batch_member_inc (
  id           INT AUTO_INCREMENT,
  number       INT,
  created_time DATE,
  PRIMARY KEY (id)
);

CREATE TABLE by_batch_trading_inc (
  id           INT AUTO_INCREMENT,
  number       INT,
  amount       FLOAT,
  created_time DATE,
  PRIMARY KEY (id)
);

CREATE TABLE by_batch_parking_coupon_info_per_day (
  id              INT AUTO_INCREMENT,
  use_number      INT,
  exchange_number INT,
  created_time    DATE,
  PRIMARY KEY (id)
);

create table by_vehicle_entrance(
	id bigint not null auto_increment,
	parkid varchar(20),
	entrance varchar(20),
	rectype varchar(3),
	carnum varchar(10),
	cartype varchar(10),
	enter_or_leave_time timestamp,
	type varchar(10),
	color varchar(10),
	primary key(id)
);

create table by_wechart_response(
	id bigint not null auto_increment,
	outId int,
	openId char(32),
	nickname varchar(20),
	sex smallint,
	language varchar(20),
	city varchar(10),
	province varchar(10),
	country varchar(10),
	headimgurl varchar(120),
	accesstoken varchar(30),
	jwt varchar(150),
	mobilephone char(11),
	islogin char(32),
	oncestr char(36),
	primary key(id)
);

CREATE UNIQUE INDEX IF NOT EXISTS by_trading_code_unique ON by_trading (code);
CREATE UNIQUE INDEX IF NOT EXISTS by_member_name_unique ON by_member (name);
CREATE UNIQUE INDEX IF NOT EXISTS by_shop_shopKey_unique ON by_shop (shop_key);
CREATE UNIQUE INDEX IF NOT EXISTS by_gift_coupon_member_code_unique ON by_gift_coupon_member (code);
CREATE UNIQUE INDEX IF NOT EXISTS by_user_unique ON by_user (name);
CREATE UNIQUE INDEX IF NOT EXISTS by_rule_name_unique ON by_rule (name);
CREATE UNIQUE INDEX IF NOT EXISTS by_coupon_name_unique ON by_coupon (name);
CREATE UNIQUE INDEX IF NOT EXISTS by_batch_member_inc_created_time ON by_batch_member_inc (created_time);
CREATE UNIQUE INDEX IF NOT EXISTS by_batch_trading_inc_created_time ON by_batch_trading_inc (created_time);
CREATE UNIQUE INDEX IF NOT EXISTS by_batch_parking_coupon_info_per_day ON by_batch_parking_coupon_info_per_day (created_time);

CREATE INDEX IF NOT EXISTS by_trading_createdTime_index ON by_trading (created_time);
