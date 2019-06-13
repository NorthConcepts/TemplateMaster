$('.toggleSelectAll-cell').click(function(e) {
 	if(!$(e.target).is('input')){
 		$(this).find('input:checkbox').click();
	}
});

$('#toggleSelectAll').change(function() {
    var checkboxes = $('input[name=id]');
    checkboxes.prop('checked', $(this).is(':checked'));
    if ($('input[name=id]:checked').length) {
        $('[type=submit]').removeAttr('disabled');
    } else {
    	$('[type=submit]').attr('disabled', 'disabled');
    }
});

$(document).ready(function() {
    var checkboxes = $('input[name=id]');
    var lastChecked = null;

    $('.checkbox-cell').click(function (e) {
    	
    	var checkbox = checkbox = $(this).find('input:checkbox');
     	if(!$(e.target).is('input')){
    		checkbox.prop('checked', !$(checkbox).is(':checked'));
    	}
     	
    	if(lastChecked) {
            if(e.shiftKey) {
                var start = checkboxes.index(checkbox);
                var end = checkboxes.index(lastChecked);

            	checkboxes.slice(Math.min(start,end), Math.max(start,end)+ 1).prop('checked', $(lastChecked).is(':checked'));
            }
        }

        lastChecked = checkbox;
        
        if ($('input[name=id]:checked').length) {
            $('[type=submit]').removeAttr('disabled');
        } else {
        	$('[type=submit]').attr('disabled', 'disabled');
        }

    });
});


