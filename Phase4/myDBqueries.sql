-------------------------------------GROUP BY, HAVING, and aggregate computations------------------------------------------------------------


--•	1.Identify popular movie genres with respect to age group 

SELECT T1.AGE_CATEGORY,T2.GENRE,T1.TOT 
FROM (SELECT AGE_CATEGORY,MAX(TOT) AS TOT FROM (SELECT AGE_CATEGORY,GENRE,COUNT(S1.USERID) AS TOT FROM F21_S001_6_STREAMING_ACTIVITY S1,F21_S001_6_MOVIES M1
                                    WHERE S1.MOVIEID = M1.MOVIEID
                                   GROUP BY M1.AGE_CATEGORY,GENRE)
	   GROUP BY AGE_CATEGORY) T1,
(SELECT AGE_CATEGORY,GENRE,COUNT(S1.USERID) AS TOT1 FROM F21_S001_6_STREAMING_ACTIVITY S1,F21_S001_6_MOVIES M1
                                    WHERE S1.MOVIEID = M1.MOVIEID
                                   GROUP BY M1.AGE_CATEGORY,GENRE) T2
WHERE T1.TOT = T2.TOT1
AND T1.AGE_CATEGORY = T2.AGE_CATEGORY;




--•2.Identify the most popular membership plan for viewers 

SELECT PLANTYPE FROM F21_S001_6_SUBSCRIPTION_PLAN
WHERE PLANID IN
(SELECT S.PLANID FROM F21_S001_6_SUBSCRIPTION_PLAN S,F21_S001_6_USERS U
WHERE S.PLANID = U.PLANID
GROUP BY U.PLANID
having COUNT(U.USERID) = (SELECT MAX(COUNT(U1.USERID)) FROM F21_S001_6_USERS U1
						GROUP BY U1.PLANID));



--3.•	Identify movie language preferences of the viewers with respect to location 

SELECT T1.LOCATION,T2.LANGUAGE,T1.TOT 
FROM (SELECT LOCATION,MAX(TOT) AS TOT FROM (SELECT DISTINCT M1.LANGUAGE,LOCATION,COUNT(S1.USERID) AS TOT FROM F21_S001_6_STREAMING_ACTIVITY S1,F21_S001_6_MOVIES M1
                                    WHERE S1.MOVIEID = M1.MOVIEID
                                   GROUP BY M1.LANGUAGE,LOCATION)
GROUP BY LOCATION) T1,
(SELECT DISTINCT M1.LANGUAGE,LOCATION,COUNT(S1.USERID) AS TOT1 FROM F21_S001_6_STREAMING_ACTIVITY S1,F21_S001_6_MOVIES M1
                                    WHERE S1.MOVIEID = M1.MOVIEID
                                   GROUP BY M1.LANGUAGE,LOCATION) T2
WHERE T1.TOT = T2.TOT1
AND T1.LOCATION = T2.LOCATION;




--4.•	Identify the most liked movie by the viewers

SELECT MOVIEID,MOVIE_NAME FROM F21_S001_6_MOVIES
WHERE MOVIEID IN (SELECT MOVIEID FROM F21_S001_6_STREAMING_ACTIVITY
					WHERE REVIEW = 'Like'
					GROUP BY MOVIEID
					HAVING COUNT(*) = (SELECT MAX(COUNT(*)) FROM F21_S001_6_STREAMING_ACTIVITY
										WHERE REVIEW = 'Like'
										GROUP BY MOVIEID));



-------------------------------------------------ROLL UP and/or CUBE queries ---------------------------------------------------------

--5a. Generate user specific activity summary segregated by each activity type and user specific activity summary
  


SELECT USERID,ACTIVITY_TYPE,COUNT(USERID) AS NO_USERS_ACTIVITY FROM F21_S001_6_ACTIVITY_LOG 
GROUP BY ROLLUP(ACTIVITY_TYPE,USERID);



--5b.• Generate user specific movie summary segregated by each movie and user specific movie summary for activity type as watching 


SELECT S.USERID,S.MOVIEID,COUNT(S.USERID) FROM F21_S001_6_STREAMING_ACTIVITY S 
WHERE S.USERID IN (SELECT USERID FROM F21_S001_6_ACTIVITY_LOG A WHERE A.ACTIVITY_TYPE = 'Watching') 
GROUP BY ROLLUP(S.MOVIEID,S.USERID);


--6a.Generate user specific activity summary, user specific activity summary segregated by each activity type and user count for each activity type



SELECT USERID,ACTIVITY_TYPE,COUNT(USERID) AS NO_USERS_ACTIVITY FROM F21_S001_6_ACTIVITY_LOG 
GROUP BY CUBE(USERID,ACTIVITY_TYPE);



--6b. Generate user specific movie summary,user specific movie summary segregated by each movie and user count for each movie for activity type as watching  


SELECT S.USERID,S.MOVIEID,COUNT(S.USERID) FROM F21_S001_6_STREAMING_ACTIVITY S 
WHERE S.USERID IN (SELECT USERID FROM F21_S001_6_ACTIVITY_LOG A WHERE A.ACTIVITY_TYPE = 'Watching')
GROUP BY CUBE(S.MOVIEID,S.USERID);



-------------------------------------------------------------------OVER clause queries-------------------------------------------------------

--7. List Movieid with language name along with the count of movies in that particular language



SELECT M.MOVIEID,L.LANGUAGE_NAME,COUNT(L.LANGUAGE_NAME) OVER (Partition by L.LANGUAGE_NAME) FROM F21_S001_6_MOVIES M,F21_S001_6_LANGUAGE_MOVIES L
WHERE L.MOVIEID = M.MOVIEID
ORDER BY L.LANGUAGE_NAME;

--8.List genre,userid,age of user and count of genres by partitioning genre


SELECT M.GENRE,S.USERID,D.AGE,COUNT(M.GENRE) OVER (Partition by M.GENRE) FROM F21_S001_6_MOVIES M,F21_S001_6_STREAMING_ACTIVITY S,F21_S001_6_USERS_AGEDIFFERENCE D 
WHERE S.MOVIEID = M.MOVIEID AND S.USERID = D.USERID ORDER BY M.GENRE;




