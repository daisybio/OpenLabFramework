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
