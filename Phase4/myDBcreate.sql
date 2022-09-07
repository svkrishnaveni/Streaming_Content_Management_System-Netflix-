
-- Query to create table F21_S001_6_SUBSCRIPTION_PLAN --


CREATE TABLE F21_S001_6_SUBSCRIPTION_PLAN(
        planid varchar2(10) not null,
        plantype varchar2(20) not null,
        max_content_resolution varchar2(20),
        cost float,
        plan_duration int,
        screen_number int,
        primary key(planid),
        unique(plantype)
);      


-- Query to create table F21_S001_6_USERS --



CREATE TABLE F21_S001_6_USERS(
        userid varchar2(10) not null,
        username varchar2(30) not null,
        email_id varchar2(30) not null,
        first_name varchar2(20),
        last_name varchar2(20),
        gender char,
        dob date,
        street varchar2(20),
        apt_no int,
        city varchar2(20),
        state varchar2(20),
        country varchar2(20),
        planid varchar2(10),
        primary key(userid),
        unique(username, email_id),
        CHECK(gender = 'M' or gender = 'F'),
        foreign key(planid) references F21_S001_6_SUBSCRIPTION_PLAN(planid) on delete cascade
);


-- Query to create table F21_S001_6_USERS_TELEPHONE --


CREATE TABLE F21_S001_6_USERS_TELEPHONE(
        userid varchar2(10),
        telephone int,
        primary key(userid, telephone),
        foreign key(userid) references F21_S001_6_USERS on delete cascade
);


-- Query to create table F21_S001_6_ACTIVITY_LOG --


CREATE TABLE F21_S001_6_ACTIVITY_LOG(
        activityid varchar2(10) not null,
        activity_type varchar2(20),
        activity_date_time timestamp,
        userid varchar2(10) not null,
        primary key(activityid,userid),
        foreign key(userid) references F21_S001_6_USERS on delete cascade
);


-- Query to create table F21_S001_6_MOVIES --


CREATE TABLE F21_S001_6_MOVIES(
        movieid varchar2(10) not null,
        movie_name varchar2(80),
        genre varchar2(50),
        duration varchar2(20),
        release_date date,
        director varchar2(30),
        actor varchar2(30),
        age_category varchar2(20),
        language varchar2(20),
        content_length_category varchar2(30),
        maturity_rating varchar(20),
        primary key(movieid)
);


-- Query to create table F21_S001_6_STREAMING_ACTIVITY --


CREATE TABLE F21_S001_6_STREAMING_ACTIVITY(
        streamid varchar2(10) not null,
        location varchar2(20),
        audio_language varchar2(20),
        subtitle_language varchar2(20),
        screen_number int,
        stream_resolution varchar2(10),
        movieid varchar2(10),
        review varchar2(20),
        stream_start_date_time timestamp,
        stream_end_date_time timestamp,
        userid varchar2(10) not null,
        primary key(streamid, userid),
        foreign key(userid) references F21_S001_6_USERS on delete cascade,
        foreign key(movieid) references F21_S001_6_MOVIES on delete cascade
);


-- Query to create table F21_S001_6_LANGUAGE --


CREATE TABLE F21_S001_6_LANGUAGE(
        language_name varchar2(20) not null,
        audio_language varchar2(10),
        subtitle_language varchar2(10),
        primary key(language_name)
);


-- Query to create table F21_S001_6_LANGUAGE_MOVIES --


CREATE TABLE F21_S001_6_LANGUAGE_MOVIES(
        movieid varchar2(10),
        language_name varchar2(20),
        primary key(movieid, language_name),
        foreign key(language_name) references F21_S001_6_LANGUAGE on delete cascade,
        foreign key(movieid) references F21_S001_6_MOVIES on delete cascade
);


-- Query to create view F21_S001_6_USERS_AGEDIFFERENCE --

create view F21_S001_6_USERS_AGEDIFFERENCE as select USERID,(trunc((trunc(sysdate) - DOB)/365)) AS Age from F21_S001_6_USERS;





