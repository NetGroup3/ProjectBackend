INSERT INTO client (password, first_name, last_name, email, status, role, image_id) VALUES
  ('$2a$10$i6HPAMY3OlQA88jZToOYhepcBG4CntsxiPFrV36X56WzuqCfl9m6a', 'John',  'Doe',        'admin@gmail.com',  'ACTIVE', 'ADMIN', ''),     -- password=newrandompassword1234
  ('$2a$10$0oKmdozNetD3MFv1iBn1d.nBMLDKDr4ZgWTrvJNaShSYWu5PEipEW', 'Katy',  'Perry',      'moder1@gmail.com', 'ACTIVE', 'MODERATOR', 'katy'), -- password=newrandompassword5678
  ('$2a$10$NBG/G0XIvpiv/QaoJzWdVeAUxQqWuhqlxVIJqCz3X.v3eNwtqwEdq', 'Joe',   'Biden',      'moder2@gmail.com', 'ACTIVE', 'MODERATOR', 'biden'), -- password=newrandompassword9012
  ('$2a$10$rM/041rKavYnAmIcJrsA6.pF5Ku0jWJTOE7u1LV8iY/7q6mKOS9Te', 'Taras', 'Shevchenko', 'user1@gmail.com',  'ACTIVE', 'USER', 'taras'),      -- password=newrandompassword3456
  ('$2a$10$NzkfeJYbmLFbsBXDQGgpzujd4rZPoCdbOWLIw/2NdStbtaGA970cG', 'Lesia', 'Ukrainka',   'user2@gmail.com',  'ACTIVE', 'USER', 'lesya'),      -- password=newrandompassword7890
  ('$2a$10$iGYxmFb/Nuz6sdqqy3eRueeESuq77RIefHnxcF4PvJmLdUF33i3qa', 'Ivan', 'Franko',      'user3@gmail.com',  'ACTIVE', 'USER', 'ivanFranko'),      -- password=newrandompassword9087
  ('$2a$10$BDDfdOrWzD6GFh7qKWxBy.zinoFBfgj.yM/4dXOYiJ0UK6l/oY92K', 'Lina', 'Kostenko',    'user4@gmail.com',  'ACTIVE', 'USER', 'lina'),      -- password=newrandompassword2965
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Ivan', 'Kotliarevsky','user5@gmail.com',  'ACTIVE', 'USER', 'ivanK'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Pyotr', 'Tchaikovsky','user6@gmail.com',  'ACTIVE', 'USER', 'petro'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Frank', 'Sinatra',    'user7@gmail.com',  'ACTIVE', 'USER', 'sinatra'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Alexander', 'Pushkin','user8@gmail.com',  'ACTIVE', 'USER', 'pushkin'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Papich', 'Velichayshiy','user9@gmail.com',  'ACTIVE', 'USER', 'cdqdpxcwapwvdfes8jc6'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Peter', 'the Great','user10@gmail.com',  'ACTIVE', 'USER', 'peter'),      -- password=newrandompassword4143
  ('$2a$10$eJBncs6wxqce5v/vJL118uV./ga/NVMklbHgXCFE..E.i6aAMFye.', 'Albert', 'Einstein','user11@gmail.com',  'ACTIVE', 'USER', 'plnwun46sprpceb7guzu');      -- password=newrandompassword4143

INSERT INTO event (title, description, status)
VALUES ('First event', 'First event', 'ACTIVE'),
       ('Second event', 'Second event', 'ACTIVE'),
       ('Third event', 'Third event', 'ACTIVE');

INSERT INTO ingredient (title, description, category, is_active, measurement)
VALUES ('Milk', 'Cow''s milk', 'Milk product', TRUE, 'liter'),
       ('Ice Cream', 'Vanilla Ice Cream', 'Milk product', TRUE, 'gram'),
       ('Vanilla sugar', 'Cow''s milk', 'Milk product', TRUE, 'gram'),
       ('Strawberry', 'Cow''s milk', 'Milk product', TRUE, 'gram');

