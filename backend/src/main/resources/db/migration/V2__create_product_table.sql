CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         code VARCHAR(50) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         category_id BIGINT NOT NULL,
                         FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);