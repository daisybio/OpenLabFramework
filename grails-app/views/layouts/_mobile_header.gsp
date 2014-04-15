<div id="header" data-theme="d" data-role="header" class="ui-header ui-bar-d" role="banner">

    <img src="<g:resource dir="images" file="olf_header_mobile.png"/>" alt="OpenLabFramework"/>

    <div data-role="fieldcontain">
        <fieldset data-role="controlgroup">
            <label for="q">
            </label>
            <input name="" id="q" placeholder="Search" value="" type="search">
        </fieldset>
    </div>

</div>

<r:script>
		jQuery("#mainPage").bind("pageshow", function(e) {

			jQuery("#q").autocomplete({
				target: jQuery('#suggestions'),
				source: '${createLink(controller:"quickSearch", action:"searchResultsAsJqueryJSON")}',
				callback: function(e){
                        var a = jQuery(e.currentTarget);
                        var currValue = a.data('autocomplete').value;
                        ${remoteFunction(update:'body', onComplete:'jQuery("div[data-role=page]").page( "destroy" ).page();', controller:"quickSearch", action:"showResult", params: '\'id=\'+currValue')}
                        jQuery('#q').autocomplete('clear');
                },
				minLength: 3
			});

		});
</r:script>


