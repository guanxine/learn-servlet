package cn.gx.servlet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by always on 16/3/25.
 */
public class MyTagBody extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        System.out.println(getJspBody());
        getJspBody().invoke(null);

    }
}
