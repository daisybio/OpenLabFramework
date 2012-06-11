class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
            constraints {
                   // apply constraints here
              }
        }
        "/"(controller:"dashboard")
        "403"(controller: "error", action: "error403")
        "500"(controller: "error", action: "error500")
		"404"(controller: "error", action: "error404")
   }
}

