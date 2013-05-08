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
class @webtest.name.caps@Test extends grails.util.WebTest {

    // Unlike unit tests, functional tests are often sequence dependent.
    // Specify that sequence here.
    void suite() {
        test@webtest.name.caps@ListNewDelete()
        // add tests for more operations here
    }

    def test@webtest.name.caps@ListNewDelete() {
        webtest('@webtest.name.caps@ basic operations: view list, create new entry, view, edit, delete, view') {
            invoke(url:'@webtest.name.lower@')
            verifyText(text:'Home')

            verifyListPage(0)

            clickLink(label:'New @webtest.name.caps@')
            verifyText(text:'Create @webtest.name.caps@')
            clickButton(label:'Create')
            verifyText(text:'Show @webtest.name.caps@', description:'Detail page')
            clickLink(label:'List', description:'Back to list view')

            verifyListPage(1)

            group(description:'edit the one element') {
                clickLink(label:'Show', description:'go to detail view')
                clickButton(label:'Edit')
                verifyText(text:'Edit @webtest.name.caps@')
                clickButton(label:'Update')
                verifyText(text:'Show @webtest.name.caps@')
                clickLink(label:'List', description:'Back to list view')
            }

            verifyListPage(1)

            group(description:'delete the only element') {
                clickLink(label:'Show', description:'go to detail view')
                clickButton(label:'Delete')
                verifyXPath(xpath:"//div[@class='message']", text:/@webtest.name.caps@.*deleted./, regex:true)
            }

            verifyListPage(0)
        }
    }

    String ROW_COUNT_XPATH = "count(//td[@class='actionButtons']/..)"

    def verifyListPage(int count) {
        ant.group(description:"verify @webtest.name.caps@ list view with $count row(s)") {
            verifyText(text:'@webtest.name.caps@ List')
            verifyXPath(xpath:ROW_COUNT_XPATH, text:count, description:"$count row(s) of data expected")
        }
    }
}