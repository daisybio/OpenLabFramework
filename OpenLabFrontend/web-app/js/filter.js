function showElement(id) {
	try {
		if (typeof Effect != "undefined" && typeof Effect.Appear != "undefined") {
                    if ($(id) && Element.visible(id) == false)
			Effect.Appear(id,{duration:0.5,queue:'end'});
		} else {
			var el = document.getElementById(id)
			if (el && el.style.display == 'none') {
				el.style.display = 'block';
			}
		}
	} catch (err) {alert(err)}
	return false;
}

function hideElement(id) {
	if (typeof Effect != "undefined" && typeof Effect.Fade != "undefined") {
            if ($(id) && Element.visible(id))
		Effect.Fade(id,{duration:0.5,queue:'end'});
	} else {
		var el = document.getElementById(id)
		if (el && el.style.display != 'none') {
			el.style.display = 'none';
		}
	}
	return false;
}

function clearFilterPane(id) {
	var form = document.getElementById(id)
	
	for (var i = 0; i < form.elements.length; i++) {
		var el = form.elements[i]
		if (el.name.indexOf('filter.') == 0) {
			if (el.type == 'select-one') {
				el.selectedIndex = 0
			} else if (el.type == 'text' || el.type == 'textarea') {
				form.elements[i].value = ''
			}
		}
	}
}

function filterOpChange(id, controlId) {
    // id should be of the form op.propertyName
    if (id.slice(0, 10) == 'filter.op.') {
        var prop = id.substring(10)
        var el = document.getElementById(id)
        var selection = el.options[el.selectedIndex]
        if (el) {
            if (el.type == 'select-one') {
                if (selection.value == 'Between') {
                    showElement('between-span-'+prop)
                } else {
                    hideElement('between-span-'+prop)
                }
            }
            
            var containerName = prop+'-container'
            if (selection.value == 'IsNull' || selection.value == 'IsNotNull') {
                hideElement(controlId);
                // Take care of date picker fields we created.
                if (document.getElementById(containerName)) hideElement(containerName)
            } else {
                showElement(controlId);
                // Take care of date picker fields.
                if (document.getElementById(containerName)) showElement(containerName)
            }
        }
    }
}

function selectDefaultOperator(id) {
    var dropdown = document.getElementById(id)
    if (dropdown && dropdown.selectedIndex <= 0) {
        dropdown.selectedIndex = 1
    }
}