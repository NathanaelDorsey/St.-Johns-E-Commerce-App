-- Add Products to Categories

-- School Supplies
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('Mechanical Pencils (Pack of 5)', 'Assorted mechanical pencils with erasers', 8.99, 4.5, 'In Stock', 'https://wildcatshop.net/images/product/medium/2703.jpg'),
    ('Spiral Notebooks (Set of 3)', 'College-ruled spiral notebooks for note-taking', 12.99, 4.8, 'In Stock', 'https://k-kraftpaper.com/cdn/shop/products/811lah16oVL._SL1500_1024x1024@2x.jpg'),
    ('Scientific Calculator', 'Advanced scientific calculator with multi-line display', 24.99, 4.7, 'In Stock', 'https://slugstore.ucsc.edu/sc-product-images/TI30XA-Scientific-Calculator_Main-1.jpg');

-- Apparel
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('University Hoodie', 'St. John\'s University logo hoodie', 29.99, 4.6, 'In Stock', 'https://i5.walmartimages.com/seo/St-John-University-Red-Storm-Game-Day-Hoodie-Gray_f82fbd94-3e8d-450e-b6ea-cf0ceeebf76e_1.fc81848ff4f7adaa219cb9c12d60c46e.jpeg'),
    ('School Uniform Polo Shirt', 'Official school uniform polo shirt', 19.99, 4.4, 'In Stock', 'https://bkstr.scene7.com/is/image/Bkstr/065-M51899-E000100-Univ-Red?$HomePageRecs_ET$'),
    ('Beanie Hat', 'Warm knit beanie hat with embroidered logo', 12.99, 4.2, 'In Stock', 'https://bkstr.scene7.com/is/image/Bkstr/065-4105-WDMK-Navy-Charcoal?$HomePageRecs_ET$');

-- Snacks and Beverages
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('Assorted Juice Pack (12-Pack)', 'Variety pack of assorted fruit juices', 14.99, 4.3, 'In Stock', 'https://i5.walmartimages.com/seo/Jumex-Mango-and-Peach-Nectar-from-Concentrate-11-3-Fl-Oz-12-Count_282a0ebb-2b9b-48da-b258-699cd4abd548_1.fa829d9a9f63a257f56a0f854a7017ed.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF'),
    ('Classic Potato Chips (Family Size)', 'Large bag of classic potato chips', 5.99, 4.6, 'In Stock', 'https://i5.walmartimages.com/seo/Lay-s-Classic-Potato-Chips-Family-Size-10-5-Oz_f4d2e77b-8b76-4b7a-a7a5-a2b6ac5ba516_1.81d554c78429d0d4349bda60fbe4b049.jpeg'),
    ('Gourmet Chocolate Assortment', 'Premium assortment of gourmet chocolates', 19.99, 4.8, 'In Stock', 'https://compartes.com/cdn/shop/products/Worlds-Best-Chocolates-Gourmet-Gift-Box-Large.jpg?v=1664146289');

