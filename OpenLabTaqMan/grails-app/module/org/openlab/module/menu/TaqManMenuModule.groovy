package org.openlab.module.menu

import groovy.xml.MarkupBuilder
import org.openlab.module.MenuModule

class TaqManMenuModule implements MenuModule {

    def getPriority() {
        5
    }

    def getMenu() {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        def controller = "taqMan"

        xml.root
                {
                    submenu(label: 'TaqMan')
                            {
                                menuitem(controller: controller, action: 'ajaxLoad', label: 'New Taqman Analysis')
                                menuitem(controller: controller, action: 'newTaqMan', label: 'Return to last Taqman Analysis')
                                menuitem(controller: 'taqManResult', action: 'list', label: 'TaqMan Results')
                                menuitem(controller: 'taqManSet', action: 'list', label: 'TaqMan Sets')
                                menuitem(controller: 'inducer', action: 'list', label: 'TaqMan Inducers')
                                menuitem(controller: 'purificationMethod', action: 'list', label: 'Purification Methods')
                                menuitem(controller: 'reverseTranscriptionKit', action: 'list', label: 'Reverse Transcription Kits')
                                menuitem(controller: 'reverseTranscriptionPrimer', action: 'list', label: 'Reverse Transcription Primers')
                                menuitem(controller: 'taqManAssay', action: 'list', label: 'TaqMan Assays')
                            }
                }

        return writer.toString()
    }
}
