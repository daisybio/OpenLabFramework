/*
 * Copyright (C) 2013 
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:		http://www.nanocan.org
 * ###########################################################################
 *	
 *	This file is part of OpenLabFramework.
 *
 *  OpenLabFramework is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.openlab.module.menu

import org.openlab.module.*;
import groovy.xml.MarkupBuilder

class MasterDataMenuModule implements MenuModule{
	
	def getPriority()
	{
		-10
	}
	
	def getMenu()
	{
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		xml.root
		{
			submenu(label: 'Master Data')
			{
				menuitem(controller: 'organism', action: 'list', label: 'Organisms')
				menuitem(controller: 'mediumAdditive', action: 'list', label: 'Medium Additives')
				menuitem(controller: 'origin', action: 'list', label: 'Origins')
				menuitem(controller: 'cellLine', action: 'list', label: 'Cell Lines')
				menuitem(controller: 'cultureMedia', action: 'list', label: 'Culture Media')
				menuitem(controller: 'antibiotics', action: 'list', label: 'Antibiotics')
				menuitem(controller: 'vector', action: 'list', label: 'Vectors')
			}
		}
		
		return writer.toString()
	}
}
