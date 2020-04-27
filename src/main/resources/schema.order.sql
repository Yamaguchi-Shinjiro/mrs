DROP TABLE IF EXISTS order_data CASCADE;
DROP TABLE IF EXISTS order_detail CASCADE;
DROP TABLE IF EXISTS product CASCADE;

CREATE TABLE IF NOT EXISTS order_data (
  order_no VARCHAR(30) NOT NULL,
  order_date TIMESTAMP NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (order_no)
);
CREATE TABLE IF NOT EXISTS order_detail (
  order_no VARCHAR(30) NOT NULL,
  line_no INT4 NOT NULL,
  product_id VARCHAR(255) NOT NULL,
  amount INT4 NOT NULL,
  purchase_price INT4 NOT NULL,
  PRIMARY KEY (order_no, line_no)
);
CREATE TABLE IF NOT EXISTS product (
  product_id VARCHAR(255) NOT NULL,
  product_name VARCHAR(255) NOT NULL,
  brand_name VARCHAR(255) NOT NULL,
  price INT4 NOT NULL,
  in_stock BOOL NOT NULL,
  PRIMARY KEY (product_id)
);
