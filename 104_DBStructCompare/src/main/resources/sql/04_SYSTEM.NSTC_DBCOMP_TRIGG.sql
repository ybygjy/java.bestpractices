CREATE TABLE SYSTEM.NSTC_DBCOMP_TRIGG(
       TABLE_NAME VARCHAR2(30) NOT NULL,
       TRIGGER_NAME VARCHAR2(30) NOT NULL,
       TRIGGER_TYPE VARCHAR2(40),
       TRIGGER_EVENT VARCHAR2(512),
       TRIGGER_BOT VARCHAR2(40),
       TRIGGER_WCLAUSE VARCHAR2(4000),
       TRIGGER_STATUS VARCHAR2(30)
);
-- Add comments to the table 
comment on table SYSTEM.NSTC_DBCOMP_TRIGG
  is '触发器对象结构比较结果';
-- Add comments to the columns 
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TABLE_NAME
  is '关联表编码';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_NAME
  is '触发器编码';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_TYPE
  is '触发器类型';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_EVENT
  is '触发器触发事件描述';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_BOT
  is '关联对象类型{TABLE:VIEW}';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_WCLAUSE
  is '触发器执行条件';
comment on column SYSTEM.NSTC_DBCOMP_TRIGG.TRIGGER_STATUS
  is '触发器状态{ENABLE:DISENABLE}';
