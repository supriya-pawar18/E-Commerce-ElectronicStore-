package com.electronicstore.electronicStoreApp.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwTHelper jwTHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Autherization
        String requestHeader = request.getHeader("Authorization");
        //bearer=245535hgjgj
        logger.info("Header : {}",requestHeader);
        String username=null;
        String token=null;

        if(requestHeader !=null && requestHeader.startsWith("Brearer")){
            //looking good
            token = requestHeader.substring(7);
            try{

                this.jwTHelper.getUsernameFromToken(token);

            }catch (IllegalArgumentException e){
                logger.info("IllegalArgument while  fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                logger.info("Given jwt is expired !!");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                logger.info("Some changed has  done in token !! Invalid TokenS");
                e.printStackTrace();
            }catch (Exception e){
               e.printStackTrace();
            }

        }else{
            logger.info(("Invalid Header Value !! "));
        }

        //
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            //fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwTHelper.validateToken(token, userDetails);
            if(validateToken){

                //set authentication
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{
                logger.info("Validation Fails !!");
            }

            filterChain.doFilter(request,response);

        }

    }
}
