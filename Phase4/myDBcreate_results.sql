SQL> @ myDBcreate.sql
SQL> Set echo on
SQL>
SQL> CREATE TABLE F21_S001_6_SUBSCRIPTION_PLAN(
  2          planid int not null,
  3          plantype varchar2(20) not null,
  4          max_content_resolution int,
  5          cost float,
  6          plan_duration int,
  7          screen_numner int,
  8          primary key(planid),
  9          unique(plantype)
 10  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_USERS(
  2          userid int not null,
  3          username varchar2(30) not null,
  4          email_id varchar2(50) not null,
  5          first_name varchar2(50),
  6          last_name varchar2(50),
  7          gender char,
  8          dob date,
  9          street varchar2(50),
 10          apt_no int,
 11          city varchar2(50),
 12          state varchar2(50),
 13          country varchar2(50),
 14          planid int,
 15          primary key(userid),
 16          unique(username, email_id),
 17          foreign key(planid) references F21_S001_6_SUBSCRIPTION_PLAN(planid) on delete cascade
 18  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_USERS_TELEPHONE(
  2          userid int,
  3          telephone int,
  4          primary key(userid, telephone),
  5          foreign key(userid) references F21_S001_6_USERS on delete cascade
  6  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_ACTIVITY_LOG(
  2          activityid int not null,
  3          activity_type varchar2(50),
  4          activity_date_time timestamp,
  5          userid int not null,
  6          primary key(activityid,userid),
  7          foreign key(userid) references F21_S001_6_USERS on delete cascade
  8  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_MOVIES(
  2          movieid int not null,
  3          movie_name varchar2(50),
  4          genre varchar2(50),
  5          duration int,
  6          release_date date,
  7          director varchar2(50),
  8          actor varchar2(50),
  9          age_category varchar2(50),
 10          language varchar2(50),
 11          content_length_category varchar2(50),
 12          maturity_rating varchar(50),
 13          primary key(movieid)
 14  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_STREAMING_ACTIVITY(
  2          streamid int not null,
  3          location varchar2(50),
  4          audio_language varchar2(50),
  5          subtitle_language varchar2(50),
  6          screen_number int,
  7          stream_resolution int,
  8          movieid int,
  9          review varchar2(50),
 10          stream_start_date_time timestamp,
 11          stream_end_date_time timestamp,
 12          userid int not null,
 13          primary key(streamid, userid),
 14          foreign key(userid) references F21_S001_6_USERS on delete cascade,
 15          foreign key(movieid) references F21_S001_6_MOVIES on delete cascade
 16  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_LANGUAGE(
  2          language_name varchar2(50) not null,
  3          audio_language varchar2(50),
  4          subtitle_language varchar2(50),
  5          primary key(language_name)
  6  );

Table created.

SQL>
SQL>
SQL> CREATE TABLE F21_S001_6_LANGUAGE_MOVIES(
  2          movieid int,
  3          language_name varchar2(50),
  4          primary key(movieid, language_name),
  5          foreign key(language_name) references F21_S001_6_LANGUAGE on delete cascade,
  6          foreign key(movieid) references F21_S001_6_MOVIES on delete cascade
  7  );

Table created.

SQL> spool off


