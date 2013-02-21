package org.openlab.storage;

import org.openlab.main.*;

public class BoxTagLib {

	def settingsService
	def boxCreationService
	
	/**
	 * calls createBoxTable with attribute update to prevent the closure from nesting div tags
	 * when called via Ajax.Update()
	 */
	def updateBoxTable = { attrs ->
			attrs.update = true
			out << createBoxTable(attrs)
	}
	
	
	/**
	 * builds a visual representation of a box in form of a html table
	 */
	def createBoxTable = { attrs ->
		
		def box = Box.get(attrs.id)
			
		def xdim = box.xdim
        def ydim = box.ydim
		def sortedElements = boxCreationService.getSortedBoxElements(attrs.id)
        
        //Problem is that we have nested scrollbars and divs otherwise
        if(!attrs.update)
        {
        	//Register custom event for changes to boxView
        	out << "<script>if(olfEvHandler) olfEvHandler.boxViewChangedEvent = new YAHOO.util.CustomEvent('boxViewChangedEvent', this);</script>"
        	
        	//target for Ajax calls
        	out << "<div id='boxView' style='max-height:400px; overflow: scroll;'>"
        }
        out << showCurrentBox(box: box)

        //build table and header
    	out << "<table border=1>"
        out << "<colgroup><col width=20/></colgroup>"
        out << "<colgroup width=${50*ydim} span=${ydim}></colgroup>" 
        out << "<thead><tr><th></th>"
        
        for(int y = 0; y < ydim; y++)
        {
        	out << "<th>${boxCreationService.getAxisLabel(y, 'y')}</th>"
        }
        
		out << "</tr></head><tbody>"
		
		//StringBuffer to avoid using 'out' all the time
		def stringBuffer = new StringBuffer()
		//initialization values
		def x = 0
		def y = -1
		//begin first row
		stringBuffer << "<tr><th>${boxCreationService.getAxisLabel(x, 'x')}</th>"
		
		if(sortedElements.size() == 0)
		{
			while((x < xdim) && (y < ydim))
			{
				y++
				//go to next row and create first column
				if(y == ydim)
				{
					y = 0
					x++
					//exit when last row processed
					if(x == xdim) break
					stringBuffer << "</tr><tr><th>${boxCreationService.getAxisLabel(x, 'x')}</th>"
				}
				if(!attrs.addToCell)
				{
					stringBuffer << """<td height=20 width=100 style='background-color:#ffaaaa;'/>"""
				}
				else
				{
                    stringBuffer << """<td onClick="${remoteFunction(before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', onSuccess: 'javascript:if(olfEvHandler)olfEvHandler.boxViewChangedEvent.fire();', controller:'box', action:'addDataObject', params: '\'x=' + x +'&y=' + y + '&boxId=' + attrs.id + '&subDataObj=' + params.subDataObj + '\'', id: params.id, update:[success:'boxView', failure:'boxView'])}" height=20 width=100 style='background-color:#ffaaaa;'>${gui.toolTip(text: 'Click to add'){'Click to add'}}</td>"""
				}
			}
		}
		
		sortedElements.each{element ->
			while((x < xdim) && (y < ydim))
			{
				y++
				//go to next row and create first column
				if(y == ydim)
				{
					y = 0
					x++
					//exit when last row processed
					if(x == xdim) break
					stringBuffer << "</tr><tr><th>${boxCreationService.getAxisLabel(x, 'x')}</th>"
				}
				//println element
				//println "last"+ sortedElements.last()
				//check if cell has content
				if(x == element.xcoord && y == element.ycoord)
				{
        			//element is another one than the one displayed above and a click shall OPEN it.
        			if(element.dataObj.id.toString() != params.id.toString()){
        				stringBuffer << """<td onClick="${remoteFunction(before: '\$(\'body\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', controller:'dataObject', action:'showSubClass',params: [backlink: params.backlink], id: element.dataObj.id, update:[success:'body', failure:'error'])}"
			 				 height=20 width=100 style='background-color:#ffffaa;'>${gui.toolTip(text: 'Click to open'){element.toString()}}</td>"""
        			}
                    //element is the one displayed above, but has a different sub object, e.g. different passage number
                    else if(element.dataObj.id.toString() == params.id.toString() && params.subDataObj && params.subDataObj != "null" && element.subDataObj?.id?.toString() != params.subDataObj.toString())
                        stringBuffer << """<td onClick="${remoteFunction(before: '\$(\'subDataStorageTab\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', controller:'storage', action:'storageTabWithSubDataObj',params: '\'boxId=' + attrs.id + '&subDataObj=' + element?.subDataObj?.id + '\'', id: element.dataObj.id, update:[success:'subDataStorageTab', failure:'error'])}"
			 				 height=20 width=100 style='background-color:#aaaaff;'>${gui.toolTip(text: 'Click to select'){element.toString()}}</td>"""
	 				//element is the one displayed above and it shall be possible to REMOVE it
    				else{
        				stringBuffer << """<td onClick="${remoteFunction(before: 'if(!confirm(\'Are you sure?\')) return false; \$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', onSuccess: 'javascript:if(olfEvHandler)olfEvHandler.boxViewChangedEvent.fire();', controller:'box', action:'removeDataObject',
                                params: '\'x=' + x + '&y=' + y + '&boxId=' + attrs.id + '\'', id: params.id, update:[success:'boxView', failure:'boxView'])}"
		 				 height=20 width=100 style='background-color:#aaffaa;'>${gui.toolTip(text: 'Click to remove'){element.toString()}}</td>"""    					
    				}
        			
        			//continue with next element
        			//if current element is the last one loop to generate remaining empty cells
        			if(sortedElements.last() != element)
        				break
				}
				//fill in empty cells
				//A click shall enable the user to ADD the current object here
    			else
    			{
    				if(!attrs.addToCell)
    				{
    					stringBuffer << """<td height=20 width=100 style='background-color:#ffaaaa;'/>"""
    				}
    				else
    				{
    					stringBuffer << """<td onClick="${remoteFunction(before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', onSuccess: 'javascript:if(olfEvHandler)olfEvHandler.boxViewChangedEvent.fire();', controller:'box', action:'addDataObject', params: '\'x=' + x +'&y=' + y + '&boxId=' + attrs.id + '&subDataObj=' + params.subDataObj + '\'', id: params.id, update:[success:'boxView', failure:'boxView'])}" height=20 width=100 style='background-color:#ffaaaa;'>${gui.toolTip(text: 'Click to add'){'Click to add'}}</td>"""
    				}
    			}
			}		
		}
		
		out << stringBuffer

		out << "</tbody></table>"

        //link to create XLS export
        out << "<a href='${createLink(controller: "box", action: "export", id: attrs.id)}'>Export as XLS</a>"

		if(!attrs.update)
		{
			out <<"</div>"
		}
	}
	
