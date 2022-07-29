// 편명 검색 클릭 시 항공사 정보 체크 및 이동(flightNext 태그의 name값으로 지정)
const airportCheck = () => {
    const selectAirport = $("select[name=departureAirport]").val();
    const selectDate = $("input[name=departureDate]").val();
    if (selectAirport == "") {
        alert("출국장소를 선택하세요")
    } else if (selectDate == "") {
        alert("출국일시를 선택하세요")
    } else {
        document.getElementById("flightNext").setAttribute("name", selectAirport);
        document.getElementById("flightNext").setAttribute("accessKey", selectDate);
        console.log(document.getElementById("flightNext"));
        $('#searchBtn').get(0).click();
    }
}

// 항공사 라디오 선택 시, 해당 항공사명(id)을 flightNext 태그의 value값으로 지정
$("input:radio[name=selectAirline]").click(function () {
    document.getElementById("flightNext").setAttribute("value", $("input:radio[name=selectAirline]:checked").attr('id'));
    console.log(document.getElementById("flightNext"));
});

// 해당 공항, 항공사 운항 스케쥴 불러오기
const flightList = (cityCode, airline, departureDate) => {
    console.log(cityCode);
    console.log(airline);
    console.log(departureDate);
    $.ajax({
        type: "get",
        url: "/departure/findFlightList",
        data: {
            "departureDate": departureDate,
            "cityCode": cityCode,
            "airline": airline
        },
        dataType: "json",
        success: function (result) {
            console.log(result);
            let output = "<table class='table'>";
            for (let i in result) {
                output += "<tr>";
                output += "<td>" + result[i].airlineKorean + "(" + airline + ")" + "</td>";
                output += "<td>" + result[i].airport + "(" + result[i].airportCode + ")" + "</td>";
                output += "<td>" + result[i].internationalNum + "</td>";
                output += "<td>" + result[i].internationalTime + "</td>";
                output += "<td>" + "<button type='button' data-bs-dismiss=\"modal\" class='btn-outline' onclick=\"flightUpdate('" + result[i].internationalNum + "')\">" + "선택" + "</button>" + "</td>";
                output += "</tr>";
            }
            output += "</table>";
            document.getElementById('flight-list').innerHTML = output;
        },
        error: function () {
            alert("error");
        }
    })
}

// 선택한 항공편을 input태그로 옮기기
const flightUpdate = (internationalNum) => {
    console.log(internationalNum)
    document.getElementById("departureNumber").value = internationalNum;
}