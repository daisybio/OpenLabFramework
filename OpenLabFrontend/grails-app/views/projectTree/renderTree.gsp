<g:javascript src="/jquery-1.8.2.min.js"/>
<g:javascript src="/jquery.jstree.js"/>
<div id="projectTree">

</div>


<script type="text/javascript">
        jQuery.noConflict();

        var myTree = jQuery("#projectTree").jstree({
            "json_data": {
                "ajax": {
                    "url":  "<g:createLink controller="projectTree" action="projectTreeAsJSON"/>",
                    "data" : function(n) { return { id: n.attr? n.attr("id") : 0, nodeType: n.attr? n.attr("nodeType") : "root" }; }
                }
            },
            "themes" : {
                "theme" : "default",
                "url" : "<g:resource dir="themes" file="default/style.css"/>",
                "icons" : false
            },
            "types" : {
                "valid_children" : ["project"],
                "types" : {
                    "project" : {
                        "hover_node": false,
                        "valid_children": ["gene"]
                    },
                    "gene" : {
                        "valid_children": ["cellLineData,geneVector"]
                    },
                    "geneVector" : {
                        "valid_children": ["cellLineData"]
                    },
                    "default": {
                        "valid_children": ["default"]
                    }
                }
            },
            "plugins": ["themes", "ui", "json_data", "types"]
        });

        myTree.bind("select_node.jstree", function(event, data){
            if(data.inst.is_root(data.args[0])){
                ${remoteFunction(action: 'show', controller: 'project', update: 'body', params: '\'id=\'+data.rslt.obj.attr("id")+\'&bodyOnly=\'+true')}
            }
            else{
                ${remoteFunction(action: 'showSubClass', controller: 'dataObject', update: 'body', params: '\'id=\'+data.rslt.obj.attr("id")')}
            }
    });
</script>