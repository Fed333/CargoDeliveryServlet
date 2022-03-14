--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (36, 1175.00, '', 'Андрій', '', 'Педосенко', 'riko', 128, '$2a$08$hpvkT9xd0XSqIveP4uop7uonh0d/B.lZ7XLdrvJZzn25lhOlBXTyu');
INSERT INTO public.users VALUES (59, 2000.00, '', 'Максим', '+380964776432', 'Поспішайко', 'maksymus', 129, '$2a$08$b9h95psKk5w/r7KNnnWgTO.qrZH9.Mm6cwZ8SkMt8X77f/USfufB6');
INSERT INTO public.users VALUES (60, 2000.00, NULL, 'Петро', '0963742374', 'Кандиба', 'Petya', 33, '$2a$08$Rllaa3ZjFWSgyK03LkMHcujr4xkPlRcXs06mDWVkc9jGZ53Ys7DKG');
INSERT INTO public.users VALUES (61, 2000.00, NULL, 'Володимир', '0985365668', 'Кличко', 'Vovan', 33, '$2a$08$wmpTYGJRmSZ7b6TQMI1CCuvrlUt7IoEeY1JPizLej/fJc.I9zBrQG');
INSERT INTO public.users VALUES (27, 150.00, 'mpostkryk@gmail.com', 'Іван', '+380967766223', 'Сіпалка', 'Pips', 14, '$2a$08$hNv2Edi5jJT.17O9TdxKouh/LoMP4/i0TseI8oDBTidD3.2Szzcfe');
INSERT INTO public.users VALUES (24, 1170.00, 'kovalchuk.roman03@gmail.com', 'Роман', '+380986278007', 'Ковальчук', 'romanko_09', 14, '$2a$08$xXOYOiavl0kIrMvXb2U67Oi1VfJakKn53.92/fstl1WdoznKtfbk6');
INSERT INTO public.users VALUES (28, 2000.00, 'meizum@gmail.com', 'Сергій', '', 'Дударко', 'seroza_da_da', 23, '$2a$08$EV7Wr2ZUHZcD4a6SIWEyMO7ONyvc6gTT9rorLZeT4gKCeUp5ObTh2');
INSERT INTO public.users VALUES (29, 2000.00, 'kosivets_ihor@gmail.com', 'Ігор', '', 'Косівець', 'ihor', 25, '$2a$08$HBAqMlKZBAVCCMnr3Zhrf.WBNcTKCyl/QHYBbXqjj54dNcIXrGHxm');
INSERT INTO public.users VALUES (30, 2000.00, 'ira_oliynyk@gmail.com', 'Іра', '', 'Олійник', 'ira_oliynyk', 27, '$2a$08$uUxvFOgPTGswycoDH6bSBOQh2UyNnDyA7yZG10dTAmJTyyvifViGO');
INSERT INTO public.users VALUES (33, 2000.00, 'klashreik@gmail.com', 'Іван', '', 'Булавко', 'iobulbik', 34, '$2a$08$upgv11fbMFMWj9yQF8vf/uSoRZDSkmULMFGEuDgtDCGTswKVK/zOS');
INSERT INTO public.users VALUES (35, 2000.00, '', 'Роман', '', 'Трембіта', 'trembita', 36, '$2a$08$1QWQDTzAtlzy2BV0N5gtk.7h9oEdF7D0p/4BA5pHdK2nMrlNp.mDC');
INSERT INTO public.users VALUES (49, NULL, '', 'Устим', '', 'Кармелюк', 'ustim_333', 105, '$2a$08$VlL/iTb1t4JAkxDBSAHRIOapm4Q13duF3YpS7OKhwmPCRScI1efoC');
INSERT INTO public.users VALUES (56, 2000.00, '', '', '', '', 'anonymous', 33, '$2a$08$T2O3W39RxzwTHlOF9Id0ceFV8E662B/xLpgWC5FQPkmbffCOkQvq.');
INSERT INTO public.users VALUES (57, 2000.00, 'fuzzydik@vntu.edu.ua', 'Іван', '+380974625535', 'Котєльніков', 'FuzzyDIK', 114, '$2a$08$RXspkrW6kafpMuXlzmjH2OfbnOizH9NJXqM.6lvqCARTZSvO2Zc5S');
INSERT INTO public.users VALUES (58, 2000.00, '', '', '', '', 'anonym', 33, '$2a$08$QFuVVUR8mU226p3Dzlryq.EFqOzcUG7rjbSYNk9uJi8w3WS4BzeIy');
INSERT INTO public.users VALUES (2, 2000.00, NULL, 'Roman', NULL, 'Koval', 'romanko_03', NULL, '$2a$08$U4mds9ZZuZMSWCuJ2RN8MOMTJ5K5dDbOBFeeFvxhbnhMAXNmxk/Sa');
INSERT INTO public.users VALUES (4, 2000.00, NULL, 'Ivan', NULL, 'Bulava', 'divan0_0', 4, '$2a$08$TpBsX.M/wwGeEOIyygQRp.ne7YnWLGe/pR2AtsvuK1CqP16K4ZJ.a');


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 61, true);

--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_role VALUES (2, 'USER');
INSERT INTO public.user_role VALUES (4, 'USER');
INSERT INTO public.user_role VALUES (24, 'USER');
INSERT INTO public.user_role VALUES (27, 'USER');
INSERT INTO public.user_role VALUES (28, 'USER');
INSERT INTO public.user_role VALUES (29, 'USER');
INSERT INTO public.user_role VALUES (30, 'USER');
INSERT INTO public.user_role VALUES (33, 'USER');
INSERT INTO public.user_role VALUES (35, 'USER');
INSERT INTO public.user_role VALUES (36, 'USER');
INSERT INTO public.user_role VALUES (24, 'MANAGER');
INSERT INTO public.user_role VALUES (49, 'USER');
INSERT INTO public.user_role VALUES (56, 'USER');
INSERT INTO public.user_role VALUES (57, 'USER');
INSERT INTO public.user_role VALUES (58, 'USER');
INSERT INTO public.user_role VALUES (59, 'USER');
INSERT INTO public.user_role VALUES (60, 'USER');
INSERT INTO public.user_role VALUES (61, 'USER');