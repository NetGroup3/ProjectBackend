INSERT INTO client (password, first_name, last_name, email, status, role) VALUES
  ('$2a$10$i6HPAMY3OlQA88jZToOYhepcBG4CntsxiPFrV36X56WzuqCfl9m6a', 'John',  'Doe',        'admin@gmail.com',  'ACTIVE', 'ADMIN'),     -- password=newrandompassword1234
  ('$2a$10$0oKmdozNetD3MFv1iBn1d.nBMLDKDr4ZgWTrvJNaShSYWu5PEipEW', 'Katy',  'Perry',      'moder1@gmail.com', 'ACTIVE', 'MODERATOR'), -- password=newrandompassword5678
  ('$2a$10$NBG/G0XIvpiv/QaoJzWdVeAUxQqWuhqlxVIJqCz3X.v3eNwtqwEdq', 'Joe',   'Biden',      'moder2@gmail.com', 'ACTIVE', 'MODERATOR'), -- password=newrandompassword9012
  ('$2a$10$rM/041rKavYnAmIcJrsA6.pF5Ku0jWJTOE7u1LV8iY/7q6mKOS9Te', 'Taras', 'Shevchenko', 'user1@gmail.com',  'ACTIVE', 'USER'),      -- password=newrandompassword3456
  ('$2a$10$NzkfeJYbmLFbsBXDQGgpzujd4rZPoCdbOWLIw/2NdStbtaGA970cG', 'Lesia', 'Ukrainka',   'user2@gmail.com',  'ACTIVE', 'USER');      -- password=newrandompassword7890

INSERT INTO event (title, description, status)
VALUES ('First event', 'First event', 'ACTIVE'),
       ('Second event', 'Second event', 'ACTIVE'),
       ('Third event', 'Third event', 'ACTIVE');

INSERT INTO ingredient (title, description, category, is_active, measurement)
VALUES ('Milk', 'Cow''s milk', 'Milk product', TRUE, '0.3 liter'),
       ('Ice Cream', 'Vanilla Ice Cream', 'Milk product', TRUE, '0.1 liter');

INSERT INTO dish (title, description, category, receipt, is_active, amount)
VALUES ('Milkshake', 'Cool sweet drink', 'Non-alcoholic drinks', 'Milk, ice cream, vanilla sugar', TRUE, 2),
       ('Strawberry Milkshake', 'Cool sweet drink', 'Non-alcoholic drinks', 'Milk, ice cream, vanilla sugar, strawberry', TRUE, 3);

INSERT INTO kitchenware (title, description, category, is_active)
VALUES ('Glass', 'Glass', 'Dish', TRUE),
       ('Mixer', 'Mixer', 'Cooking tool', TRUE),
       ('Spoon', 'Spoon', 'Cooking tool', TRUE);