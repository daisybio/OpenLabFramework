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
package security

import org.openlab.security.Role

/**
 * Authority Controller.
 */
class RoleController {

    // the delete, save and update actions only accept POST requests
    static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def springSecurityService

    def index = {
        redirect action: list, params: params
    }

    /**
     * Display the list authority page.
     */
    def list = {
        if (!params.max) {
            params.max = 10
        }
        [authorityList: Role.list(params)]
    }

    /**
     * Display the show authority page.
     */
    def show = {
        def authority = Role.get(params.id)
        if (!authority) {
            flash.message = "Role not found with id $params.id"
            redirect action: list
            return
        }

        [authority: authority]
    }

    /**
     * Delete an authority.
     */
    def delete = {
        def authority = Role.get(params.id)
        if (!authority) {
            flash.message = "Role not found with id $params.id"
            redirect action: list
            return
        }

        springSecurityService.deleteRole(authority)

        flash.message = "Role $params.id deleted."
        redirect action: list
    }

    /**
     * Display the edit authority page.
     */
    def edit = {
        def authority = Role.get(params.id)
        if (!authority) {
            flash.message = "Role not found with id $params.id"
            redirect action: list
            return
        }

        [authority: authority]
    }

    /**
     * Authority update action.
     */
    def update = {

        def authority = Role.get(params.id)
        if (!authority) {
            flash.message = "Role not found with id $params.id"
            redirect action: edit, id: params.id
            return
        }

        long version = params.version.toLong()
        if (authority.version > version) {
            authority.errors.rejectValue 'version', 'authority.optimistic.locking.failure',
                    'Another user has updated this Role while you were editing.'
            render view: 'edit', model: [authority: authority]
            return
        }

        if (springSecurityService.updateRole(authority, params)) {
            springSecurityService.clearCachedRequestmaps()
            redirect action: show, id: authority.id
        }
        else {
            render view: 'edit', model: [authority: authority]
        }
    }

    /**
     * Display the create new authority page.
     */
    def create = {
        [authority: new Role()]
    }

    /**
     * Save a new authority.
     */
    def save = {

        def authority = new Role()
        authority.properties = params
        if (authority.save()) {
            redirect action: show, id: authority.id
        }
        else {
            render view: 'create', model: [authority: authority]
        }
    }
}
