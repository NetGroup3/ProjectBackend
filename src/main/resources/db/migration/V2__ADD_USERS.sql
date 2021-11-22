INSERT INTO client (password, first_name, last_name, email, status, role) VALUES
  ('$2a$10$gR2B.htZjygoaQ8YIEP8zOBY5UwF/NU8UkeTRsYcf05MTIdAt898S', 'John',  'Doe',        'admin@gmail.com',  'ACTIVE', 'ADMIN'),     -- password=admin
  ('$2a$10$IMhAKD/jwFvm942e0eTq9.uX4GQD9k/b29QiQ/o0VKjZV1/l7.1sa', 'Katy',  'Perry',      'moder1@gmail.com', 'ACTIVE', 'MODERATOR'), -- password=moder1
  ('$2a$10$zbsZhuYaKtS003Yy4RYzm.yqdgSIA.BTGz8gi843oUURPZART423u', 'Joe',   'Biden',      'moder2@gmail.com', 'ACTIVE', 'MODERATOR'), -- password=moder2
  ('$2a$10$mw/ql5RRGajQ0jYinJvBR.a.fRbjXzr7Cr9xv6cM78mIihVDf42s2', 'Taras', 'Shevchenko', 'user1@gmail.com',  'ACTIVE', 'USER'),      -- password=user1
  ('$2a$10$rJAsOebtWEf.emM5t5Pdqe5cPs/vrjDDQyEq7j88YbXo5iVoN6HKu', 'Lesia', 'Ukrainka',   'user2@gmail.com',  'ACTIVE', 'USER');      -- password=user2
