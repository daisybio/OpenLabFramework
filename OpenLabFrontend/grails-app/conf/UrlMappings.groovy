class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
            constraints {
                   // apply constraints here
              }
        }
        "/"(controller:"login", action: "index")
        "403"(controller: "error", action: "error403")
        "500"(view: "error")
		"404"(controller: "error", action: "error404")
        "401"(controller: "login", action: "auth")
   }
}

