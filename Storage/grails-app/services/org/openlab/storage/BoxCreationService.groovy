package org.openlab.storage

class BoxCreationService {

	def settingsService
	
	def getSortedBoxElements(def id)
	{
		def box = Box.get(id)
		
		def xdim = box.xdim
		def ydim = box.ydim
		def elements = box.elements
		
		//sort elements for easy iteration
		def sortedElements = elements.sort{ a, b ->
			if(a.xcoord == b.xcoord) a.ycoord <=> b.ycoord
			else a.xcoord <=> b.xcoord
		}
		
		sortedElements
	}
	
	def getAxisLabel(int num, def axis, def isAlpha = false)
	{
		num += 1
		if(isAlpha == false) isAlpha = settingsService.getUserSetting(key: "storage.axis.${axis}") == "alpha"
		
		if(isAlpha)
		{
			 StringBuilder sb = new StringBuilder();
			 char a = 'A'
			 
			 while (num > 0) {
				 char label = a + (num % 26)-1
				 sb.append(label);
				 num /= 26;
			 }

			 return sb.reverse().toString();
		}
		else
		{
			return num
		}
	}
	
	def getIntFromAlpha(char c)
	{
		def num = ((int) c) - ((int)'A') + 1 
		return num
	}

}
