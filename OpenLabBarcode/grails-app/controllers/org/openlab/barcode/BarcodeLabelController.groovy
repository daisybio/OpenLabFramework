package org.openlab.barcode

class BarcodeLabelController {

    def scaffold = BarcodeLabel

    def show() {

        redirect(action: "list", params: [bodyOnly: params.bodyOnly?:false])
    }
}
