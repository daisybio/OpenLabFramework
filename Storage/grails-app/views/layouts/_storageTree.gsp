<script type="text/javascript">
    jQuery.noConflict();

    var myStorageTree = jQuery("#storageTree").jstree({
        "json_data": {
            "ajax": {
                "url":  "<g:createLink controller="storage" action="treeDataAsJSON"/>",
                "data" : function(n) { return { id: n.attr? n.attr("id") : 0, nodeType: n.attr? n.attr("nodeType") : "root" }; }
            }
        },
        "themes" : {
            "theme" : "default",
            "url" : "<g:resource dir="themes" file="default/style.css"/>",
            "icons" : false
        },
        "types" : {
            "valid_children" : ["location"],
            "types" : {
                "location" : {
                    "valid_children": ["storageType"]
                },
                "storageType" : {
                    "hover_node": false,
                    "valid_children": ["compartment"]
                },
                "compartment" : {
                    "valid_children": ["box"]
                } ,
                "box": {

                }
            }
        },
        "plugins": ["themes", "ui", "json_data", "types"]
    });

    myStorageTree.bind("select_node.jstree", function(event, data){

        if(data.inst.is_leaf(data.args[0]) && data.rslt.obj.attr("nodeType") == "box"){
            <g:if test="${params.treeInTab}">
                ${remoteFunction(action: 'showBoxInTab', controller: 'box', update: 'boxView', id: params.id, params: '\'subDataObj=' + params.subDataObj[0] + '&boxId=\'+data.rslt.obj.attr("id")')}
            </g:if>
            <g:else>
                ${remoteFunction(action: 'showBox', controller: 'box', update: 'boxView', params: '\'id=\'+data.rslt.obj.attr("id")')}
            </g:else>
        }
    });
</script>