package org.openlab.genetracker

import org.openlab.main.Project
import grails.converters.JSON
import org.openlab.main.Laboratory
import org.openlab.security.User

class StatsController {

    def geneStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def genesAsJSON = [
            cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of genes", pattern: "", type: "number"]],
            rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                [c: [[v: project.name, f: null], [v: Gene.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
            }
        ]

        render genesAsJSON as JSON
    }

    def recombinantStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def recombinantsAsJSON = [
                cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of recombinants", pattern: "", type: "number"]],
                rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                    [c: [[v: project.name, f: null], [v: Recombinant.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
                }
        ]

        render recombinantsAsJSON as JSON
    }

    def cellLineDataStats() {
        def nanocanLab = Laboratory.findByName("Odense")

        def cellLineDataAsJSON = [
                cols : [[id: "", label:"projects", pattern: "", type: "string"], [id: "", label: "number of cell line recombinants:", pattern: "", type: "number"]],
                rows : Project.findAllByLaboratory(nanocanLab).collect{ project ->
                    [c: [[v: project.name, f: null], [v: CellLineData.withCriteria{ projects { eq('id', project.id) }}.size(), f: null]]]
                }
        ]

        render cellLineDataAsJSON as JSON
    }

    def genesByUserStats(){
        def genesByUserAsJSON = [
                cols : [[id: "", label:"user", pattern: "", type: "string"], [id: "", label: "genes by user:", pattern: "", type: "number"]],
                rows : User.list().collect{ user ->
                    [c: [[v: user.username, f: null], [v: Gene.withCriteria{ projects { eq('creator.id', user.id) }}.size(), f: null]]]
                }
        ]

        render genesByUserAsJSON as JSON
    }
}
