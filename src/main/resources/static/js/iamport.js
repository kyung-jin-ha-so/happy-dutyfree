// 아임포트
const IMP = window.IMP; // 생략 가능
IMP.init("imp66752664"); // 예: imp00000000
function requestPay(pgName, paymentWon) {
    // IMP.request_pay(param, callback) 결제창 호출
    IMP.request_pay({ // param
        pg: pgName,
        pay_method: "card",
        merchant_uid: 'merchant_' + new Date().getTime(), // 중복되지 않는 값(임의의 값, 주문 고유번호)
        name: "[[${cartList.get(0).productName}]] 외 [[${cartList.size()} - 1]]개",
        amount: paymentWon,
        buyer_email: "[[${member.memberEmail}]]",
        buyer_name: "[[${member.memberName}]]",
        buyer_tel: "[[${member.memberMobile}]]",
    }, function (rsp) { // callback
        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
            // jQuery로 HTTP 요청
            // jQuery.ajax({
            //     url: "{서버의 결제 정보를 받는 endpoint}", // 예: https://www.myservice.com/payments/complete
            //     method: "POST",
            //     headers: { "Content-Type": "application/json" },
            //     data: {
            //         imp_uid: rsp.imp_uid,
            //         merchant_uid: rsp.merchant_uid
            //     }
            // }).done(function (data) {
            //     // 가맹점 서버 결제 API 성공시 로직
            // })
            alert("완료" + rsp.imp_uid + rsp.merchant_uid)
        } else {
            alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
        }
    });
}