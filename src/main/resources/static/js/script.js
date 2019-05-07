document.addEventListener("DOMContentLoaded", function () {

    $("table tr").hide();
    $("table tr").each(function (index) {
        $(this).delay(index * 500).show(1000);
    });

    renderChart();

});

function renderChart(){

    var ctx = document.getElementById('myChart').getContext('2d');

    fetch("http://localhost:8080/purchase/chart").then(function (response) {
        return response.json();
    }).then(function (response) {

        if (!(Array.isArray(response) && response.length)) {
            var chartIsEmpty = document.getElementById('chartInfo');
            chartIsEmpty.innerText = "Upps Scrooge - you don't have any savings!!!";
            var nextElem = chartIsEmpty.nextElementSibling;
            nextElem.innerHTML=("Get to work!!!");
        } else{

            var labelsOfChart = [];
        var dataMap = new Map();
        for (const purchase of response) {
            labelsOfChart.push(purchase.product.group.name)
            if (dataMap.has(purchase.product.group.name)) {
                dataMap.set(purchase.product.group.name, dataMap.get(purchase.product.group.name) + purchase.savedMoney);
            } else {
                dataMap.set(purchase.product.group.name, purchase.savedMoney);
            }
        }
        // console.log(dataMap);
        // let x = (labelsOfChart) => labelsOfChart.filter((v,index) => labelsOfChart.indexOf(v) === index)
        // var unique = x(labelsOfChart);


        let keys = Array.from(dataMap.keys());
        let values = Array.from(dataMap.values())

        var chart = new Chart(ctx, {
            // The type of chart we want to create
            type: 'doughnut',

            // The data for our dataset
            data: {
                labels: keys,
                datasets: [{
                    backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                    borderColor: 'rgb(255, 99, 132)',
                    data: values
                }]
            }


        });
    }
})
}