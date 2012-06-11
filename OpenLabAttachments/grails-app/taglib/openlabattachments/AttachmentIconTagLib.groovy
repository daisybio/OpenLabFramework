package openlabattachments

class AttachmentIconTagLib {

	def attachmentIcon = { attr, body ->

		out << "<img src='" + createLinkTo(dir: 'images', file: attr['type']+".png")+"'/>"
	}
	
	def attachmentIconWithPlugin = { attr, body ->
		out << "<img src='" + createLinkTo(dir: pluginContextPath+'/images', file: attr['type']+".png")+"'/>"
	}
	
}
