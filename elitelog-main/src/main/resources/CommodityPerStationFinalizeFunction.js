function Finalize(key, reduced) {

		stationDetails = {};

		data = reduced.data;

		for(var index in data) {

			newStationObj = data[index]
			stationName = newStationObj.station

			existingStationObj = stationDetails[newStationObj.station];


			if( existingStationObj == undefined || newStationObj.created > existingStationObj.created ) {
				delete newStationObj.station
				stationDetails[stationName] = newStationObj;
			}

		}
		return stationDetails;
	}