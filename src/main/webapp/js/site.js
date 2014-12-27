var datatable2Rest = function(sSource, aoData, fnCallback) {

			//extract name/value pairs into a simpler map for use later
			var paramMap = {};
			for (var i = 0; i < aoData.length; i++) {
				paramMap[aoData[i].name] = aoData[i].value;
			}

			//page calculations
			var pageSize = paramMap.iDisplayLength;
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

			//if we are searching by name, override the url and add the name parameter
			var url = sSource;
			if (paramMap.sSearch != '') {
				url = "http://localhost:8080/api/customers/search/findByNameContains";
				var nameParam = '%' + paramMap.sSearch + '%'; // add wildcards
				restParams.push({
					"name" : "name",
					"value" : paramMap.sSearch
				});
			}
                        
                        console.log("URL: " + url)
			console.log("Rest Params: " + JSON.stringify(restParams))

			//finally, make the request
			$.ajax({
				"dataType" : 'json',
				"type" : "GET",
				"url" : url,
				"data" : restParams,
				"success" : function(data) {
					console.log("Data: " + JSON.stringify(data))
					data.iTotalRecords = data.page.totalElements;
					data.iTotalDisplayRecords = data.page.totalElements;
					fnCallback(data);

				}
			});
		};
		$('#customers').dataTable({
			"sAjaxSource" : 'http://localhost:8080/api/customers',
			"sAjaxDataProp" : '_embedded.customers',
			"aoColumns" : [ {
				mDataProp : 'name'
			}, {
				mDataProp : 'email'
			}, {
                                mDataProp : 'favoriteColor'
                        }],
			"bServerSide" : true,
			"fnServerData" : datatable2Rest
		});