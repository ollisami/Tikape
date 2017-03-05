package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AiheDao;
import tikape.runko.database.Database;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:tietokanta.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        AiheDao aiheDao = new AiheDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Aihe> aiheet = aiheDao.findAll();
            //map.put("aiheet", aiheet);
            List<String> aiheL = new ArrayList<>();

            for (Aihe aihe : aiheet) {
                Viesti uusin = null;
                List<Keskustelu> k = keskusteluDao.findAllWithAiheId(aihe.getId());
                List<Viesti> kaikkiViestit = new ArrayList<>();
                for (Keskustelu keskustelu : k) {
                    List<Viesti> v = viestiDao.findAllWithKeskusteluId(keskustelu.getId());
                    kaikkiViestit.addAll(v);
                    for (Viesti viesti : v) {
                        if (uusin == null || viesti.getId() < uusin.getId()) {
                            uusin = viesti;
                        }
                    }
                    aihe.setViesteja(kaikkiViestit.size());
                    if (uusin != null) {
                        aihe.setViimeisinViesti(uusin.getAika());
                    }
                }
                String s = "" + aihe.getAiheenNimi() + " " + kaikkiViestit.size();
                if (uusin != null) {
                    s += " " + uusin.getAika();
                }
                aiheL.add(s);
            }
            //System.out.println(aiheL);
            map.put("aiheet", aiheL);
            map.put("aiheOliot", aiheet);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            if (!req.queryParams("aihe").isEmpty() && !req.queryParams("nimi").isEmpty()) {
                aiheDao.lisaa(req.queryParams("aihe"), req.queryParams("nimi"));
            }
            res.redirect(req.pathInfo());
            return "ok";
        });

        get("/viestit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAll());

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        get("/viestit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        /*
         get("/aihe", (req, res) -> {
         HashMap map = new HashMap<>();
         map.put("aiheet", aiheDao.findAll());

         return new ModelAndView(map, "aihe");
         }, new ThymeleafTemplateEngine());
         */
        get("/aihe/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelu> keskustelut = keskusteluDao.findAllWithAiheId(Integer.parseInt(req.params("id")));
            for (Keskustelu k : keskustelut) {
                String s = "" + k.getId();
                System.out.println(s);
                List<Viesti> viestit = viestiDao.findAllWithKeskusteluId(Integer.parseInt(s));
                if (!viestit.isEmpty()) {
                    k.setViesteja(viestit.size());
                }
                Viesti uusin = null;
                for (Viesti v : viestit) {
                    if (uusin == null || v.getId() < uusin.getId()) {
                        uusin = v;
                    }
                }
                if (uusin != null) {
                    k.setViimeisinViesti(uusin.getAika());
                }
            }

            map.put("keskustelut", keskustelut);

            return new ModelAndView(map, "aihe");
        }, new ThymeleafTemplateEngine());
        post("/aihe/:id", (req, res) -> {
            if (!req.queryParams("keskustelu").isEmpty() && !req.queryParams("nimi").isEmpty()) {
            keskusteluDao.lisaa(req.queryParams("keskustelu"), req.queryParams("nimi"), Integer.parseInt(req.params("id")));
            }
            res.redirect(req.pathInfo());
            return "ok";
        });

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = viestiDao.findAllWithKeskusteluId(Integer.parseInt(req.params("id")));
            map.put("id", req.params("id"));
            map.put("viestit", viestit);

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
        post("/keskustelu/:id", (req, res) -> {
            if (!req.queryParams("viesti").isEmpty() && !req.queryParams("nimi").isEmpty()) {
            viestiDao.lisaa(req.queryParams("viesti"), req.queryParams("nimi"), Integer.parseInt(req.params("id")));
            }
            res.redirect(req.pathInfo());
            return "ok";
        });

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}
