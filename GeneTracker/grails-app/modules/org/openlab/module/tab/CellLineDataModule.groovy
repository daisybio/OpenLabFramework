package org.openlab.module.tab

import org.openlab.module.*
import org.openlab.genetracker.CellLineData;

class CellLineDataModule implements Module{

	def getPluginName() {
		"gene-tracker"
	}

	def getTemplateForDomainClass(def domainClass) 
	{
		if(domainClass == "gene") return "cellLineDataTab"
		
		else return null
	}

    @Override
    def getMobileTemplateForDomainClass(Object domainClass) {
        return null
    }

    def isInterestedIn(def domainClass, def type)
	{
		if((type == "tab") && (domainClass == "gene")) return true
		return false
	}
	
	def getModelForDomainClass(def domainClass, def id)
	{
		if(domainClass == "gene")
		{
            def cellLineDataList = CellLineData.withCriteria{
                    firstRecombinant{
                        genes{
                            eq('id', Long.valueOf(id))
                        }
                    }
            }

            cellLineDataList.addAll(CellLineData.withCriteria{
                    secondRecombinant{
                        genes{
                            eq('id', Long.valueOf(id))
                        }
                    }
            })

			return [cellLineDataList: cellLineDataList]
		}
	}

    @Override
    def isMobile() {
        return false
    }
}
