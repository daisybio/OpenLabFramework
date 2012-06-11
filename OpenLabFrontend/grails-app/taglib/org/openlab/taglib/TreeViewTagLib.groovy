package org.openlab.taglib

class TreeViewTagLib {

	def treeControls = { attrs ->
		out << """<div style="width:180px; class="nav">
			<table><tbody><tr class="odd">
				<td><span onClick="YAHOO.widget.TreeView.getTree('${attrs.id}').expandAll();">expand all</span></td>
				<td><span onClick="YAHOO.widget.TreeView.getTree('${attrs.id}').collapseAll();">collapse all</span></td>
			</tr></tbody></table>
		</div>"""
	}
}
