package com.example.core.wed.utils;

import com.example.core.wed.command.AbstractCommand;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static void initSearchBean(HttpServletRequest request, AbstractCommand bean) {
        if(bean!=null) {
            // get du lieu khi click vao bang trong client
            String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String pageStr = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));

            Integer page = 1;
            // Kiem tra khac rong
            if(StringUtils.isNotBlank(pageStr)) {
                try {
                    page = Integer.valueOf(pageStr);
                } catch (Exception e) {
                    //ignore
                }
            }
            //set gia tri dua tren cong thuc tren trang displaytag
            bean.setPage(page);
            bean.setSortDirection(sortDirection );
            bean.setSortExpression(sortExpression);
            bean.setFirstItem((bean.getPage() - 1) * bean.getMaxPageItems());


        }
    }
}
