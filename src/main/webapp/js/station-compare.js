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
    
    
   
//    var stationsBloodhound = new Bloodhound({
//        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
//        queryTokenizer: Bloodhound.tokenizers.whitespace,
//        limit: 20,
//        prefetch: {
//            url: '/api/stations/distinct',
//            filter: function(response) {
//                //console.log(list['commodities'])
//                
//                return $.map(response['stations'], function(station) { return { name: station }; });
//            },
//			ttl: 60000
//        }
//    });
//    
//    stationsBloodhound.initialize();
//    
//    $('#stations-typeahead').typeahead(
//        {
//            hint: true,
//            highlight: true,
//            limit: 1,
//        },
//        {
//        name: 'stations',
//        displayKey: 'name',
//        source: stationsBloodhound.ttAdapter()
//    });
//    
//    // Give the user the option to refresh the data list
//	$('#station-refresh-btn').on('click', function() {
//		console.log("Refresh pressed");
//        
//		stationsBloodhound.clearPrefetchCache();
//		stationsBloodhound.clearRemoteCache();
//        stationsBloodhound.initialize(true);
//	});
    
    


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
	$.ajax({
        type: "POST",
        url: "/api/commodities/stationPricePerCommodity"
    }).done(function(data) {
        generateTable(stationList, data);
    });
};

function generateTable(stationList, data) {
	
	$('#data-table').empty();
	
	commodityDetails = data.content;
	console.log(commodityDetails);
	console.log("3StationList: " + stationList);
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
	
	
	
	console.log(header)
	
}