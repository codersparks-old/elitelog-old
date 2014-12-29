var commodityTable;

$(document).ready(function() {
    
    var commodities = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 10,
        prefetch: {
            url: '/api/commodities/distinct',
            filter: function(response) {
                //console.log(list['commodities'])
                
                return $.map(response['commodities'], function(country) { return { name: country }; });
            },
			ttl: 60000
        }
    });
	
    commodities.initialize();

	updateCommodityTable("");
	
    $('#commodities-typeahead').typeahead(null, {
       name: 'commodities',
       displayKey: 'name',
       source: commodities.ttAdapter()
    });
        
	// Give the user the option to refresh the data list
	$('#commodity-refresh-btn').on('click', function() {
		console.log("Refresh pressed");
		commodities.clearPrefetchCache();
		commodities.clearRemoteCache();
		commodities.initialize(true);
	})
	
	// Enable the tooltops on this page
	 $('[data-toggle="tooltip"]').tooltip()
	
    
	
	$('#commodity-show-btn').on('click', function() {
		updateCommodityTable($('#commodities-typeahead').val());
	});
    
	// Register the display of the help modal to the help div
	$('#commodities-help').on('click', function() {
		console.log("Help button pressed");
		$('#helpModal').modal()
	});
	
	
	
	
});

var commodityToDataTable = function(sSource, aoData, fnCallback) {
	//extract name/value pairs into a simpler map for use later
	var paramMap = {};
	for (var i = 0; i < aoData.length; i++) {
		paramMap[aoData[i].name] = aoData[i].value;
	}

	//page calculations
	var pageSize = paramMap.iDisplayLength;
	console.log("PageSize: " + pageSize)
	var start = paramMap.iDisplayStart;
	var pageNum = (start == 0) ? 1 : (start / pageSize) + 1; // pageNum is 1 based

	// extract sort information
	var sortCol = paramMap.iSortCol_0;
	var sortDir = paramMap.sSortDir_0;
	var sortName = paramMap['mDataProp_' + sortCol];

	//create new json structure for parameters for REST request
	var restParams = new Array();
	restParams.push({
		"name" : "size",
		"value" : pageSize
	});
	restParams.push({
		"name" : "page",
		"value" : pageNum
	});
	restParams.push({
		"name" : "sort",
		"value" : sortName + "," + sortDir
	});
	
	console.log("Sort Name: " + sortName)
	// TODO: Add filter "stuff" here
	var url = sSource;
	
	console.log("Commodities Datatable URL: " + url)
	console.log("Commodities Datatable Rest Params: " + JSON.stringify(restParams))
	
	// finally, make the request
	$.ajax({
		"dataType" : "json",
		"type" : "GET",
		"url" : url,
		"data" : restParams,
		"success" : function(data) {
			console.log("Commodities datatables ajax returned: " + JSON.stringify(data))
			data.iTotalRecords = data.page.totalElements;
			data.iTotalDisplayRecords = data.page.totalElements;
			fnCallback(data);
		}
	});

};

function updateCommodityTable(commodity) {
	
	console.log(commodityTable)
	
	if (commodityTable != undefined) {
		commodityTable.fnDestroy();
	}
	
	var url;
	
	if (commodity) {
		url = "/api/commodities/search/byName?name=" + commodity;
	} else {
		url = "/api/commodities/";
	}
	
	console.log("Updating table for: " + commodity);
	// Convert the commodity table into a dataTable
	commodityTable = $('#commodity-table').dataTable({
		"sDom" : 'R<"row"<"col-sm-6"l><"col-sm-6"C>><"row"<"col-sm-12"t>><"row"<"col-sm-6"i><"col-sm-6"p>>',
		"sAjaxSource" : url,
		"sAjaxDataProp" : "_embedded.commodities",
		"aoColumns" : [
			{mDataProp:'station'},
			{mDataProp:'system'},
			{mDataProp:'name'},
			{mDataProp:'buy'},
			{mDataProp:'sell'},
			{mDataProp:'supplyLevel'},
			{mDataProp:'demandLevel'},
			{mDataProp:'created'}
		],
		"bServerSide" : true,
		"fnServerData" : commodityToDataTable
	});
}