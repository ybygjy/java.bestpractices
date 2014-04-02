CREATE OR REPLACE PROCEDURE SYSTEM.NSTC_DBCOMPARE4TRIGG(SRCSCHEMA    IN VARCHAR2,
                                                 TARGETSCHEMA IN VARCHAR2) IS
  INSERT_FLAG BOOLEAN := FALSE;
  COMM_FLAG   BOOLEAN := FALSE;
BEGIN
  BEGIN
    DELETE FROM SYSTEM.NSTC_DBCOMP_TRIGG;
    COMMIT;
  END;
  DECLARE
    NDT SYSTEM.NSTC_DBCOMP_TRIGG%ROWTYPE := NULL;
  BEGIN
    FOR C IN (SELECT A.TABLE_NAME,
                     A.TRIGGER_NAME,
                     A.TRIGGER_TYPE     AS A_TRIGGER_TYPE,
                     A.TRIGGERING_EVENT AS A_TRIGGERING_EVENT,
                     A.BASE_OBJECT_TYPE AS A_BASE_OBJECT_TYPE,
                     A.WHEN_CLAUSE      AS A_WHEN_CLAUSE,
                     A.STATUS           AS A_STATUS,
                     B.TRIGGER_TYPE     AS B_TRIGGER_TYPE,
                     B.TRIGGERING_EVENT AS B_TRIGGERING_EVENT,
                     B.BASE_OBJECT_TYPE AS B_BASE_OBJECT_TYPE,
                     B.WHEN_CLAUSE      AS B_WHEN_CLAUSE,
                     B.STATUS           AS B_STATUS
                FROM (SELECT TABLE_NAME,
                             TRIGGER_NAME,
                             TRIGGER_TYPE,
                             TRIGGERING_EVENT,
                             BASE_OBJECT_TYPE,
                             WHEN_CLAUSE,
                             STATUS
                        FROM ALL_TRIGGERS A
                       WHERE A.OWNER = SRCSCHEMA
                         AND EXISTS
                       (SELECT 1
                                FROM ALL_TRIGGERS AA
                               WHERE AA.OWNER = TARGETSCHEMA
                                 AND AA.TRIGGER_NAME = A.TRIGGER_NAME
                                 AND AA.TABLE_NAME = A.TABLE_NAME)) A,
                     (SELECT TABLE_NAME,
                             TRIGGER_NAME,
                             TRIGGER_TYPE,
                             TRIGGERING_EVENT,
                             BASE_OBJECT_TYPE,
                             WHEN_CLAUSE,
                             STATUS
                        FROM ALL_TRIGGERS B
                       WHERE B.OWNER = TARGETSCHEMA
                         AND EXISTS
                       (SELECT 1
                                FROM ALL_TRIGGERS BB
                               WHERE BB.OWNER = SRCSCHEMA
                                 AND BB.TRIGGER_NAME = B.TRIGGER_NAME
                                 AND BB.TABLE_NAME = B.TABLE_NAME)) B
               WHERE A.TABLE_NAME = B.TABLE_NAME
                 AND A.TRIGGER_NAME = B.TRIGGER_NAME) LOOP
      IF (C.A_TRIGGER_TYPE <> C.B_TRIGGER_TYPE) THEN
        NDT.TRIGGER_TYPE := C.A_TRIGGER_TYPE || '!=' || C.B_TRIGGER_TYPE;
        INSERT_FLAG      := TRUE;
      END IF;
      IF (C.A_TRIGGERING_EVENT <> C.B_TRIGGERING_EVENT) THEN
        NDT.TRIGGER_EVENT := C.A_TRIGGERING_EVENT || '!=' ||
                             C.B_TRIGGERING_EVENT;
        INSERT_FLAG       := TRUE;
      END IF;
      IF (C.A_BASE_OBJECT_TYPE <> C.B_BASE_OBJECT_TYPE) THEN
        NDT.TRIGGER_BOT := C.A_BASE_OBJECT_TYPE || '!=' ||
                           C.B_BASE_OBJECT_TYPE;
        INSERT_FLAG     := TRUE;
      END IF;
      IF (C.A_WHEN_CLAUSE <> C.B_WHEN_CLAUSE) THEN
        NDT.TRIGGER_WCLAUSE := C.A_WHEN_CLAUSE || '!=' || C.B_WHEN_CLAUSE;
        INSERT_FLAG         := TRUE;
      END IF;
      IF (C.A_STATUS <> C.B_STATUS) THEN
        NDT.TRIGGER_STATUS := C.A_STATUS || '!=' || C.B_STATUS;
        INSERT_FLAG        := TRUE;
      END IF;
      IF (INSERT_FLAG = TRUE) THEN
        NDT.TABLE_NAME   := C.TABLE_NAME;
        NDT.TRIGGER_NAME := C.TRIGGER_NAME;
        INSERT INTO SYSTEM.NSTC_DBCOMP_TRIGG
          (TABLE_NAME,
           TRIGGER_NAME,
           TRIGGER_TYPE,
           TRIGGER_EVENT,
           TRIGGER_BOT,
           TRIGGER_WCLAUSE,
           TRIGGER_STATUS)
        VALUES
          (NDT.TABLE_NAME,
           NDT.TRIGGER_NAME,
           NDT.TRIGGER_TYPE,
           NDT.TRIGGER_EVENT,
           NDT.TRIGGER_BOT,
           NDT.TRIGGER_WCLAUSE,
           NDT.TRIGGER_STATUS);
        INSERT_FLAG := FALSE;
        COMM_FLAG   := TRUE;
        NDT         := NULL;
      END IF;
    END LOOP;
    IF (COMM_FLAG = TRUE) THEN
      COMMIT;
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('SQLERRM: ' || SQLERRM);
      DBMS_OUTPUT.PUT_LINE('SQLCODE: ' || SQLCODE);
  END;
END NSTC_DBCOMPARE4TRIGG;
/
