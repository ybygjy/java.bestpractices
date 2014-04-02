-- Create table
CREATE TABLE SYSTEM.NSTC_DBCOMP_OBJECT
(
  OP_TYPE   NUMBER(2) default 1 not null,
  SRCSCHEMA VARCHAR2(20) not null,
  TARSCHEMA VARCHAR2(20) not null,
  TABLE_NAME VARCHAR2(40),
  OBJ_NAME  VARCHAR2(40) not null,
  OBJ_TYPE  VARCHAR2(20) not null
);
-- Add comments to the table 
comment on table SYSTEM.NSTC_DBCOMP_OBJECT
  is '数据库对象结构比较_对象缺失登记表';
-- Add comments to the columns 
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.OP_TYPE
  is '缺失类型{1:缺失;2:多余}';
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.SRCSCHEMA
  is '源比较用户';
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.TABLE_NAME
  is '表名称,适用于基于表的对象类型如字段\触发器';
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.TARSCHEMA
  is '目标(参照)用户';
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.OBJ_NAME
  is '对象名称';
comment on column SYSTEM.NSTC_DBCOMP_OBJECT.OBJ_TYPE
  is '对象数据类型{FIELD|VIEW|TABLE|FUNC|PROC|TRIG|SEQ|ETC..}';
