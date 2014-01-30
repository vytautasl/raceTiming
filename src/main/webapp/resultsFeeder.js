function executeQuery(dataProvider) {
    $.ajax({
//        url: 'http://217.147.39.56:8080/api/results/liveFeed/'.concat(dataProvider),
        url: 'http://develop.agmis.lt:8082/raceLive/api/results/liveFeed/'.concat(dataProvider),
        type: "GET",
        crossDomain: true,
        dataType: "json",
        timeout: 5000,
        complete: setTimeout(function() {executeQuery(dataProvider)}, 5000),
        success: function(data) {
            var doc = document;
            var fragment = doc.createDocumentFragment();
            var bestLapColumn = false;
            var lastLapColumn = false;
            var totalLapsColumn = false;
            $.each(data.bestLapRowDtoList, function (index, row) {
                var tr = doc.createElement("tr");
                var td = td = doc.createElement("td");
                if (row.racePos!=null)
                {
                    td = doc.createElement("td"); td.innerHTML = row.racePos; tr.appendChild(td);
                } else {
                    td = doc.createElement("td"); td.innerHTML = row.position; tr.appendChild(td);
                }
                td = doc.createElement("td"); td.innerHTML = row.kartName; tr.appendChild(td);
                if (row.bestLapStr!=null)
                {
                    td = doc.createElement("td"); td.innerHTML = row.bestLapStr; tr.appendChild(td);
                    bestLapColumn = true;
                }
                if (row.lastLapStr!=null)
                {
                    td = doc.createElement("td"); td.innerHTML = row.lastLapStr; tr.appendChild(td);
                    lastLapColumn = true;
                }
                if (row.totalLaps!=null)
                {
                    td = doc.createElement("td"); td.innerHTML = row.totalLaps; tr.appendChild(td);
                    totalLapsColumn = true;
                }
                fragment.appendChild(tr);
            });
            var table = doc.createElement("table");
            var headerFragment = doc.createDocumentFragment();
            var tr = doc.createElement("tr");
            var columns = 2;
            td = doc.createElement("td"); td.innerHTML = "<b>Pos</b>"; tr.appendChild(td);
            td = doc.createElement("td"); td.innerHTML = "<b>Name</b>"; tr.appendChild(td);
            if (bestLapColumn)
            {
                td = doc.createElement("td"); td.innerHTML = "<b>Best lap</b>"; tr.appendChild(td); columns++;
            }
            if (lastLapColumn)
            {
                td = doc.createElement("td"); td.innerHTML = "<b>Last lap</b>"; tr.appendChild(td); columns++;
            }
            if (totalLapsColumn)
            {
                td = doc.createElement("td"); td.innerHTML = "<b>Total laps</b>"; tr.appendChild(td); columns++;
            }
            var headerTr = doc.createElement("tr");
            td = doc.createElement("td");
            td.colSpan = columns;
            td.innerHTML = '<b>'+data.sessionName+'</b>';
            headerTr.appendChild(td);

            headerFragment.appendChild(headerTr);
            headerFragment.appendChild(tr);

            table.appendChild(headerFragment);

            var footerTr = doc.createElement("tr");
            td = doc.createElement("td");
            td.colSpan = columns;
            td.innerHTML = 'updated on: '+data.updateTime;
            footerTr.appendChild(td);
            fragment.appendChild(footerTr);

            table.appendChild(fragment);
            table.id = "feedTable";
            table.width="100%";
            var feedElement = doc.getElementById("feedTable");
            var tableContainer = doc.getElementById("resultsContainer");
            if (feedElement==null)
            {
                tableContainer.appendChild(table);
            } else {
                tableContainer.replaceChild(table, feedElement);
            }

        }
    });
}