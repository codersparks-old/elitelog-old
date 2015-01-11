function Map() {

		emit(this.name,
			{ "data" :
				[
					{
						"station" : this.station,
						"system" : this.system,
						"supplyLevel" : this.supplyLevel,
						"demandLevel" : this.demandLevel,
						"buy" : this.buy,
						"sell" : this.sell,
						"created" : this.created
					}
				]
			});
	}