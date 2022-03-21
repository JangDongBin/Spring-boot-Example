package com.sample.sample.account;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Account login_user_data = (Account)authentication.getPrincipal();
        System.out.println("인증 후 : login data" + login_user_data);

        List<Role> auth_list = login_user_data.getRoles();
        Iterator<Role> auth_Iterator = auth_list.iterator();

        String base_url = "/";
        while(auth_Iterator.hasNext()){
            Role auth = auth_Iterator.next();
            if(auth.getAuthority().equals("ROLE_ADMIN") | auth.getAuthority().equals("ROLE_USER")){
                base_url = "/account/update";
            }
        }

        //request.getSession().setAttribute("", "");
        response.sendRedirect(base_url);
    }

}
