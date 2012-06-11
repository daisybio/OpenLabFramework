package org.openlab.taglib

import java.util.Calendar;

class IconTagLib {

	def loadIcon = { attrs ->
		
		if(isChristmas()) 
		{
			out << """<link rel="shortcut icon" href="${resource(dir:'images',file:'christmas.gif')}" type="image/x-icon" />"""
		}
		else
		{ 
			out << """<link rel="shortcut icon" href="${resource(dir:'images',file:'openlab_ico.gif')}" type="image/x-icon" />"""
		}	
	}
	
	def loadLogo = { attrs ->
		
		if(isChristmas())
			out << """<img src="${createLinkTo('dir': 'images', file: 'christmas.gif')}" />"""
		
		else
			out << """<img src="${createLinkTo('dir': 'images', file: 'OLFt.gif')}" />"""
			
		if(isHolyNight())
		{
			out << """<script type="text/javascript">alert('Merry Christmas!');</script>"""
		}
	}
	
	def isHolyNight()
	{
		def cal = new java.util.GregorianCalendar()
		
		(cal.get(Calendar.MONTH) == 11 && cal.get(Calendar.DAY_OF_MONTH) == 24)
	}
	
	def isChristmas()
	{
		def cal = new java.util.GregorianCalendar()
		
		def dezRange = 24..31
		def janRange = 1..6
		
		((cal.get(Calendar.MONTH) == 11 && dezRange.contains(cal.get(Calendar.DAY_OF_MONTH)))
		||(cal.get(Calendar.MONTH) == 0 && janRange.contains(cal.get(Calendar.DAY_OF_MONTH))))
	}
	
}
