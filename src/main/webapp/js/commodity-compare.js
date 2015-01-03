var commodityTable;



$(document).ready(function() {
    
    var commodities = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 20,
        prefetch: {
            url: '/api/commodities/distinct',
            filter: function(response) {
                //console.log(list['commodities'])
                
                return $.map(response['commodities'], function(commodityValue) { return { name: commodityValue }; });
            },
			ttl: 60000
        }
    });
	
    commodities.initialize();

	// To load all the data on page load uncomment this line
	//updateCommodityTable("");
	
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
	});
	
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
			console.log("Commodities datatables ajax returned: " + JSON.stringify(data));
			//commodityArray = data._embedded.commodities;
			//for(var i = 0; i < commodityArray.length; i++) {
			//	item = commodityArray[i];
			//	var createdDate = new Date(item.created);
			//	item.created = createdDate.toLocaleString("en-GB") + '<input type="hidden" class="comod-timestamp" value="' + createdDate.getTime() +'" />';
			//}
			//console.log("Commodities Array: " + JSON.stringify(commodityArray));
			data.iTotalRecords = data.page.totalElements;
			data.iTotalDisplayRecords = data.page.totalElements;
			fnCallback(data);
		}
	});

};

function updateCommodityTable(commodity) {
	
	
	
	currentTime = Date.now();
	
	console.log(commodityTable)
	
	var url;
	
	if (commodity) {
		url = "/api/commodities/search/byName?name=" + commodity;
	} else {
		alert("You must specify a commodity")
		return
	}
	
	if (commodityTable != undefined) {
		commodityTable.fnDestroy();
	}
	
	
	console.log("Updating table for: " + commodity);
	// Convert the commodity table into a dataTable
	commodityTable = $('#commodity-table').dataTable({
		"sDom" : '<"row"<"col-sm-6"l><"col-sm-6"C>><"row"<"col-sm-12"t>><"row"<"col-sm-6"i><"col-sm-6"p>>',
		"aaSorting" : [7,'Desc'],
		"sAjaxSource" : url,
		"sAjaxDataProp" : "_embedded.commodities",
		"aoColumns" : [
			{mDataProp:'station', sClass:'comod-station'},
			{mDataProp:'system', sClass:'comod-system'},
			{mDataProp:'name', sClass:'comod-name', 'bSortable':false},
			{mDataProp:'buy', sClass:'comod-buy'},
			{mDataProp:'supplyLevel', sClass:'comod-supplyLevel'},
			{mDataProp:'sell', sClass:'comod-sell'},
			
			{mDataProp:'demandLevel', sClass:'comod-demandLevel'},
			{
				mData:'created',
				mRender: function( data, type, full) {
					return new Date(data).toLocaleString("en-GB")
				},
				sClass:'comod-created',
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					console.log("nTD: " +nTd);
					
					createdDate = new Date(sData).getTime();
					
					diffDate = currentTime - createdDate;
					
					dayMilli = 86400000;
					
					var bgColor;
					
					if (diffDate <= (2 * dayMilli)) {
						bgColor = lightGreen;
					} else if (diffDate <= (5 * dayMilli)) {
						bgColor = lightYellow;
					} else {
						bgColor = lightRed;
					}
					
					$(nTd).css("background-color", bgColor);
					//console.log("sDate: " + sData);
					//console.log("oDate: " + JSON.stringify(oData));
					//console.log("iRow: " + iRow);
					//console.log("iCol: " + iCol);
					//timestamp = $(sData).find(".comod-timestamp").val();
					//console.log("Timestamp: " + timestamp);
				}
			}
		],
		"bSort" : true,
		"bServerSide" : true,
		"fnServerData" : commodityToDataTable
	});
	
	
	// Unhide the table incase it is hidden
	$('#commodity-table').removeAttr('hidden');
	
}