INSERT INTO dish (title, description, category, receipt, is_active, likes)
VALUES ('Milkshake', 'Cool sweet drink', 'Non-alcoholic drinks', 'Milk, ice cream, vanilla sugar', TRUE, 2),
       ('Strawberry Milkshake', 'Cool sweet drink', 'Non-alcoholic drinks', 'Milk, ice cream, vanilla sugar, strawberry', TRUE, 20);

INSERT INTO kitchenware (title, description, category, is_active)
VALUES ('Glass', 'Glass', 'Dish', TRUE),
       ('Mixer', 'Mixer', 'Cooking tool', TRUE),
       ('Spoon', 'Spoon', 'Cooking tool', TRUE);

INSERT INTO dish_ingredient (dish_id, ingredient_id, ingredient_amount)
VALUES (1, 1, 0.3),
       (1, 2, 300),
       (1, 3, 50),
       (2, 1, 0.3),
       (2, 2, 300),
       (2, 3, 50),
       (2, 4, 200);

INSERT INTO dish_kitchenware (dish_id, kitchenware_id)
VALUES ( 1, 1),
       ( 1, 2),
       ( 2, 1),
       ( 2, 2);

INSERT INTO event_dish (user_id, event_id, dish_id, quantity)
VALUES (4, 1, 1, 5),
       (4, 1, 2, 5),
       (5, 2, 1, 10),
       (5, 3, 2, 8);

INSERT INTO event_ingredient (user_id, event_id, ingredient_id, amount)
VALUES (4, 1, 1, 10),
       (4, 1, 2, 10),
       (4, 1, 3, 10),
       (4, 1, 4, 5),
       (5, 2, 1, 10),
       (5, 2, 2, 10),
       (5, 2, 3, 10),
       (5, 3, 1, 8),
       (5, 3, 2, 8),
       (5, 3, 3, 8),
       (5, 3, 4, 8);

INSERT INTO event_member (user_id, event_id, status, is_owner)
VALUES ( 4, 1, 'ACTIVE', TRUE),
       ( 5, 1, 'ACTIVE', TRUE),
       ( 4, 2, 'ACTIVE', TRUE),
       ( 5, 2, 'ACTIVE', TRUE),
       ( 4, 3, 'ACTIVE', TRUE),
       ( 5, 3, 'ACTIVE', TRUE);

INSERT INTO favourite (user_id, dish_id)
VALUES (4, 1),
       (5, 2);

INSERT INTO friend (sender_id, recipient_id, status)
VALUES (4, 2, 'FRIEND'),
       (4, 3, 'FRIEND'),
       (4, 5, 'FRIEND'),
       (4, 6, 'FRIEND'),
       (4, 7, 'FRIEND'),
       (4, 8, 'FRIEND'),
       (9, 4, 'AWAITING'),
       (10, 4, 'AWAITING'),
       (11, 4, 'AWAITING'),
       (12, 4, 'AWAITING'),
       (13, 4, 'AWAITING'),
       (14, 4, 'AWAITING');

INSERT INTO message (user_id, event_id, text)
VALUES (5, 1, 'Good job');

INSERT INTO message_status (user_id, message_id, status)
VALUES (5, 1, 'ACTIVE');

INSERT INTO post (user_id, title, content)
VALUES (4, 'First post', 'Post''s content'),
       (5, 'Second post', 'Post''s content');

INSERT INTO stock (user_id, ingredient_id, amount)
VALUES (4, 1, 3),
       (4, 2, 3),
       (4, 3, 3),
       (4, 4, 2),
       (5, 1, 5),
       (5, 3, 6);

INSERT INTO comment (user_id, dish_id, text)
VALUES (4, 1, 'Delicious Milkshake'),
       (5, 2, 'Delicious Strawberry Milkshake');

INSERT INTO wish_list (user_id, ingredient_id, event_id, amount)
VALUES (4, 4, 1, 1);

INSERT INTO label (title)
VALUES ('Milk'),
       ('Sweet');

INSERT INTO dish_label (dish_id, label_id)
VALUES ( 1, 1),
       ( 1, 2),
       ( 2, 1),
       ( 2, 2);

