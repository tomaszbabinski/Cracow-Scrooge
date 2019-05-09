document.addEventListener("DOMContentLoaded", function () {

    $("table tr").hide();
    $("table tr").each(function (index) {
        $(this).delay(index * 500).show(1000);
    });

    var startButton = document.getElementById('start')
    if(startButton!==null) {
        startButton.addEventListener("click", startCounting);
    }

    var stopButton = document.getElementById('stop')
    if(stopButton!==null) {
        stopButton.addEventListener("click", stopCounting);
    }


});

function startCounting() {

    var url = this.getAttribute('url');
    fetch(url).then(function (response) {
        return response.json();
    }).then(function (response) {
        var newUrl = url.replace("start","getById");
        window.location.replace(newUrl);

    })
}

function stopCounting() {

    var url = this.getAttribute('url');
    fetch(url).then(function (response) {
        return response.json();
    }).then(function (response) {
        // var newUrl = url.replace("start","getById");
        window.location.replace("/home");

    })
}

