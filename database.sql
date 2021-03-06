--
-- PostgreSQL database dump
--

-- Dumped from database version 10.20
-- Dumped by pg_dump version 10.20

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: pets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pets (
    id bigint NOT NULL,
    birthdate timestamp without time zone,
    name character varying(255),
    sex boolean,
    kind_id bigint,
    owner_id bigint NOT NULL
);


ALTER TABLE public.pets OWNER TO postgres;

--
-- Name: pets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pets_id_seq OWNER TO postgres;

--
-- Name: pets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pets_id_seq OWNED BY public.pets.id;


--
-- Name: s_kind; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.s_kind (
    id bigint NOT NULL,
    kind character varying(255)
);


ALTER TABLE public.s_kind OWNER TO postgres;

--
-- Name: s_kind_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.s_kind_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.s_kind_id_seq OWNER TO postgres;

--
-- Name: s_kind_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.s_kind_id_seq OWNED BY public.s_kind.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255),
    password character varying(255),
    failed_attempts integer,
    first_fail_time timestamp without time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: pets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets ALTER COLUMN id SET DEFAULT nextval('public.pets_id_seq'::regclass);


--
-- Name: s_kind id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.s_kind ALTER COLUMN id SET DEFAULT nextval('public.s_kind_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: pets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pets (id, birthdate, name, sex, kind_id, owner_id) FROM stdin;
16	2013-01-15 00:00:00	Trezor	t	1	26
17	2020-10-24 00:00:00	Murka	f	2	26
18	1945-05-09 00:00:00	Kaa	t	5	27
19	2020-05-05 00:00:00	Barsik	t	2	28
20	1900-08-18 00:00:00	Singing Shark	f	4	28
21	1992-05-05 00:00:00	Zita	f	1	29
22	1986-06-24 00:00:00	Danka	f	5	30
23	2010-01-07 00:00:00	Kesha	t	3	32
\.


--
-- Data for Name: s_kind; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.s_kind (id, kind) FROM stdin;
2	cat
3	bird
4	fish
5	snake
1	dog
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, password, failed_attempts, first_fail_time) FROM stdin;
26	vasya	$2a$10$BJVhWaJFKkeOYjmUbJcktO/OWePny4onnOC.FuJLsPtLUJMS/O36G	0	\N
27	petya	$2a$10$qVHSSUorJa5V.c82RS.h0.8LjhVdWv9WK6p9uieElF9bJSU4YhxrS	0	\N
28	oleg	$2a$10$3fUai3FmxBx9hZ8E/LaXBuoTAYgSYPAqOSvH5n7cW61D62RXHQI6y	0	\N
29	andrey	$2a$10$Wj5CgXLZ8.kWQLQhDn2Irua0nQd/gT1ma6kuI71a83xojJvn434Di	0	\N
30	misha	$2a$10$VxMMz6IqIn4u4oRd4R30FeMots0U/O5ku5Wh9XTLyVYgDRvn7FTXC	0	\N
31	olya	$2a$10$KnC4R/1OB4LW0QCZ3rnSU.NKzmI/0mxRud6OMqdlBqUBGfGoxSyvO	0	\N
32	masha	$2a$10$5E87AVH8EuspOJ7QWQwwTe.usE017b260CDAub32zqn4nc.7cFzs2	0	\N
\.


--
-- Name: pets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pets_id_seq', 23, true);


--
-- Name: s_kind_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.s_kind_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 32, true);


--
-- Name: pets pets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT pets_pkey PRIMARY KEY (id);


--
-- Name: s_kind s_kind_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.s_kind
    ADD CONSTRAINT s_kind_pkey PRIMARY KEY (id);


--
-- Name: users uk_3g1j96g94xpk3lpxl2qbl985x; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_3g1j96g94xpk3lpxl2qbl985x UNIQUE (name);


--
-- Name: pets uk_mpsqruq98wmxe1bfwxd41ws4l; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT uk_mpsqruq98wmxe1bfwxd41ws4l UNIQUE (name);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: pets fkkttmcgdiy48od9nnlu9bvnemd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT fkkttmcgdiy48od9nnlu9bvnemd FOREIGN KEY (kind_id) REFERENCES public.s_kind(id);


--
-- Name: pets fkoygstexeo9ivoylgrdrv2tc39; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT fkoygstexeo9ivoylgrdrv2tc39 FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

