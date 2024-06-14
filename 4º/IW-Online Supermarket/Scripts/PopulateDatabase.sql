INSERT INTO "public"."module" ("oid", "moduleid", "modulename") 
VALUES
(1, 'sv2', 'Users'),
(2, 'sv3', 'Admin');

INSERT INTO "public"."group" ("oid", "groupname", "module_oid") 
VALUES
(1, 'Users', 1),
(2, 'Admin', 2);

INSERT INTO "public"."user" ("oid", "email", "password", "username", "group_oid") 
VALUES
(1, 'user@example.com', 'user', 'user', 1),
(2, 'admin@example.com', 'admin', 'admin', 2);

INSERT INTO "public"."user_group" ("user_oid", "group_oid") 
VALUES
(1, 1),
(2, 2); 

INSERT INTO "public"."group_module" ("group_oid", "module_oid") 
VALUES
(1, 1),
(2, 2); 


INSERT INTO "public"."product" ("oid", "name", "description", "price", "stock") 
VALUES
(1, 'Laptop', 'Thin and light laptop with fast processor', 999.99, 50),
(2, 'Smartphone', 'High-performance smartphone with dual camera', 699.99, 100),
(3, 'Headphones', 'Wireless headphones with noise cancellation feature', 149.99, 75),
(4, 'Tablet', 'Large-screen tablet with long battery life', 449.99, 30),
(5, 'Smartwatch', 'Fitness tracker and smartwatch with heart rate monitor', 199.99, 60),
(6, 'Desktop Computer', 'Powerful desktop computer for gaming and productivity', 1499.99, 25),
(7, 'Bluetooth Speaker', 'Portable speaker with deep bass and long battery life', 79.99, 120),
(8, 'Gaming Console', 'Next-gen gaming console with 4K capabilities', 499.99, 40),
(9, 'External Hard Drive', 'High-capacity external hard drive for data storage', 129.99, 80),
(10, 'Wireless Router', 'Dual-band wireless router for high-speed internet', 89.99, 45),
(11, 'Printer', 'All-in-one printer with scanning and copying functions', 199.99, 55),
(12, 'Camera', 'Mirrorless camera with interchangeable lenses', 899.99, 35),
(13, 'Monitor', 'Ultra-wide monitor for immersive gaming and productivity', 599.99, 20),
(14, 'Keyboard', 'Mechanical gaming keyboard with customizable RGB lighting', 129.99, 65),
(15, 'Mouse', 'Ergonomic gaming mouse with adjustable DPI settings', 69.99, 90),
(16, 'Graphics Card', 'High-performance graphics card for gaming PCs', 699.99, 15),
(17, 'RAM', 'DDR4 RAM module for upgrading PC memory', 79.99, 50),
(18, 'SSD', 'Solid-state drive for faster data storage and boot times', 149.99, 70),
(19, 'Power Bank', 'Portable charger for smartphones and tablets', 49.99, 110),
(20, 'USB Flash Drive', 'Compact USB drive for transferring files on-the-go', 19.99, 150),
(21, 'HDMI Cable', 'High-speed HDMI cable for connecting devices to TVs', 9.99, 200),
(22, 'Ethernet Cable', 'Cat-6 Ethernet cable for wired internet connections', 14.99, 100),
(23, 'Wireless Earbuds', 'True wireless earbuds with sweat resistance', 99.99, 80),
(24, 'Fitness Tracker', 'Activity tracker with heart rate monitoring and GPS', 79.99, 70),
(25, 'Power Strip', 'Surge-protected power strip with multiple outlets', 29.99, 120),
(26, 'Desk Lamp', 'LED desk lamp with adjustable brightness levels', 39.99, 85),
(27, 'Bluetooth Earphones', 'Wireless earphones with noise isolation feature', 59.99, 65),
(28, 'Wireless Charging Pad', 'Qi-compatible charging pad for wireless charging', 34.99, 95),
(29, 'Webcam', 'HD webcam for video conferencing and streaming', 49.99, 40),
(30, 'Smart Thermostat', 'Wi-Fi enabled thermostat for smart home automation', 149.99, 30),
(31, 'Smart Bulb', 'Color-changing smart bulb controllable via smartphone', 24.99, 110),
(32, 'Security Camera', 'Indoor/outdoor security camera with motion detection', 129.99, 50),
(33, 'Smart Plug', 'Wi-Fi enabled smart plug for remote device control', 19.99, 90),
(34, 'Wireless Mouse', 'Optical wireless mouse with ergonomic design', 29.99, 75),
(35, 'External SSD', 'Portable solid-state drive for fast data transfer', 199.99, 25),
(36, 'USB-C Hub', 'Multi-port USB-C hub for connecting peripherals to laptops', 49.99, 60),
(37, 'Gaming Headset', 'Over-ear gaming headset with noise-canceling microphone', 89.99, 40),
(38, 'VR Headset', 'Virtual reality headset for immersive gaming experiences', 299.99, 15),
(39, 'Wireless Keyboard', 'Slim wireless keyboard for comfortable typing', 39.99, 55),
(40, 'Wireless Adapter', 'USB Wi-Fi adapter for adding wireless connectivity to PCs', 19.99, 85),
(41, 'Bluetooth Transmitter', 'Wireless audio transmitter for non-Bluetooth devices', 34.99, 70),
(42, 'Car Charger', 'Dual USB car charger for charging devices on-the-go', 14.99, 100),
(43, 'Portable Speaker', 'Compact portable speaker with built-in microphone', 59.99, 80),
(44, 'External Battery Case', 'Battery case for extending smartphone battery life', 39.99, 65),
(45, 'Wireless Gaming Mouse', 'Gaming mouse with customizable buttons and RGB lighting', 79.99, 50),
(46, 'Mechanical Keyboard', 'Full-sized mechanical keyboard with Cherry MX switches', 129.99, 35),
(47, 'USB Microphone', 'Professional USB microphone for podcasting and streaming', 99.99, 25),
(48, 'USB-C Cable', 'Fast-charging USB-C cable for compatible devices', 12.99, 150),
(49, 'Portable Hard Drive', 'Compact hard drive for storing and backing up data', 89.99, 45),
(50, 'Wireless Charging Stand', 'Wireless charging stand for smartphones with Qi support', 44.99, 70);
