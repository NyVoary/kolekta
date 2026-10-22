
$(function () {
    'use strict';

    fetch("/admin/tache/stat")
        .then(val => val.json())
        .then((dt) => {
            console.log(dt);
            var data = {
                labels: ["En Attend", "En Cours", "Terminé"],
                datasets: [{
                    label: '# of Votes',
                    data: [dt["en_attente"], dt["en_cours"], dt["termine"]],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(97, 255, 86, 0.2)',
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgb(97, 255, 86)',
                    ],
                    borderWidth: 1,
                    fill: false
                }]
            };

            var options = {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true, 
                            max: dt['total']
                        }
                    }]
                },
                legend: {
                    display: false
                },
                elements: {
                    point: {
                        radius: 10
                    }
                }
            };

            if ($("#barChart").length) {
                var barChartCanvas = $("#barChart").get(0).getContext("2d");
                var barChart = new Chart(barChartCanvas, {
                    type: 'bar',
                    data: data,
                    options: options
                });
            }
        })
        .catch(console.log)
});