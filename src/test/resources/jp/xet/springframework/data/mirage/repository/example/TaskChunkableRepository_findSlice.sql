SELECT
    *
FROM
    tasks
ORDER BY deadline /*$direction*/ASC
/*BEGIN*/
LIMIT
	/*IF offset != null*/
	/*offset*/0,
	/*END*/

	/*IF size != null*/
	/*size*/10
	/*END*/
/*END*/
