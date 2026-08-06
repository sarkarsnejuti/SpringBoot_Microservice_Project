CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` integer AUTO_INCREMENT  PRIMARY KEY,
  `name` varchar2(100) NOT NULL,
  `email` varchar2(100) NOT NULL,
  `mobile_number` varchar2(20) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar2(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
    `updated_by` varchar2(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
  `customer_id` integer NOT NULL,
   `account_number` integer AUTO_INCREMENT  PRIMARY KEY,
  `account_type` varchar2(100) NOT NULL,
  `branch_address` varchar2(200) NOT NULL,
  `created_at` date NOT NULL,
   `created_by` varchar2(20) NOT NULL,
   `updated_at` date DEFAULT NULL,
    `updated_by` varchar2(20) DEFAULT NULL
);