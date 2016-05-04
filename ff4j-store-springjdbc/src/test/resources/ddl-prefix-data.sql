---
-- #%L
-- ff4j-store-jdbc
-- %%
-- Copyright (C) 2013 Ff4J
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---

INSERT INTO T_FF4J_FEATURES_01(FEAT_UID, ENABLE, DESCRIPTION) VALUES('AwesomeFeature',  1, 'some desc');

-- First
INSERT INTO T_FF4J_FEATURES_01(FEAT_UID, ENABLE, DESCRIPTION) VALUES('first',  1, 'description');

INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('first', 'USER');

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('ppint', 'org.ff4j.property.PropertyInt', '12', NULL, 'first');;

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('ppdouble', 'org.ff4j.property.PropertyDouble', '12.5', NULL, 'first');;

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('ppboolean', 'org.ff4j.property.PropertyBoolean', 'true', NULL, 'first');;

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('ppstring', 'org.ff4j.property.PropertyString', 'hello', NULL, 'first');;

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('ppListInt', 'org.ff4j.property.PropertyInt','12' , '12,13,14', 'first');

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('digitValue', 'org.ff4j.property.PropertyInt', '1', '0,1,2,3', 'first');

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('regionIdentifier', 'org.ff4j.property.PropertyString', 'AMER', 'AMER,EAST,SSSS', 'first');

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('myLogLevel', 'org.ff4j.property.PropertyLogLevel', 'DEBUG', 'FATAL,WARN,TRACE,ERROR,INFO,DEBUG', 'first');

INSERT INTO T_FF4J_CUSTOM_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES, FEAT_UID) 
VALUES('date', 'org.ff4j.property.PropertyDate', '2015-08-15 12:00', NULL, 'AwesomeFeature');

-- Second
INSERT INTO T_FF4J_FEATURES_01(FEAT_UID, ENABLE, DESCRIPTION, GROUPNAME) VALUES('second', 0, 'description', 'GRP0');
INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('second', 'USER');

-- Third
INSERT INTO T_FF4J_FEATURES_01(FEAT_UID, ENABLE, DESCRIPTION, GROUPNAME) VALUES('third',  0, 'ThirdJDBC', 'GRP1');
INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('third', 'ADMINISTRATOR');
INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('third', 'BETA-TESTER');

-- Forth
INSERT INTO T_FF4J_FEATURES_01(FEAT_UID, ENABLE, DESCRIPTION, STRATEGY, EXPRESSION, GROUPNAME) 
VALUES('forth',  1, 'ForthJDBC', 'org.ff4j.strategy.el.ExpressionFlipStrategy', 'expression=third|second', 'GRP1');
INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('forth', 'ADMINISTRATOR');
INSERT INTO T_FF4J_ROLES_01(FEAT_UID, ROLE_NAME)  VALUES('forth', 'BETA-TESTER');

-- *********************************
-- ** INSERT INTO FF4J_PROPERTIES ** 
-- *********************************

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('a', 'org.ff4j.property.PropertyString', 'AMER', 'AMER,EAST');

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('b', 'org.ff4j.property.PropertyInt', '12', NULL);

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('c', 'org.ff4j.property.PropertyDouble', '12.5', NULL);

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('d', 'org.ff4j.property.PropertyBoolean', 'true', 'true,false');

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('e', 'org.ff4j.property.PropertyString', '12,13,14', NULL);

INSERT INTO T_FF4J_PROPERTIES_01(PROPERTY_ID, CLAZZ, CURRENTVALUE, FIXEDVALUES) 
VALUES('g', 'org.ff4j.property.PropertyLogLevel', 'DEBUG', 'FATAL,WARN,TRACE,ERROR,INFO,DEBUG');

