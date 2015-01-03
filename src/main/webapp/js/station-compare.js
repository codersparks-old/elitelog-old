var selectedStationList = [];
var stationTypeAhead;

var getStationList = function() {
    $.ajax({
        type: "POST",
        url: "/api/stations/distinct"
    }).done(function(data) {
        stationList = data.stations;
        initialiseStationTypeAhead($('#stations-typeahead'));
        
    });
}

$(document).ready(function() {
    
    getStationList();
    
    $('#station-refresh-btn').on('click', function() {
        console.log("Refresh pressed");
        console.log(stationList);
        getStationList();
    });
    
    // Register the display of the help modal to the help div
	$('#help-icon').on('click', function() {
		console.log("Help button pressed");
		$('#helpModal').modal()
	});
	
	$('#station-update-btn').on('click', function() {
		var selectedStationList = [];
		selectedObjects = stationTypeAhead.getSelection();
		for(i=0; i < selectedObjects.length; i++) {
			selectedStationList.push(selectedObjects[i].name);
		}
		console.log("StationList: " + selectedStationList);
		
		updateTable(selectedStationList);
	});
});

var initialiseStationTypeAhead = function() {
    
    maxSelection = 3
    stationTypeAhead = $('#stations-typeahead').magicSuggest({
       data: stationList,
       highlight: false,
       allowFreeEntries: false,
       maxSelection: maxSelection,
       useTabKey: true,
    });
    if (stationTypeAhead.getSelection().length == 0 && stationList.length <= maxSelection) {
        stationTypeAhead.setSelection(stationTypeAhead.getData());
    }
}

function updateTable(stationList) {
	console.log("2StationList: " + stationList)
	
	generateTableHtml(stationList);
	
	dataTableConfig = buildDataTablesConfig(stationList);
	
	console.log(dataTableConfig);
	
//	$.ajax({
//        type: "POST",
//        url: "/api/commodities/stationPricePerCommodity"
//    }).done(function(data) {
//        generateTable(stationList, data);
//    });
};

function generateTableHtml(stationList) {
	
	$('#data-table').empty();
	
	
	
	// Generate headers
	header = '<thead><tr><th rowspan=2>Commodity</th>';
	
	for(i = 0; i< stationList.length; i++ ) {
		header += "<th colspan='4'>" + stationList[i] + "</th>";
	}
	header += '</tr><tr>'
	
	for(i=0; i < stationList.length; i++) {
		header += "<th>Buy</th><th>Supply Level</th><th>Sell</th><th>Demand Level</th>"
	}
	header += '</tr></thead>'
	
	body = "<tbody></tbody>"
	
	
	table = header + body;
	$('#data-table').append(table);
	$('#data-table').removeAttr('hidden');
	
};

function buildDataTablesConfig(stationList) {
	var retVal = {};
	
	retVal["sDom"] = '<"row"<"col-sm-6"l><"col-sm-6"C>><"row"<"col-sm-12"t>><"row"<"col-sm-6"i><"col-sm-6"p>>';
	
	
	return retVal;
		
}