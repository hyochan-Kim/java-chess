package chess;

import chess.cotroller.GameController;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Map;

import static spark.Spark.*;

public class WebUIChessApplication {
    public static void main(String[] args) {
        staticFiles.location("/templates");
        get("/", GameController::index);
        get("/start", GameController::start);
        post("/load", GameController::load);
        post("/refresh", GameController::refresh);
        post("/end", GameController::end);
        post("/surrender", GameController::surrender);
        post("/way", GameController::way);
        post("/move", GameController::move);
    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
