SELECT
    *
FROM
    tasks
/*BEGIN*/
WHERE
    /*IF deadlineFrom != null*/
        AND /*deadlineFrom*/123 <= tasks.deadline
    /*END*/
    /*IF deadlineTo != null*/
        AND tasks.deadline <= /*deadlineTo*/456 
    /*END*/
/*END*/
-- ソート条件に複数項目指定する場合
ORDER BY deadline /*$direction*/ASC, task_name /*$direction*/ASC
/*BEGIN*/
LIMIT
	/*IF offset != null*/
	/*offset*/0,
	/*END*/

	/*IF size != null*/
	/*size*/10
	/*END*/
/*END*/
