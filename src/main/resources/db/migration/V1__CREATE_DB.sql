CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE public.client (
	id serial4 NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	email varchar NOT NULL,
	"password" varchar NOT NULL,
	"timestamp" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	image_id varchar NULL,
	status varchar NOT NULL,
	"role" varchar NOT NULL,
	CONSTRAINT client_un UNIQUE (email),
	CONSTRAINT client_pk PRIMARY KEY (id)
);

CREATE TABLE public.verify (
	user_id int4 NOT NULL,
	verify_code varchar NULL,
	"timestamp" timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT verify_un UNIQUE (user_id),
	CONSTRAINT verify_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE
);

CREATE TABLE public.post (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	title varchar NOT NULL,
	"timestamp" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"content" varchar NULL,
	image_id varchar NULL,
	CONSTRAINT post_pk PRIMARY KEY (id),
	CONSTRAINT post_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE
);

CREATE TABLE public.subscribe (
	id serial4 NOT NULL,
	sender_id int4 NOT NULL,
	recipient_id int4 NOT NULL,
	CONSTRAINT subscribe_pk PRIMARY KEY (id),
	CONSTRAINT subscribe_un UNIQUE (sender_id, recipient_id),
	CONSTRAINT subscribe_fk FOREIGN KEY (sender_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT subscribe_fk_1 FOREIGN KEY (recipient_id) REFERENCES public.client(id) ON DELETE CASCADE
);

CREATE TABLE public.friend (
	id serial4 NOT NULL,
	sender_id int4 NOT NULL,
	recepient_id int4 NOT NULL,
	status varchar NOT NULL,
	"timestamp" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT friend_pk PRIMARY KEY (id),
	CONSTRAINT friend_un UNIQUE (sender_id, recepient_id),
	CONSTRAINT friend_fk FOREIGN KEY (sender_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT friend_fk_1 FOREIGN KEY (recepient_id) REFERENCES public.client(id) ON DELETE CASCADE
);

CREATE TABLE public."event" (
	id serial4 NOT NULL,
	creation_timestamp timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	event_timestamp timestamptz NULL,
	title varchar NOT NULL,
	description varchar NULL,
	image_id varchar NULL,
	status varchar NOT NULL,
	location_lat numeric NULL,
	location_lon numeric NULL,
	CONSTRAINT event_pk PRIMARY KEY (id)
);

CREATE TABLE public.event_member (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	event_id int4 NOT NULL,
	status varchar NOT NULL,
	is_owner bool NOT NULL,
	CONSTRAINT event_member_pk PRIMARY KEY (id),
	CONSTRAINT event_member_un UNIQUE (user_id, event_id),
	CONSTRAINT event_member_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT event_member_fk_1 FOREIGN KEY (event_id) REFERENCES public."event"(id) ON DELETE CASCADE
);

CREATE TABLE public.message (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	event_id int4 NOT NULL,
	"text" varchar NOT NULL,
	"timestamp" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT message_event_pk PRIMARY KEY (id),
	CONSTRAINT message_event_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT message_event_fk_1 FOREIGN KEY (event_id) REFERENCES public."event"(id) ON DELETE CASCADE
);

CREATE TABLE public.message_status (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	message_id int4 NOT NULL,
	status varchar NOT NULL,
	CONSTRAINT message_status_pk PRIMARY KEY (id),
	CONSTRAINT message_status_un UNIQUE (user_id, message_id),
	CONSTRAINT message_status_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT message_status_fk_1 FOREIGN KEY (message_id) REFERENCES public.message(id) ON DELETE CASCADE
);

CREATE TABLE public."label" (
	id serial4 NOT NULL,
	title varchar NOT NULL,
	CONSTRAINT label_pk PRIMARY KEY (id),
	CONSTRAINT label_un UNIQUE (title)
);

CREATE TABLE public.dish (
	id serial4 NOT NULL,
	title varchar NOT NULL,
	description varchar NULL,
	category varchar NULL,
	receipt varchar NULL,
	image_id varchar NULL,
	is_active bool NOT NULL,
	CONSTRAINT dish_pk PRIMARY KEY (id)
);

CREATE TABLE public.dish_label (
	id serial4 NOT NULL,
	dish_id int4 NOT NULL,
	label_id int4 NOT NULL,
	CONSTRAINT dish_label_pk PRIMARY KEY (id),
	CONSTRAINT dish_label_un UNIQUE (dish_id, label_id),
	CONSTRAINT dish_label_fk FOREIGN KEY (label_id) REFERENCES public."label"(id) ON DELETE CASCADE,
	CONSTRAINT dish_label_fk_1 FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE
);

CREATE TABLE public.suggestion (
	id serial4 NOT NULL,
	dish1_id int4 NOT NULL,
	dish2_id int4 NOT NULL,
	CONSTRAINT suggestion_pk PRIMARY KEY (id),
	CONSTRAINT suggestion_un UNIQUE (dish1_id, dish2_id),
	CONSTRAINT suggestion_fk FOREIGN KEY (dish1_id) REFERENCES public.dish(id),
	CONSTRAINT suggestion_fk_1 FOREIGN KEY (dish2_id) REFERENCES public.dish(id)
);

CREATE TABLE public."comment" (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	dish_id int4 NOT NULL,
	"text" varchar NOT NULL,
	"timestamp" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT comment_pk PRIMARY KEY (id),
	CONSTRAINT comment_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT comment_fk_1 FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE
);

CREATE TABLE public."like" (
	id serial4 NOT NULL,
	amount int4 NULL,
	dish_id int4 NOT NULL,
	CONSTRAINT like_pk PRIMARY KEY (id),
	CONSTRAINT like_un UNIQUE (dish_id),
	CONSTRAINT like_fk FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE
);

CREATE TABLE public.ingredient (
	id serial4 NOT NULL,
	title varchar NOT NULL,
	description varchar NULL,
	category varchar NULL,
	image_id varchar NULL,
	is_active bool NOT NULL,
	measurement varchar NOT NULL,
	CONSTRAINT ingredient_pk PRIMARY KEY (id)
);

CREATE TABLE public.event_dish (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	event_id int4 NOT NULL,
	dish_id int4 NOT NULL,
	quantity int4 NOT NULL,
	CONSTRAINT event_dish_pk PRIMARY KEY (id),
	CONSTRAINT event_dish_un UNIQUE (user_id, event_id, dish_id),
	CONSTRAINT event_dish_fk FOREIGN KEY (user_id) REFERENCES public.client(id),
	CONSTRAINT event_dish_fk_1 FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE,
	CONSTRAINT event_dish_fk_2 FOREIGN KEY (event_id) REFERENCES public."event"(id) ON DELETE CASCADE
);

CREATE TABLE public.event_ingredient (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	event_id int4 NOT NULL,
	ingredient_id int4 NOT NULL,
	amount numeric NOT NULL,
	CONSTRAINT event_ingredient_pk PRIMARY KEY (id),
	CONSTRAINT event_ingredient_un UNIQUE (user_id, event_id, ingredient_id),
	CONSTRAINT event_ingredient_fk FOREIGN KEY (user_id) REFERENCES public.client(id),
	CONSTRAINT event_ingredient_fk_1 FOREIGN KEY (event_id) REFERENCES public."event"(id),
	CONSTRAINT event_ingredient_fk_2 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id)
);

CREATE TABLE public.dish_ingredient (
	id serial4 NOT NULL,
	dish_id int4 NOT NULL,
	ingredient_id int4 NOT NULL,
	ingredient_amount numeric NOT NULL,
	CONSTRAINT dish_ingredient_pk PRIMARY KEY (id),
	CONSTRAINT dish_ingredient_un UNIQUE (dish_id, ingredient_id),
	CONSTRAINT dish_ingredient_fk FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE,
	CONSTRAINT dish_ingredient_fk_1 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id) ON DELETE CASCADE
);

