package kitchenpos.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.domain.OrderTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTableAcceptanceFactory {


    public static ExtractableResponse<Response> 주문테이블_등록_요청(boolean isEmpty, int 손님의수) {
        OrderTable orderTable = new OrderTable();
        orderTable.setEmpty(isEmpty);
        orderTable.setNumberOfGuests(손님의수);

        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderTable)
                .when()
                .post("/api/tables/")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문테이블_조회_요청() {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/tables/")
                .then().log().all()
                .extract();
    }


    public static void 주문테이블_등록성공(ExtractableResponse<Response> 주문테이블등록_결과) {
        assertThat(주문테이블등록_결과.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 주문테이블_조회성공(ExtractableResponse<Response> 주문테이블조회_결과) {
        assertThat(주문테이블조회_결과.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
