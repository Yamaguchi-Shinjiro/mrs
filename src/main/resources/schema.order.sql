DROP TABLE IF EXISTS order_ CASCADE;
DROP TABLE IF EXISTS order_line CASCADE;
DROP TABLE IF EXISTS product CASCADE;

CREATE TABLE IF NOT EXISTS order_data (
  order_id SERIAL NOT NULL,
  order_date DATE NOT NULL,
  user_id INT4 NOT NULL,
  PRIMARY KEY (order_id)
);
CREATE TABLE IF NOT EXISTS order_detail (
  order_id INT4 NOT NULL,
  line_no INT4 NOT NULL,
  product_id INT4 NOT NULL,
  amount INT4 NOT NULL,
  purchase_price INT4 NOT NULL,
  PRIMARY KEY (order_id, line_no)
);
CREATE TABLE IF NOT EXISTS product (
  product_id VARCHAR(255) NOT NULL,
  product_name VARCHAR(255) NOT NULL,
  brand_name VARCHAR(255) NOT NULL,
  price INT4 NOT NULL,
  in_stock BOOL NOT NULL,
  PRIMARY KEY (product_id)
);
