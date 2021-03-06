INSERT INTO DRIVER (DRIVER_ID, CREATE_TIME, UPDATE_TIME, COMPANY, NAME) VALUES (1, NOW(), NOW(), '대국통운', '하은용');
INSERT INTO DRIVER (DRIVER_ID, CREATE_TIME, UPDATE_TIME, COMPANY, NAME) VALUES (2, NOW(), NOW(), '대국통운', '염정현');
INSERT INTO DRIVER (DRIVER_ID, CREATE_TIME, UPDATE_TIME, COMPANY, NAME) VALUES (3, NOW(), NOW(), '대국통운', '임세현');
INSERT INTO DRIVER (DRIVER_ID, CREATE_TIME, UPDATE_TIME, COMPANY, NAME) VALUES (4, NOW(), NOW(), '한민화물운송', '임준영');
INSERT INTO DRIVER (DRIVER_ID, CREATE_TIME, UPDATE_TIME, COMPANY, NAME) VALUES (5, NOW(), NOW(), '한민화물운송', '이동규');

INSERT INTO Vendor (VENDOR_ID, CREATE_TIME, UPDATE_TIME, NAME, CHARGE_NAME, CHARGE_PHONE) VALUES (1, NOW(), NOW(), '마크컬리','김영업', '010999999999');
INSERT INTO Vendor (VENDOR_ID, CREATE_TIME, UPDATE_TIME, NAME, CHARGE_NAME, CHARGE_PHONE) VALUES (2, NOW(), NOW(), '팡쿠','김영업', '010999999999');
INSERT INTO Vendor (VENDOR_ID, CREATE_TIME, UPDATE_TIME, NAME, CHARGE_NAME, CHARGE_PHONE) VALUES (3, NOW(), NOW(), '새벽야채','김영업', '010999999999');
INSERT INTO Vendor (VENDOR_ID, CREATE_TIME, UPDATE_TIME, NAME, CHARGE_NAME, CHARGE_PHONE) VALUES (4, NOW(), NOW(), '빠른과일','박영업', '010888888888');
INSERT INTO Vendor (VENDOR_ID, CREATE_TIME, UPDATE_TIME, NAME, CHARGE_NAME, CHARGE_PHONE) VALUES (5, NOW(), NOW(), '오늘수산물','박영업', '010888888888');

INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (1, NOW(), NOW(), '고객사 물건 누락건', 1, 1, FALSE, 'VENDOR');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (2, NOW(), NOW(), '고객사 청바지 누락', 1, 2, TRUE, 'VENDOR');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (3, NOW(), NOW(), '운송기사 물건 분실했습니다. 확인필요', 2, 3, FALSE, 'DRIVER');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (4, NOW(), NOW(), '운송기사 배송 지연건', 3, 3, TRUE, 'DRIVER');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (5, NOW(), NOW(), '운송기사 확인 필요건', 4, 5, TRUE, 'DRIVER');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (6, NOW(), NOW(), '운송사 책임', 1, 5, TRUE, 'DRIVER');
INSERT INTO VOC (VOC_ID, CREATE_TIME, UPDATE_TIME, CONTENT, DRIVER_ID, VENDOR_ID, IS_RECEIVED_COMPENSATION, VOC_TYPE) VALUES (7, NOW(), NOW(), '운송기사님 차량 고장', 4, 5, TRUE, 'DRIVER');

INSERT INTO COMPENSATION (COMPENSATION_ID , CREATE_TIME , UPDATE_TIME , AMOUNT , VOC_ID) VALUES (1, NOW(), NOW(), 100000, 2);
INSERT INTO COMPENSATION (COMPENSATION_ID , CREATE_TIME , UPDATE_TIME , AMOUNT , VOC_ID) VALUES (2, NOW(), NOW(), 50000, 7);

INSERT INTO PENALTY (PENALTY_ID , CREATE_TIME , UPDATE_TIME , AMOUNT , IS_CONFIRMED , OBJECT_CONTENT , IS_OBJECTED , VOC_ID) VALUES (1, NOW(), NOW(), 150000, FALSE, NULL, FALSE, 5);
INSERT INTO PENALTY (PENALTY_ID , CREATE_TIME , UPDATE_TIME , AMOUNT , IS_CONFIRMED , OBJECT_CONTENT , IS_OBJECTED , VOC_ID) VALUES (2, NOW(), NOW(), 98700, FALSE, '운송사 전산 오류', TRUE, 6);
INSERT INTO PENALTY (PENALTY_ID , CREATE_TIME , UPDATE_TIME , AMOUNT , IS_CONFIRMED , OBJECT_CONTENT , IS_OBJECTED , VOC_ID) VALUES (3, NOW(), NOW(), 50000, TRUE, NULL, FALSE, 7);
