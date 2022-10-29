package me.zuif.teashop.utils;

import lombok.Data;
import me.zuif.teashop.model.tea.TeaType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Data
public class FindOptions {
    private Object data;
    private FindType type;
    private boolean sort;
    private boolean sortBy;

    public static FindOptions retrieveFromRequest(HttpServletRequest request) {
        FindOptions findOptions = new FindOptions();
        if (request.getParameter("search") != null && !request.getParameter("search").isEmpty()) {
            String search = request.getParameter("search");
            findOptions.setData("%" + search + "%");
            findOptions.setType(FindOptions.FindType.SEARCH);
        } else if (request.getParameter("teaType") != null && !request.getParameter("teaType").isEmpty()) {
            TeaType type = TeaType.valueOf(request.getParameter("teaType"));
            findOptions.setData(type);
            findOptions.setType(FindOptions.FindType.TYPE);
        } else {
            findOptions.setType(FindOptions.FindType.DEFAULT);
        }
        if (request.getParameter("sort") != null && !request.getParameter("sort").isEmpty()) {
            List<String> sortValues = List.of("count", "price", "addTime");
            findOptions.setSort(sortValues.contains(request.getParameter("sort")));
        }
        if (request.getParameter("sortBy") != null && !request.getParameter("sortBy").isEmpty()) {
            findOptions.setSortBy(findOptions.isSort());
        }
        return findOptions;
    }

    public enum FindType {
        SEARCH, DEFAULT, TYPE
    }
}
