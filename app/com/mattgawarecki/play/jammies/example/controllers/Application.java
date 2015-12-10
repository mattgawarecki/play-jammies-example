package com.mattgawarecki.play.jammies.example.controllers;

import com.mattgawarecki.play.jammies.JsonApiErrorResponse;
import com.mattgawarecki.play.jammies.json.DetailedLink;
import com.mattgawarecki.play.jammies.json.JsonApiError;
import com.mattgawarecki.play.jammies.json.SimpleLink;
import com.mattgawarecki.play.jammies.json.Source;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.UUID;

public class Application extends Controller {

    public Result getBadRequestResponse() {
        return JsonApiErrorResponse.badRequest(
            "This is a custom detail message. Apparently there was a problem with the request.");
    }

    public Result getForbiddenResponse() {
        return JsonApiErrorResponse.forbidden(
            "This is a custom detail message. You're not allowed to see this resource.");
    }

    public Result getInternalServerErrorResponse() throws Exception {
        throw new Exception("This is the detail from a thrown exception!");
    }

    public Result getQuickCustomErrorResponse() {
        final Integer statusCode = Integer.parseInt(request().getQueryString("status"));
        final String title = request().getQueryString("title");
        final String detail = request().getQueryString("detail");

        return JsonApiErrorResponse.status(statusCode, title, detail);
    }

    public Result getFullCustomErrorResponse() {
        final JsonApiError fullError = new JsonApiError();

        String id = request().getQueryString("id");
        if (id == null) id = UUID.randomUUID().toString();
        fullError.setId(id);

        fullError.setStatus(42);
        fullError.setCode("ANSWER_FOUND");
        fullError.setTitle("The Answer");
        fullError.setDetail("The answer to life, the universe, and everything has been found. But you're not going to like it...");

        fullError.setSource(new Source(request().uri(), "id"));

        fullError.links().add(new SimpleLink("/quick?status=622&title=Custom Status&detail=This%20is%20a%20custom%20detail%20message."));
        final DetailedLink detailedLink = new DetailedLink();
        detailedLink.setHref("http://www.example.com");
        detailedLink.meta().put("info", "Just some information about this link.");
        fullError.links().add(detailedLink);

        fullError.meta().put("water_boiling_point_celsius", 100);
        fullError.meta().put("is_full_error", true);

        return JsonApiErrorResponse.status(42, fullError);
    }
}