	/**
	 * Closure to create an output for a tab where a box and its location 
	 * is displayed if given for the corresponding element.
	 */
	def showBoxInTab = { attrs ->
		out << "<div id='storageTab'>"

        def storageElts

        if(params.subDataObj) storageElts = StorageElement.findAllByDataObjAndSubDataObj(DataObject.get(params.id), SubDataObject.get(params.subDataObj))
        else storageElts = StorageElement.findAllByDataObj(DataObject.get(params.id))

		def boxes = []
        def subDataObjs = [:]
        storageElts.each {
            boxes.add(it.box)
            if(it.subDataObj){
                if(!subDataObjs.get(it.box.id)) subDataObjs.put(it.box.id, [it.subDataObj])

                else{
                    def currentEntry = subDataObjs.get(it.box.id)
                    currentEntry.add(it.subDataObj)
                    subDataObjs.put(it.box.id, currentEntry)
                }
            }
        }
		boxes.unique()

		out << "<div id='storageTree'>"
        out << "</div><div class='message' id='unitsLeft'>"
		out << numberOfEntitiesLeft()
		out << "</div>"

		if(storageElts)
		{
			def storageElt = storageElts[0]
			
			//Link to other boxes with the same elt
			if(boxes.size() > 1)
			{
				out << "Entities can be found in the following boxes:<br>"
				out << "<ul style='margin-left:25px;'>"
				boxes.each{box ->
					out << "<li>${remoteLink(before: '\$(\'boxView\').update(\'<img src='+createLinkTo(dir:'/images',file:'spinner.gif')+' border=0 width=16 height=16/>\')', onSuccess: 'javascript:if(olfEvHandler)olfEvHandler.boxViewChangedEvent.fire();', controller:'box', action:'showBoxInTab', update:[success:'boxView', failure:'boxView'], id: params.id, params: '\'subDataObj='+ params.subDataObj + '&boxId=' + box.id + '\''){box.toString().replace(' - ', ' >> ')}}"
                    if(subDataObjs.get(box.id)) out << " | " + subDataObjs.get(box.id).toString()
                    out << "</li>"
				}
				out << "</ul><br><br>"
			}
			
			//create the actual box
			out << g.createBoxTable(id: params.boxId?:storageElt.box.id, addToCell:true)
		}
		//if element has not yet been stored use the box that was least recently used
		else if(Box.count() > 0)
		{
			//show box that has been modified least recently
            def leastRecentlyUpdatedBox = Box.find("FROM Box ORDER BY lastUpdate DESC")

			if(leastRecentlyUpdatedBox)
			{

				out << g.createBoxTable(id: params.boxId?:leastRecentlyUpdatedBox.id, addToCell:true)
			}
		}
		else out << "There are no boxes to show. Please edit your storage configuration."
		
		out << "</div>"
		
		out << registerEventHandlers()
	}
	
