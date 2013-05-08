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
package org.openlab.barcode

import grails.test.mixin.*

@TestFor(BarcodeLabelController)
@Mock(BarcodeLabel)
class BarcodeLabelControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/barcodeLabel/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.barcodeLabelInstanceList.size() == 0
        assert model.barcodeLabelInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.barcodeLabelInstance != null
    }

    void testSave() {
        controller.save()

        assert model.barcodeLabelInstance != null
        assert view == '/barcodeLabel/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/barcodeLabel/show/1'
        assert controller.flash.message != null
        assert BarcodeLabel.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/barcodeLabel/list'


        populateValidParams(params)
        def barcodeLabel = new BarcodeLabel(params)

        assert barcodeLabel.save() != null

        params.id = barcodeLabel.id

        def model = controller.show()

        assert model.barcodeLabelInstance == barcodeLabel
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/barcodeLabel/list'


        populateValidParams(params)
        def barcodeLabel = new BarcodeLabel(params)

        assert barcodeLabel.save() != null

        params.id = barcodeLabel.id

        def model = controller.edit()

        assert model.barcodeLabelInstance == barcodeLabel
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/barcodeLabel/list'

        response.reset()


        populateValidParams(params)
        def barcodeLabel = new BarcodeLabel(params)

        assert barcodeLabel.save() != null

        // test invalid parameters in update
        params.id = barcodeLabel.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/barcodeLabel/edit"
        assert model.barcodeLabelInstance != null

        barcodeLabel.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/barcodeLabel/show/$barcodeLabel.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        barcodeLabel.clearErrors()

        populateValidParams(params)
        params.id = barcodeLabel.id
        params.version = -1
        controller.update()

        assert view == "/barcodeLabel/edit"
        assert model.barcodeLabelInstance != null
        assert model.barcodeLabelInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/barcodeLabel/list'

        response.reset()

        populateValidParams(params)
        def barcodeLabel = new BarcodeLabel(params)

        assert barcodeLabel.save() != null
        assert BarcodeLabel.count() == 1

        params.id = barcodeLabel.id

        controller.delete()

        assert BarcodeLabel.count() == 0
        assert BarcodeLabel.get(barcodeLabel.id) == null
        assert response.redirectedUrl == '/barcodeLabel/list'
    }
}
