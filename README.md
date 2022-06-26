# 키친포스

## 요구 사항
- 1단계
  - 상품 
    - 상품 등록
      - 상품 가격은 0원 이상이어야 한다.
    - 상품 조회
  - 메뉴 그룹
    - 메뉴 그룹 등록
    - 메뉴 그룹 조회
  - 메뉴
    - 메뉴 등록
      - 메뉴 가격은 0원 이상이어야 한다.
      - 메뉴의 그룹이 존재하여야 한다.
      - 메뉴의 상품들이 모두 등록되어 있어야 한다.
      - 메뉴 price는 메뉴 상품들의 가격의 합보다 클 수 없다.
    - 메뉴 조회
  - 주문 테이블
    - 주문 테이블 등록
    - 주문 테이블 조회
    - 주문 테이블 빈 테이블로 변경
      - 존재하는 주문 테이블이어야 한다
      - 테이블 그룹에 속하지 않아야 한다.
      - 주문 상태가 요리중이거나 식사중이면 변경할 수 없다.
    - 주문 테이블 손님수 변경
      - 손님의 수는 0명 이상이어야 한다.
      - 존재하는 주문 테이블이어야 한다.
      - 주문테이블이 비어있지 않아야한다.
  - 테이블 그룹(단체 지정)
    - 테이블 그룹 등록
      - 주문 테이블이 2개 이상이어야 한다.
      - 주문 테이블이 모두 존재하여야 한다.
      - 주문 테이블이 모두 비어있어야 한다.
    - 테이블 그룹 삭제
      - 주문 상태가 요리중이거나 식사중이면 삭제할 수 없다.
  - 주문
    - 주문 등록
      - 주문 항목이 비어있으면 안된다.
      - 주문 항목의 메뉴가 등록되어있는 메뉴여야 한다.
      - 주문 테이블이 모두 존재하여야 한다.
      - 주문 테이블이 비어있으면 안된다.
    - 주문 조회
    - 주문 상태 변경
      - 주문이 존재하여야 한다.
      - 주문 상태가 완료이면 변경할 수 없다.
      - 


  
## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