	/**
	 * Register a JS listeners to listen to events e.g. for update of numberOfEntitiesLeft
	 */
	def registerEventHandlers = {
			 
			out << """
			<script> 
			var updateEntitiesLeft = function(type, args, me) {
				${remoteFunction(controller:'box', action:'numberOfEntitiesLeft', params: params, update:[success: 'unitsLeft', error: 'unitsLeft'])}
			}
			
			var updateShowCurrentBoxLabel = function(type, args, me) {
				if(args.length > 0)	${remoteFunction(controller:'box', action:'currentBox', params: '\'boxId=\'+args[0]', update:[success: 'currentBoxLabel', error: 'currentBoxLabel'])}
			}
			if(olfEvHandler){
			    olfEvHandler.boxViewChangedEvent.subscribe(updateEntitiesLeft, this);
			    olfEvHandler.boxViewChangedEvent.subscribe(updateShowCurrentBoxLabel, this);
            }
			</script>"""
	}
	
	def showCurrentBox = { attrs ->
			out << "You are here: <span style='padding:5px;'><b>" + attrs.box.toString().replace(' - ', ' >> ') + remoteLink(controller: 'storage', action: 'showTree', update: 'storageTree', params: [id: params.id, subDataObj: params.subDataObj, treeInTab: true]){' (Change) '}+"</span> <br><br>"
	}
	
	/**
	 * Closure that calculates how many units are left and produces an output string
	 */
	def numberOfEntitiesLeft = {

			def numberOfEntitiesLeft

            if(params.subDataObj) numberOfEntitiesLeft = StorageElement.countByDataObjAndSubDataObj(DataObject.get(params.id), SubDataObject.get(params.long("subDataObj")))
            else numberOfEntitiesLeft = StorageElement.countByDataObj(DataObject.get(params.id))
			
			if(numberOfEntitiesLeft == 1)
			{
				//flash.message = "<b>There is only 1 entity left</b>" 
				out << "<b>There is only 1 entity left</b>"
			}
			else
				out << "There are ${numberOfEntitiesLeft} units left."
	}
}
