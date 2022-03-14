--
-- Data for Name: cities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cities VALUES (2, 'Uman', '20301');
INSERT INTO public.cities VALUES (1, 'Vinnytsia', '21012');
INSERT INTO public.cities VALUES (3, 'Odessa', '65125');
INSERT INTO public.cities VALUES (4, 'Khmelnytskyi', '29000-29027');
INSERT INTO public.cities VALUES (5, 'Zhytomyr', '10000-10031');
INSERT INTO public.cities VALUES (6, 'Kyiv', '01001-06999');
INSERT INTO public.cities VALUES (7, 'Dnipro', '49000-49489');
INSERT INTO public.cities VALUES (8, 'Zaporizhzhia', '69001-69124');
INSERT INTO public.cities VALUES (9, 'Ivano-Frankivsk', '76000-76030');
INSERT INTO public.cities VALUES (10, 'Kamianets-Podilskyi', '32300-32318');
INSERT INTO public.cities VALUES (12, 'Kropyvnytskyi', '25000-25490');
INSERT INTO public.cities VALUES (13, 'Lutsk', '43000');
INSERT INTO public.cities VALUES (14, 'Lviv', '79000');
INSERT INTO public.cities VALUES (15, 'Mykolayiv', '54001-54058');
INSERT INTO public.cities VALUES (16, 'Poltava', '36000-36499');
INSERT INTO public.cities VALUES (17, 'Rivne', '33000-33499');
INSERT INTO public.cities VALUES (18, 'Sumy', '40000-40035');
INSERT INTO public.cities VALUES (19, 'Ternopil', '46000-46499');
INSERT INTO public.cities VALUES (20, 'Uzhhorod', '88000');
INSERT INTO public.cities VALUES (22, 'Kherson', '73000');
INSERT INTO public.cities VALUES (23, 'Cherkasy', '18000');
INSERT INTO public.cities VALUES (24, 'Chernivtsi', '58000');
INSERT INTO public.cities VALUES (25, 'Chernihiv', '14000');
INSERT INTO public.cities VALUES (21, 'Kharkiv', '61000-62480');
INSERT INTO public.cities VALUES (11, 'Kryvyi_Rih', '50000-50479');


--
-- Name: cities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cities_id_seq', 26, true);