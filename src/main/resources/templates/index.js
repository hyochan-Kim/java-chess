document.addEventListener("DOMContentLoaded", function () {

    $('.btn-makeRoom').click(async function () {
        gameName = $('#makeRoomName').val()
        $.ajax({
            type: "GET",
            url: "/start",
            contentType: "application/json",
            success: function (data) {
                gameId = JSON.parse(data).gameId
                window.location.href = "board.html?id=" + gameId
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });

    $('.btn-joinRoom').click(async function () {
        gameName = $('#joinRoomName').val()
        $.ajax({
            type: "POST",
            url: "/load",
            data: {
                gameId: gameId
            },
            success: function (data) {
                console.log(data)
                window.location.href = "board.html?id=" + gameId
            },
            error: function (e) {
                console.log(e.message);
            }
        });
    });
});
