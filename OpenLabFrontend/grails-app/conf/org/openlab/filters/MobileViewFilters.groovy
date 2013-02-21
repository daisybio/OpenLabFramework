package org.openlab.filters

import org.springframework.web.servlet.ModelAndView

class MobileViewFilters {

    def filters = {
        all(controller: '*', action: 'show') {

            after = { model ->

                def device = request.getAttribute('currentDevice')

                boolean mobileDevice = device.isMobile()

                if (mobileDevice) {
                    modelAndView = new ModelAndView('mobile_show', model)
                }
            }

        }
    }
}