CREATE TABLE public.favourite (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	dish_id int4 NOT NULL,
	CONSTRAINT favourite_pk PRIMARY KEY (id),
	CONSTRAINT favourite_un UNIQUE (user_id, dish_id),
	CONSTRAINT favourite_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT favourite_fk_1 FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE
);

CREATE TABLE public.stock (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	ingredient_id int4 NOT NULL,
	amount numeric NOT NULL,
	CONSTRAINT stock_pk PRIMARY KEY (id),
	CONSTRAINT stock_un UNIQUE (user_id, ingredient_id),
	CONSTRAINT stock_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT stock_fk_1 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id) ON DELETE CASCADE
);

CREATE TABLE public.wish_list (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	ingredient_id int4 NOT NULL,
	event_id int4 NOT NULL,
	amount numeric NOT NULL,
	CONSTRAINT wish_list_pk PRIMARY KEY (id),
	CONSTRAINT wish_list_un UNIQUE (user_id, ingredient_id, event_id),
	CONSTRAINT wish_list_fk FOREIGN KEY (user_id) REFERENCES public.client(id) ON DELETE CASCADE,
	CONSTRAINT wish_list_fk_1 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id) ON DELETE CASCADE,
	CONSTRAINT wish_list_fk_2 FOREIGN KEY (event_id) REFERENCES public."event"(id) ON DELETE CASCADE
);

CREATE TABLE public.kitchenware (
	id serial4 NOT NULL,
	title varchar NOT NULL,
	description varchar NULL,
	category varchar NULL,
	image_id varchar NULL,
	is_active bool NOT NULL,
	CONSTRAINT kitchenware_pk PRIMARY KEY (id)
);

CREATE TABLE public.dish_kitchenware (
	id serial4 NOT NULL,
	dish_id int4 NOT NULL,
	kitchenware_id int4 NOT NULL,
	CONSTRAINT dish_kitchenware_pk PRIMARY KEY (id),
	CONSTRAINT dish_kitchenware_un UNIQUE (dish_id, kitchenware_id),
	CONSTRAINT dish_kitchenware_fk FOREIGN KEY (dish_id) REFERENCES public.dish(id) ON DELETE CASCADE,
	CONSTRAINT dish_kitchenware_fk_1 FOREIGN KEY (kitchenware_id) REFERENCES public.kitchenware(id) ON DELETE CASCADE
);
