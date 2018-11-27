package ru.otus.l161;

import com.google.gson.JsonObject;
import ru.otus.l161.MessageSystem.Message;
import ru.otus.l161.MessageSystem.MessageContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserAddServlet extends HttpServlet {

    public static final String USERNAME_PARAMETER_NAME = "username";
    private static final String LOGIN_PAGE_TEMPLATE = "users.html";
    private int owner;
    private StringBuilder inbox;

    private final TemplateProcessor templateProcessor;
    private MessageContainer messageContainer;

    public UserAddServlet(TemplateProcessor templateProcessor, MessageContainer messageContainer, int owner) {
        this.templateProcessor = templateProcessor;
        this.messageContainer = messageContainer;
        this.owner = owner;
        this.inbox = new StringBuilder();
    }

    private String getPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        while(messageContainer.countInbox()>0){
            inbox.append(messageContainer.getFromInbox().getBody().get("status").toString() + "<br>");
        }
        pageVariables.put("inbox", inbox);


        return templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME_PARAMETER_NAME);

        if (username != null) {
            JsonObject body = new JsonObject();
            body.addProperty("user", "{\"name\":\"" + username + "\",\"age\":35,\"phone\":{\"number\":\"123\"},\"address\":{\"street\":\"Moscow\"}}");
            body.addProperty("from", owner);

            messageContainer.putToOutbox(new Message(1234, body));
        }

        setOK(response);
        String page = getPage();
        response.getWriter().println(page);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
