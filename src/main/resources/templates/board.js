var click_flag = 0;
var before_position = "";
var after_position = "";
var team = "";
var way = "";
var gameId = "";
var end_flag = false;

document.addEventListener("DOMContentLoaded", function () {

    $(document).ready(function () {
        gameId = location.href.substr(
            location.href.lastIndexOf('=') + 1
        );
    });

    setInterval(function () {
        console.log(gameId)
        if (!end_flag) {
            $.ajax({
                type: "POST",
                url: "/refresh",
                data: {
                    gameId: gameId
                },
                success: function (data) {
                    if (data === "lose") {
                        end_flag = true;
                        printLoser()
                    } else {
                        console.log(data)
                        drawBoard(data)
                    }
                },
                error: function (e) {
                    console.log(e.message);
                }
            });
        }
    }, 1000);

    $('.btn-surrender').click(function () {
        $.ajax({
            type: "POST",
            url: "/surrender",
            async: true,
            data: {
                gameId: gameId
            },
            success: function (data) {
                console.log(data);
                window.location.href = "index.html"
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });

    $('.btn-end').click(function () {
        $.ajax({
            type: "POST",
            url: "/end",
            async: true,
            data: {
                gameId: gameId
            },
            success: function (data) {
                console.log(data);
                window.location.href = "index.html"
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });

    $('.square').click(function () {
        if (click_flag == 0) {
            before_position = $(this).attr('id');
            $.ajax({
                url: '/way',
                async: true,
                type: 'POST',
                data: {
                    gameId: gameId,
                    team: team,
                    coordinate: before_position
                },
                dataType: 'text',// xml, json, script, html

                success: function (data) {
                    console.log(data)
                    drawWay(data)
                    click_flag = 1;
                },// 요청 완료 시
                error: function (e) {
                    console.log("false");
                    console.log(e.message);
                },// 요청 실패.
                complete: function (jqXHR) {
                    console.log("finish");
                }// 요청의 실패, 성공과 상관 없이 완료 될 경우 호출
            });
        } else {
            after_position = $(this).attr('id');
            click_flag = 0;
            removeWay();
            $.ajax({
                url: '/move',
                async: true,
                type: 'POST',
                data: {
                    source: before_position,
                    target: after_position
                },
                dataType: 'text',// xml, json, script, html

                success: function (data) {
                    if (data === "win") {
                        printWinner()
                        end_flag = true
                    }
                    console.log("success");
                },// 요청 완료 시
                error: function (e) {
                    console.log("false");
                    console.log(e.message);
                },// 요청 실패.
                complete: function (jqXHR) {
                    console.log("finish");
                }// 요청의 실패, 성공과 상관 없이 완료 될 경우 호출
            });
        }
    });
});

function drawBoard(data) {
    let jsonData = JSON.parse(data)
    let board = jsonData.board
    let blackScore = jsonData.blackScore
    let whiteScore = jsonData.whiteScore
    let gameId = jsonData.gameId
    let playerCount = jsonData.playerCount

    if (team === "" && playerCount === 1) {
        team = "WHITE"
    }
    if (team === "" && playerCount === 2) {
        team = "BLACK"
    }
    jQuery.each(board, function (key, value) {
        $(`#${key}`).html(value)
    })

    if (playerCount == 1) {
        printDelay()
    }
    $(`#${"gameId"}`).html(gameId)
    $(`#${"blackScore"}`).html(blackScore)
    $(`#${"whiteScore"}`).html(whiteScore)

}

function switchTurn() {
    if (team === "BLACK") {
        team = "WHITE"
    } else {
        team = "BLACK"
    }
}

function drawWay(data) {
    let jsonData = JSON.parse(data);
    way = jsonData;
    jQuery.each(jsonData, function (key, value) {
        $(`#${value}`).css("background-color", "red")
    })
}

function removeWay() {
    jQuery.each(way, function (key, value) {
        if ((value.charCodeAt(0) + value.charCodeAt(1)) % 2 === 0) {
            $(`#${value}`).css("background-color", "#3d3d3d")
        } else {
            $(`#${value}`).css("background-color", "#d3d3d3")
        }
    })
}

function printWinner() {
    $(`#${"a4"}`).html("Y")
    $(`#${"b4"}`).html("O")
    $(`#${"c4"}`).html("U")
    $(`#${"d4"}`).html(" ")
    $(`#${"e4"}`).html("W")
    $(`#${"f4"}`).html("I")
    $(`#${"g4"}`).html("N")
    $(`#${"h4"}`).html("!")
}

function printLoser() {
    $(`#${"a4"}`).html("Y")
    $(`#${"b4"}`).html("O")
    $(`#${"c4"}`).html("U")
    $(`#${"d4"}`).html(" ")
    $(`#${"e4"}`).html("L")
    $(`#${"f4"}`).html("O")
    $(`#${"g4"}`).html("S")
    $(`#${"h4"}`).html("E")
}

function printDelay() {
    $(`#${"a4"}`).html("연")
    $(`#${"b4"}`).html("결")
    $(`#${"c4"}`).html("대")
    $(`#${"d4"}`).html("기")
    $(`#${"e4"}`).html("중")
    $(`#${"f4"}`).html(".")
    $(`#${"g4"}`).html(".")
    $(`#${"h4"}`).html(".")
}