-- School Spirit Items
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('Foam Finger - Red', 'St. John\'s University foam finger in red', 9.99, 4.5, 'In Stock', 'https://i.ebayimg.com/images/g/N58AAOSwKmRjcfVU/s-l1200.webp'),
    ('Megaphone - Blue', 'Blue school spirit megaphone with handle', 14.99, 4.2, 'In Stock', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUI6Bmk5J9i2icpTQXgmSKAot2mMcWs8-n0iVlx1D7Aw&s'),
    ('Temporary Tattoos (Pack of 20)', '20-pack of temporary tattoos with school logos', 4.99, 4.0, 'In Stock', 'https://m.media-amazon.com/images/I/81jCNOgQtmL._AC_UF1000,1000_QL80_.jpg');

-- Electronics and Accessories
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('Bluetooth Earphones', 'Wireless Bluetooth earphones with noise cancellation', 39.99, 4.7, 'In Stock', 'https://i5.walmartimages.com/seo/Bluetooth-Earbuds-Wireless-Headphones-5-3-Running-IPX7-Waterproof-Earphones-12-Hrs-Playtime-Stereo-Sound-Isolation-Headsets-Workout-Gym_f436d447-babc-4fd8-b8e0-ae6b753a8d69.a305c96e43c9815e40e81cf6b406657b.jpeg'),
    ('USB Flash Drive (64GB)', 'High-speed USB flash drive with ample storage', 19.99, 4.6, 'In Stock', 'https://m.media-amazon.com/images/I/51o2VzSkYJL._AC_UF894,1000_QL80_.jpg'),
    ('Laptop Sleeve (15-inch)', 'Slim laptop sleeve with soft interior lining', 14.99, 4.4, 'In Stock', 'https://m.media-amazon.com/images/I/91P-5tqh8WL._AC_UF1000,1000_QL80_.jpg');

-- Books and Magazines
INSERT INTO Product (name, description, price, rating, stock_status, image_url)
VALUES
    ('Biology 101 Textbook', 'Comprehensive textbook for introductory biology course', 49.99, 4.5, 'In Stock', 'https://openoregon.pressbooks.pub/app/uploads/sites/13/2016/09/Biology-101.jpg'),
    ('The Great Gatsby (Paperback)', 'Classic novel by F. Scott Fitzgerald', 12.99, 4.8, 'In Stock', 'https://images-na.ssl-images-amazon.com/images/I/61z0MrB6qOS._AC_UL600_SR600,600_.jpg'),
    ('National Geographic Magazine Subscription', 'One-year subscription to National Geographic magazine', 29.99, 4.7, 'In Stock', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF7D3bXs13J9k3dar6XLP5UnSkPuquMmdzxc9mP9Ug9Q&s');

-- Health and Beauty
INSERT INTO Product (name, description, price, stock_status, image_url)
VALUES
    ('Hand Sanitizer (8 oz)', 'Alcohol-based hand sanitizer gel', 6.99, 'In Stock', 'https://cdnimg.webstaurantstore.com/images/products/large/197774/969342.jpg'),
    ('Lip Balm Assortment (Pack of 3)', 'Variety pack of moisturizing lip balms', 9.99, 'In Stock', 'https://m.media-amazon.com/images/I/51ns9OUWhXL._AC_UF1000,1000_QL80_.jpg'),
    ('Detangling Hairbrush', 'Gentle detangling hairbrush for all hair types', 7.99, 'In Stock', 'https://wetbrush.com/cdn/shop/products/ORIGINAL_DETANGLER-Oval-PINK-Hair_Brush-BWR830PINK-Wet_Brush-Angled_2048x.jpg?v=1667404665');

-- Gifts and Novelties
INSERT INTO Product (name, description, price, stock_status, image_url)
VALUES
    ('Assorted Greeting Cards (Pack of 12)', 'Pack of assorted greeting cards for all occasions', 12.99, 'In Stock', 'https://www.hallmark.com/dw/image/v2/AALB_PRD/on/demandware.static/-/Sites-hallmark-master/default/dw3a496d4b/images/finished-goods/products/5STZ5016/Premium-Birthday-Cards-Assortment-Pack_5STZ5016_02.jpg?sw=570&sh=758&sm=fit&q=65'),
    ('Magnetic Keychain', 'Decorative magnetic keychain with school logo', 4.99, 'In Stock', 'https://m.media-amazon.com/images/I/41ShRoHVJ2L._SR600%2C315_PIWhiteStrip%2CBottomLeft%2C0%2C35_SCLZZZZZZZ_FMpng_BG255%2C255%2C255.jpg'),
    ('Sticker Pack - School Mascot', 'Pack of stickers featuring the school mascot', 3.99, 'In Stock', 'https://bkstr.scene7.com/is/image/Bkstr/6-25-SI--10-B');

-- School-related Merchandise
INSERT INTO Product (name, description, price, stock_status, image_url)
VALUES
    ('Yearbook 2022', 'Official school yearbook featuring highlights from the year', 24.99, 'In Stock', ''),
    ('Class Ring - Gold', 'Customizable class ring in gold finish', 89.99, 'In Stock', 'https://thumbs.worthpoint.com/zoom/images2/1/0617/13/vintage-10k-gold-st-johns-university_1_45dbdf17304bd8d124b8dc73a4a88b46.jpg');

