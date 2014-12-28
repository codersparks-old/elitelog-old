$(document).ready(function() {
    
    var commodities = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 10,
        prefetch: {
            // url points to a json file that contains an array of country names, see
            // https://github.com/twitter/typeahead.js/blob/gh-pages/data/countries.json
            url: '/api/commodities/distinct',
            // the json file contains an array of strings, but the Bloodhound
            // suggestion engine expects JavaScript objects so this converts all of
            // those strings
            filter: function(list) {
                //console.log(list['commodities'])
                
                return $.map(list['commodities'], function(country) { return { name: country }; });
            }
        }
    });
    
    commodities.initialize();
    
    $('#commodities-typeahead').typeahead(null, {
       name: 'commodities',
       displayKey: 'name',
       source: commodities.ttAdapter()
    });
    
    $('.tt-dropdown').css('background-color','#fff');
	$('.tt-hint').addClass('form-control');
    
    
    //console.log(commodities)
    
    // Convert the commodity table into a dataTable
	$('#commodity-table').dataTable();
    // Convert the 
                
});