CREATE SEQUENCE SEQ_TRAFFIC_LIGHT
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_TRAFFIC_LIGHTS(
    ID_TRAFFIC_LIGHT INTEGER DEFAULT SEQ_TRAFFIC_LIGHT.NEXTVAL NOT NULL,
    LATITUDE FLOAT(126) NOT NULL,
    LONGITUDE FLOAT(126) NOT NULL,
    CURRENT_STATUS VARCHAR2(100) DEFAULT 'OFF',
    GREEN_LIGHT_COUNTER INTEGER DEFAULT 0,
    YELLOW_LIGHT_COUNTER INTEGER DEFAULT 0,
    RED_LIGHT_COUNTER INTEGER DEFAULT 0,
    LIGHT_TIME_COUNTER INTEGER DEFAULT 0,
    LAST_UPDATE_TIME_STAMP DATE DEFAULT SYSDATE
);