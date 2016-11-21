package ck.magicfilter.controllers;

import ck.magicfilter.FilterManager;
import ck.magicfilter.FilterResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @Autowired
    private FilterManager filterManager;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "upload-file", method = RequestMethod.POST)
    public
    @ResponseBody
    List<FilterResult> uploadFileHandler(@RequestParam(value = "file", required = false) MultipartFile file,
                                         HttpServletRequest request) throws ParseException {
        return filterManager.apply(file, parseFilters(request));
    }

    private String[] parseFilters(HttpServletRequest request) throws ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(request.getParameter("data"));
        JSONObject jsonObject = (JSONObject) object;
        Object[] objects = ((JSONArray) jsonObject.get("filters")).toArray();
        String[] filters = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            filters[i] = objects[i].toString();
        }
        return filters;
    }
}
