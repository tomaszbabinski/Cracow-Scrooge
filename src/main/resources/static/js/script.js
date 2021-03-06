document.addEventListener("DOMContentLoaded", function () {

    $("table tr").hide();
    $("table tr").each(function (index) {
        $(this).delay(index * 500).show(1000);
    });

    renderChart();
    renderBestOffersPerProduct();
    setInterval(renderBestOffersPerProduct, 1000 * 15);

});

function resize() {
    console.log("hi");
}

function renderChart() {

    var ctx = document.getElementById('myChart').getContext('2d');
    var totalSavings = document.getElementById('totalSavings');
    var savings;

    fetch("/purchase/chart").then(function (response) {
        return response.json();
    }).then(function (response) {

        if (!(Array.isArray(response) && response.length)) {
            var chartIsEmpty = document.getElementById('chartInfo');
            chartIsEmpty.innerText = "Upps Scrooge - you don't have any savings!!!";
            var nextElem = chartIsEmpty.nextElementSibling;
            nextElem.innerHTML = ("Get to work!!!");
        } else {

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
            let borderColor = 'rgb(151, 166, 234)';

            var chart = new Chart(ctx, {
                // The type of chart we want to create
                type: 'doughnut',

                // The data for our dataset
                data: {
                    labels: keys,
                    datasets: [{
                        backgroundColor: ["#33658a", "#f26419", "#f6ae2d", "#86bbd8"],
                        borderColor: borderColor,
                        colors: "#FFFFFF",
                        data: values
                    }]
                },

                options: {
                    legend: {
                        labels: {
                            fontColor: 'white',
                            fontSize: 20
                        }
                    },
                    responsive: true
                }


            });

            savings = values.reduce(getSum)
            savings = Math.round(savings * 100) / 100;
            totalSavings.innerText = "Hey Scrooge you managed to save " + savings.toString() + " money!!!";


        }
    })
}

function getSum(total, num) {
    return total + num;
}

function renderBestOffersPerProduct() {


    fetch("/offer/bestOffers").then(function (response) {
        return response.json();
    }).then(function (response) {

        var bestOfferDiv = document.getElementById('bestOffers');

        if (!(Array.isArray(response) && response.length)) {
            bestOfferDiv.innerHTML = ("<h4>Add some products and offers to be a descent Scrooge!!</h4>");
        } else {


            var dataMap = new Map();
            var shopMap = new Map();
            var efficiencyMap = new Map();
            for (const offer of response) {

                dataMap.set(offer.product.brand + " " + offer.product.name, offer.price);
                shopMap.set(offer.product.brand + " " + offer.product.name, offer.shopName);
                efficiencyMap.set(offer.product.brand + " " + offer.product.name, offer.product.averageEfficiency);
            }

            if (dataMap.size >= 3) {

                let shopNames = Array.from(shopMap.values());
                let efficiencies = Array.from(efficiencyMap.values());
                let keys = Array.from(dataMap.keys());
                let values = Array.from(dataMap.values());
                var table = " <table class=\"table table-striped mt-sm-4\">\n" +
                    "<caption style='text-align: center'>Your best offers</caption>\n" +
                    "        <thead class=\"thead-light\">\n" +
                    "\n" +
                    "        <th scope=\"col\">Name</th>\n" +
                    "        <th scope=\"col\">Price</th>\n" +
                    "        <th scope=\"col\">Shop</th>\n" +
                    "        <th scope=\"col\">Efficiency</th>\n" +
                    "        </thead>\n" +
                    "        <tbody>";
                var randomDataToshow = new Set();

                while (randomDataToshow.size < 3) {
                    randomDataToshow.add(Math.floor(Math.random() * (keys.length - 0) + 0));
                }

                var arrayFromData = Array.from(randomDataToshow);

                table += "<tr><td>" + keys[arrayFromData[0]] + "</td><td>" + values[arrayFromData[0]] + "</td><td>" + shopNames[arrayFromData[0]] + "</td><td>" + efficiencies[arrayFromData[0]] + "</td></tr>";
                table += "<tr><td>" + keys[arrayFromData[1]] + "</td><td>" + values[arrayFromData[1]] + "</td><td>" + shopNames[arrayFromData[1]] + "</td><td>" + efficiencies[arrayFromData[1]] + "</td></tr>";
                table += "<tr><td>" + keys[arrayFromData[2]] + "</td><td>" + values[arrayFromData[2]] + "</td><td>" + shopNames[arrayFromData[2]] + "</td><td>" + efficiencies[arrayFromData[2]] + "</td></tr>";
                table += "</tbody></table>";
                bestOfferDiv.innerHTML = table;

            }
        }
    })
}
