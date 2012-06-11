package security

import org.openlab.security.Role
import org.openlab.security.User
import org.openlab.security.UserRole

/**
 * User controller.
 */
class UserController {

    def springSecurityService

    def scaffold = User

    // the delete, save and update actions only accept POST requests
    static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def index = {
        redirect action: list, params: params
    }

    def list = {
        if (!params.max) {
            params.max = 10
        }
        [userList: User.list(params), userTotal: User.count()]
    }

    def show = {
        def person = User.get(params.id)
        if (!person) {
            flash.message = "User not found with id $params.id"
            redirect action: list
            return
        }
        List roleNames = []
        for (role in person.authorities) {
            roleNames << role.authority
        }
        roleNames.sort { n1, n2 ->
            n1 <=> n2
        }
        [person: person, roleNames: roleNames]
    }

    /**
     * Person delete action. Before removing an existing person,
     * he should be removed from those authorities which he is involved.
     */
    def delete = {

        def person = User.get(params.id)
        if (person) {
            def authPrincipal = springSecurityService.getPrincipal()
            //avoid self-delete if the logged-in user is an admin
            if (!(authPrincipal instanceof String) && authPrincipal.username == person.username) {
                flash.message = "You can not delete yourself, please login as another admin and try again"
            }
            else {
                //first, delete this person from People_Authorities table.
                UserRole.removeAll(person)
                person.delete()
                flash.message = "User $params.id deleted."
            }
        }
        else {
            flash.message = "User not found with id $params.id"
        }

        redirect(action: list, params: [bodyOnly: true])
    }

    def edit = {

        def person = User.get(params.id)
        if (!person) {
            flash.message = "User not found with id $params.id"
            redirect(action: list, params: [bodyOnly: true])
            return
        }

        return buildPersonModel(person)
    }

    /**
     * Person update action.
     */
    def update = {

        def person = User.get(params.id)
        if (!person) {
            flash.message = "User not found with id $params.id"
            redirect action: edit, id: params.id, params: [bodyOnly: true]
            return
        }

        params.bodyOnly = true

        long version = params.version.toLong()
        if (person.version > version) {
            person.errors.rejectValue 'version', "person.optimistic.locking.failure",
                    "Another user has updated this User while you were editing."
            render view: 'edit', model: buildPersonModel(person)
            return
        }

        def oldPassword = person.password
        person.properties = params
        if (!params.password.equals(oldPassword)) {
            person.password = springSecurityService.encodePassword(params.password)
        }
        if (person.save()) {
            UserRole.removeAll(person)
            addRoles(person)
            redirect action: show, id: person.id, params: [bodyOnly: true]
        }
        else {
            render view: 'edit', model: buildPersonModel(person)
        }
    }

    def create = {
        [person: new User(params), authorityList: Role.list()]
    }

    /**
     * Person save action.
     */
    def save = {

        def person = new User()
        person.properties = params
        person.password = springSecurityService.encodePassword(params.password)
        params.bodyOnly = true
        if (person.save()) {
            addRoles(person)
            redirect action: show, id: person.id, params: params
        }
        else {
            render view: 'create', model: [authorityList: Role.list(), person: person]
        }
    }

    private void addRoles(person) {
        for (String key in params.keySet()) {
            if (key.contains('ROLE') && 'on' == params.get(key)) {
                UserRole.create(person, Role.findByAuthority(key))
            }
        }
    }

    private Map buildPersonModel(person) {

        List roles = Role.list()
        roles.sort { r1, r2 ->
            r1.authority <=> r2.authority
        }
        Set userRoleNames = []
        for (role in person.authorities) {
            userRoleNames << role.authority
        }
        LinkedHashMap<Role, Boolean> roleMap = [:]
        for (role in roles) {
            roleMap[(role)] = userRoleNames.contains(role.authority)
        }

        return [person: person, roleMap: roleMap]
    }
}
