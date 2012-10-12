package org.openlab.navigation;

import org.openlab.main.Project
import grails.converters.JSON
import org.openlab.genetracker.Gene

/**
 * Controller to deliver XML data to the YUI tree that is showing
 * the project structure.
 * @author markus.list
 *
 */
public class ProjectTreeController {

    def index = { renderTree }

    //def springSecurityService
    //def settingsService

    def renderTree(){
        [:]
    }

    def projectTreeAsJSON() {

        if(params.int("id") == 0)
        {
            def projects = Project.list(sort: "name")

            def projectsAsJSON = projects.collect{
                [
                        "data" : it.name,
                        "attr" : [ "id" : it.id , "rel":"project", "nodeType" : "project"],
                        "state" : "closed"
                ]
            }
            render projectsAsJSON as JSON
        }

        else{
            def genesAsJSON = Gene.withCriteria{ projects { eq('id', params.long("id")) }}.sort{it.name}.collect{
                [
                        "data" : it.name,
                        "attr" : [ "id" : it.id , "rel":"gene", "nodeType" : "gene"]
                ]
            }

            render genesAsJSON as JSON
        }
    }
    /**
     * Create the XML data for the YUI treeview
     */
    /*def renderTree = {

        /*
               * create tree data XML from projects
               *
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        def principal = springSecurityService.getPrincipal()
        def userName = principal.getUsername()

        xml.root(name: settingsService.getLabel(key: "projects") ?: "Projects", checked: false, expanded: false)
                {
                    Laboratory.list().each { lab ->

                        xml.laboratories(name: lab.toString(), checked: false, expanded: false)
                                {
                                    def myProjects = Project.findAllByLaboratoryAndCreator(lab, User.findByUsername(userName)).toList().sort {it.toString()};
                                    def otherProjects = (Project.findAllByLaboratory(lab) - myProjects).toList().sort {it.toString()}

                                    findProjects(xml, settingsService.getLabel(key: "myprojects") ?: "My Projects", myProjects)
                                    findProjects(xml, settingsService.getLabel(key: "otherprojects") ?: "Other Projects", otherProjects)
                                }
                    }

                }

        /*
               * set params
               *
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        /**
         * obtain dataobject types
         *
        def types = DataObject.list().collect {it.type}.unique()

        render(template: "/layouts/renderTree", model: ["data": writer.toString(), objectTypes: types])
    }

    /**
     * Processes a list of projects and builds up the XML structure. Checked refers to the availability of a
     * checkbox version of the YUI treeview. Not used here.
     * @param builder
     * @param projName
     * @param projList
     * @return

    def findProjects(builder, projName, projList) {
        builder.projects(name: projName, id: projName, checked: false, expanded: false, selected: false)
                {
                    projList.each {p ->
                        if (projList.contains(p)) {
                            project(name: p.name, id: p.id, checked: false, expanded: false, selected: false)
                                    {
                                        DataObject.list().findAll {it.projects.contains(p)}.each {obj ->
                                            object(name: obj.toString(), id: obj.id, checked: false, expanded: false, selected: isSelected(obj))
                                        }
                                    }
                        }
                    }
                }
    }

    /**
     * Determines whether the current node should be expanded or not.
     * To determine which project is focused, the nextBackId property
     * of the BrowserHistoryFilter (grails-app/conf) is used.
     * @param p
     * @return
     *
    def isExpanded(def project) {
        if (session.nextBackId && DataObject.findById(session.nextBackId)?.projects?.contains(project)) return true

        else return false
    }

    /**
     * Determines whether a node (representing a dataobject or a project) should be selected.
     * To determine which node is focused at that moment, the nextBackId property
     * of the BrowserHistoryFilter (grails-app/conf) is used.
     * @param obj
     * @return
     *
    def isSelected(def obj) {
        if (!session.nextBackId) return false

        else if (obj.class.getName() == "org.openlab.main.Project") {
            return (session.nextBackId.toString() == obj.id.toString())
        }
        else return (DataObject.findById(session.nextBackId) == obj)
    }

    /**
     * Checks whether "MyProjects" or "OtherProjects" has been selected and
     * triggers the corresponding action in project controller.
     *
    def listSubsetOfProjects = {
        def id = params.id
        params.remove("id")

        if (id == (settingsService.getLabel(key: "myprojects") ?: "My Projects"))
            redirect(controller: "project", action: "listMyProjects", params: params)

        else if (id == (settingsService.getLabel(key: "otherprojects") ?: "Other Projects"))
            redirect(controller: "project", action: "listOtherProjects", params: params)
    }    **/
}
