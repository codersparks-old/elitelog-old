function Reduce(key, values) {

		var reduced = {"data":[]};

		for(var i in values) {
			var inter = values[i];
			for(var j in inter.data) {
				reduced.data.push(inter.data[j]);
			}
		}


		return reduced;
	}