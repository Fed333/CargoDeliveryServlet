--
-- Data for Name: distance_fares; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.distance_fares VALUES (10, 0, 19, 30);
INSERT INTO public.distance_fares VALUES (11, 20, 49, 50);
INSERT INTO public.distance_fares VALUES (12, 50, 199, 80);
INSERT INTO public.distance_fares VALUES (13, 200, 999, 150);
INSERT INTO public.distance_fares VALUES (14, 1000, 1000, 200);


--
-- Name: distance_fares_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.distance_fares_id_seq', 14, true);

--
-- Data for Name: weight_fares; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.weight_fares VALUES (11, 20, 0, 2);
INSERT INTO public.weight_fares VALUES (12, 30, 3, 9);
INSERT INTO public.weight_fares VALUES (13, 60, 10, 29);
INSERT INTO public.weight_fares VALUES (14, 120, 30, 99);
INSERT INTO public.weight_fares VALUES (15, 150, 100, 100);


--
-- Name: weight_fares_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.weight_fares_id_seq', 15, true);

--
-- Data for Name: dimensions_fares; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.dimensions_fares VALUES (10, 0, 4999, 10);
INSERT INTO public.dimensions_fares VALUES (11, 5000, 19999, 20);
INSERT INTO public.dimensions_fares VALUES (12, 20000, 99999, 35);
INSERT INTO public.dimensions_fares VALUES (13, 100000, 999999, 60);
INSERT INTO public.dimensions_fares VALUES (14, 1000000, 1000000, 80);


--
-- Name: dimensions_fares_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dimensions_fares_id_seq', 14, true);