$('th.tm-select-all').click(function(e) {
 	if(!$(e.target).is('input')){
 		$(this).find('input:checkbox').click();
	}
});

$('th.tm-select-all input:checkbox').change(function() {
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

    $('td.tm-select-all').click(function (e) {
    	
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
    
    $('.tm-list #searchInput').keypress(function(e) {
		if (e.which == 13) {
			jQuery(this).closest("form").submit();
		}
	});

    $('#pageSize').change(function() {
    	$(this).closest('form').submit();
    });

    $('#recordsFilter').change(function() {
        $(this).closest('form').submit();
    });

    $("input:checkbox").change(function() {
    	$('input:checkbox[name=id]:checked').closest('tr').addClass('tm-selected');
    	$('input:checkbox[name=id]:not(:checked)').closest('tr').removeClass('tm-selected');
    });
    
});
