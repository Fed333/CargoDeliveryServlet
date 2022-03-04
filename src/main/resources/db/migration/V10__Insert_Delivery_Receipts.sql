--
-- Data for Name: delivery_receipts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.delivery_receipts VALUES (2, '2022-02-06', false, 1810, 22, 24, 27);
INSERT INTO public.delivery_receipts VALUES (6, '2022-02-06', false, 980, 20, 24, 27);
INSERT INTO public.delivery_receipts VALUES (9, '2022-02-06', false, 980, 19, 24, 27);
INSERT INTO public.delivery_receipts VALUES (8, '2022-02-06', true, 250, 24, 24, 27);
INSERT INTO public.delivery_receipts VALUES (5, '2022-02-06', true, 720, 12, 24, 27);
INSERT INTO public.delivery_receipts VALUES (3, '2022-02-06', true, 880, 21, 24, 27);
INSERT INTO public.delivery_receipts VALUES (7, '2022-02-06', true, 120, 1, 24, 36);
INSERT INTO public.delivery_receipts VALUES (10, '2022-02-09', true, 320, 26, 24, 36);
INSERT INTO public.delivery_receipts VALUES (14, '2022-02-09', false, 30, 17, 24, 27);
INSERT INTO public.delivery_receipts VALUES (4, '2022-02-06', true, 830, 23, 24, 24);
INSERT INTO public.delivery_receipts VALUES (12, '2022-02-09', true, 170, 31, 24, 36);
INSERT INTO public.delivery_receipts VALUES (11, '2022-02-09', true, 215, 32, 24, 36);
INSERT INTO public.delivery_receipts VALUES (16, '2022-02-16', false, 230, 18, 24, 27);
INSERT INTO public.delivery_receipts VALUES (17, '2022-02-16', false, 160, 27, 24, 36);
INSERT INTO public.delivery_receipts VALUES (20, '2022-02-19', false, 880, 11, 24, 27);
INSERT INTO public.delivery_receipts VALUES (21, '2022-02-19', false, 180, 35, 24, 57);
INSERT INTO public.delivery_receipts VALUES (22, '2022-02-21', false, 180, 36, 24, 36);
INSERT INTO public.delivery_receipts VALUES (23, '2022-02-22', false, 500, 7, 24, 27);
INSERT INTO public.delivery_receipts VALUES (24, '2022-02-22', false, 500, 6, 24, 27);


--
-- Name: delivery_receipts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.delivery_receipts_id_seq', 24, true);